package com.aiwellness.admin.adapter.infrastructure.external.sso;

import com.aiwellness.common.util.sso.SsoTokenManager;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

/**
 * com.aiwellness.admin.adapter.infrastructure.external.sso
 * <p>
 * ExternalSsoApiFeignConfig
 * <p>
 * 외부 SSO 연계 API 호출 시 SSO 토큰을 자동으로 주입하는 Feign Client 설정
 * <p>
 * Common 모듈의 SsoTokenManager를 주입받아 사용합니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 */
@Slf4j
@RequiredArgsConstructor
public class ExternalSsoApiFeignConfig {
    
    private final SsoTokenManager ssoTokenManager;
    
    /**
     * SSO 토큰을 요청 헤더에 자동 주입하는 Interceptor
     */
    @Bean
    public RequestInterceptor ssoTokenRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                try {
                    // SSO 토큰 발급 (스코프는 필요에 따라 변경 가능)
                    String token = ssoTokenManager.getAccessToken("external-api");
                    
                    // Authorization 헤더에 Bearer 토큰 추가
                    template.header("Authorization", "Bearer " + token);
                    
                    log.debug("[ExternalSsoApiFeignConfig] SSO token injected into request header");
                } catch (Exception e) {
                    log.error("[ExternalSsoApiFeignConfig] Failed to get SSO token: {}", e.getMessage(), e);
                    throw new RuntimeException("Failed to get SSO token for external API call", e);
                }
            }
        };
    }
}

