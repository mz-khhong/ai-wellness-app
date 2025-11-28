package com.aiwellness.common.util.sso;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * com.aiwellness.common.util.sso
 * <p>
 * SsoTokenManager
 * <p>
 * SSO 토큰 관리 유틸리티 (공통)
 * <p>
 * 외부 SSO 솔루션의 토큰을 발급, 갱신, 캐싱합니다.
 * <p>
 * 각 서버에서 이 유틸리티를 사용하여 SSO 토큰을 관리할 수 있습니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 20.    메가존 시스템            최초 생성
 * </pre>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SsoTokenManager {
    
    @Value("${external.sso.url:}")
    private String ssoUrl;
    
    @Value("${external.sso.client-id:}")
    private String clientId;
    
    @Value("${external.sso.client-secret:}")
    private String clientSecret;
    
    @Value("${external.sso.token.refresh-before-expiry:300}")
    private long refreshBeforeExpiry; // 기본값: 5분 전 갱신
    
    private final RestTemplate restTemplate;
    
    // 토큰 캐시 (실제 운영에서는 Redis 등 사용 권장)
    private final ConcurrentMap<String, TokenCache> tokenCache = new ConcurrentHashMap<>();
    
    /**
     * SSO 토큰 발급 또는 갱신
     * 
     * @param scope 토큰 스코프 (선택사항, null이면 "default" 사용)
     * @return 액세스 토큰
     * @throws IllegalStateException SSO 설정이 없거나 토큰 발급 실패 시
     */
    public String getAccessToken(String scope) {
        if (ssoUrl == null || ssoUrl.isEmpty()) {
            throw new IllegalStateException("SSO URL is not configured. Please set external.sso.url");
        }
        
        String cacheKey = scope != null ? scope : "default";
        TokenCache cached = tokenCache.get(cacheKey);
        
        // 캐시된 토큰이 있고 만료되지 않았으면 반환
        if (cached != null && !cached.isExpired()) {
            log.debug("[SsoTokenManager] Using cached token for scope: {}", scope);
            return cached.getAccessToken();
        }
        
        // 새 토큰 발급
        log.info("[SsoTokenManager] Requesting new SSO token for scope: {}", scope);
        SsoTokenResponse tokenResponse = requestToken(scope);
        
        // 토큰 캐시에 저장 (만료 시간 전에 갱신)
        long expiresIn = tokenResponse.getExpiresIn();
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(expiresIn - refreshBeforeExpiry);
        
        tokenCache.put(cacheKey, new TokenCache(
            tokenResponse.getAccessToken(),
            expiresAt
        ));
        
        log.info("[SsoTokenManager] SSO token cached for scope: {}, expiresAt: {}", scope, expiresAt);
        return tokenResponse.getAccessToken();
    }
    
    /**
     * SSO 서버에 토큰 요청
     * <p>
     * 상속받은 클래스나 컴포지션으로 사용하는 클래스에서도 사용할 수 있도록 protected로 선언
     * 
     * @param scope 토큰 스코프
     * @return SSO 토큰 응답
     */
    protected SsoTokenResponse requestToken(String scope) {
        SsoTokenRequest request = SsoTokenRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType("client_credentials")
                .scope(scope)
                .build();
        
        try {
            SsoTokenResponse response = restTemplate.postForObject(
                ssoUrl + "/oauth/token",
                request,
                SsoTokenResponse.class
            );
            
            if (response == null || response.getAccessToken() == null) {
                throw new RuntimeException("Failed to get SSO token: empty response");
            }
            
            log.info("[SsoTokenManager] SSO token issued successfully for scope: {}", scope);
            return response;
            
        } catch (Exception e) {
            log.error("[SsoTokenManager] Failed to get SSO token for scope: {}", scope, e);
            throw new RuntimeException("Failed to get SSO token: " + e.getMessage(), e);
        }
    }
    
    /**
     * 특정 스코프의 토큰 캐시 무효화
     * 
     * @param scope 토큰 스코프
     */
    public void invalidateToken(String scope) {
        String cacheKey = scope != null ? scope : "default";
        tokenCache.remove(cacheKey);
        log.info("[SsoTokenManager] Token cache invalidated for scope: {}", scope);
    }
    
    /**
     * 모든 토큰 캐시 무효화
     */
    public void invalidateAllTokens() {
        tokenCache.clear();
        log.info("[SsoTokenManager] All token caches invalidated");
    }
    
    /**
     * 토큰 캐시 클래스
     */
    private static class TokenCache {
        private final String accessToken;
        private final LocalDateTime expiresAt;
        
        public TokenCache(String accessToken, LocalDateTime expiresAt) {
            this.accessToken = accessToken;
            this.expiresAt = expiresAt;
        }
        
        public String getAccessToken() {
            return accessToken;
        }
        
        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expiresAt);
        }
    }
}