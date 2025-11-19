package com.aiwellness.common.security;

import com.aiwellness.common.util.JwtUtils;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * com.aiwellness.common.security
 * <p>
 * JwtTokenProviderNimbus
 * <p>
 * Nimbus JOSE JWT를 사용한 JWT 토큰 제공자
 * <p>
 * icn/iasds-sso 프로젝트의 방식을 참고하여 구현
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 14.    메가존 시스템            최초 생성
 * </pre>
 */
@Slf4j
@Component
public class JwtTokenProviderNimbus {

    private final String secretKey; // HS256용
    private final String rsaPrivateKey; // RS256용 (Base64 인코딩된 Private Key)
    private final String rsaPublicKey; // RS256용 (Base64 인코딩된 Public Key)
    private final long tokenValidityInMilliseconds;
    private final JWSAlgorithm algorithm;

    public JwtTokenProviderNimbus(
            @Value("${jwt.secret:wellness-app-secret-key-for-jwt-token-generation-minimum-256-bits}") String secret,
            @Value("${jwt.expiration:86400000}") long tokenValidityInMilliseconds,
            @Value("${jwt.algorithm:HS256}") String algorithm,
            @Value("${jwt.rs256.private-key:}") String rsaPrivateKey,
            @Value("${jwt.rs256.public-key:}") String rsaPublicKey) {
        this.secretKey = secret;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
        this.algorithm = JWSAlgorithm.parse(algorithm);
        this.rsaPrivateKey = rsaPrivateKey;
        this.rsaPublicKey = rsaPublicKey;
        
        // RS256 선택 시 키 검증
        if (JWSAlgorithm.RS256.equals(this.algorithm)) {
            if (rsaPrivateKey == null || rsaPrivateKey.isEmpty() || 
                rsaPublicKey == null || rsaPublicKey.isEmpty()) {
                throw new IllegalArgumentException(
                    "RS256 알고리즘을 사용하려면 jwt.rs256.private-key와 jwt.rs256.public-key가 필요합니다.");
            }
        }
    }

    /**
     * JWT 토큰을 생성합니다 (HS256 또는 RS256 알고리즘 사용).
     *
     * @param email 이메일
     * @param userId 사용자 ID
     * @param roles 역할 목록
     * @return JWT 토큰 문자열
     * @throws Exception 토큰 생성 실패 시
     */
           public String createToken(String email, Long userId, String userUuid, Long facilityGroupId, List<String> roles) throws Exception {
               Date now = new Date();
               Date expirationTime = new Date(now.getTime() + tokenValidityInMilliseconds);

               // Claims 생성
               JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
               JWTClaimsSet claimsSet = builder
                       .subject(email)
                       .claim("userId", userId)
                       .claim("userUuid", userUuid)
                       .claim("facilityGroupId", facilityGroupId)
                       .claim("roles", roles)
                       .issueTime(now)
                       .expirationTime(expirationTime)
                       .build();

        // 알고리즘에 따라 서명
        JWSSigner signer;
        SignedJWT signedJWT;
        
        if (JWSAlgorithm.RS256.equals(algorithm)) {
            // RS256 알고리즘 사용
            RSAPrivateKey privateKey = loadRS256PrivateKey(rsaPrivateKey);
            signer = new RSASSASigner(privateKey);
            signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);
        } else {
            // HS256 알고리즘 사용 (기본)
            signer = new MACSigner(secretKey);
            signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        }
        
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
    
    /**
     * RS256 Private Key를 로드합니다.
     *
     * @param privateKeyBase64 Base64로 인코딩된 Private Key
     * @return RSAPrivateKey
     * @throws Exception 키 로드 실패 시
     */
    private RSAPrivateKey loadRS256PrivateKey(String privateKeyBase64) throws Exception {
        byte[] pvdecode = Base64.getDecoder().decode(privateKeyBase64);
        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(pvdecode);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) kf.generatePrivate(ks);
    }
    
    /**
     * RS256 Public Key를 로드합니다.
     *
     * @param publicKeyBase64 Base64로 인코딩된 Public Key
     * @return RSAPublicKey
     * @throws Exception 키 로드 실패 시
     */
    private RSAPublicKey loadRS256PublicKey(String publicKeyBase64) throws Exception {
        byte[] pubdecode = Base64.getDecoder().decode(publicKeyBase64);
        X509EncodedKeySpec ks = new X509EncodedKeySpec(pubdecode);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(ks);
    }

    /**
     * JWT 토큰에서 이메일을 추출합니다
     *
     * @param token JWT 토큰
     * @return 이메일
     * @throws Exception 토큰 파싱 실패 시
     */
    public String getEmail(String token) throws Exception {
        JWTClaimsSet claimsSet = JwtUtils.parseToken(token);
        return claimsSet.getSubject();
    }

    /**
     * JWT 토큰에서 사용자 ID를 추출합니다.
     *
     * @param token JWT 토큰
     * @return 사용자 ID
     * @throws Exception 토큰 파싱 실패 시
     */
    public Long getUserId(String token) throws Exception {
        JWTClaimsSet claimsSet = JwtUtils.parseToken(token);
        return claimsSet.getClaim("userId") != null ? 
                Long.valueOf(claimsSet.getClaim("userId").toString()) : null;
    }

    /**
     * JWT 토큰에서 역할 목록을 추출합니다.
     *
     * @param token JWT 토큰
     * @return 역할 목록
     * @throws Exception 토큰 파싱 실패 시
     */
    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token) throws Exception {
        JWTClaimsSet claimsSet = JwtUtils.parseToken(token);
        Object roles = claimsSet.getClaim("roles");
        if (roles instanceof List) {
            return (List<String>) roles;
        }
        return List.of();
    }

    /**
     * JWT 토큰에서 사용자 UUID를 추출합니다.
     *
     * @param token JWT 토큰
     * @return 사용자 UUID
     * @throws Exception 토큰 파싱 실패 시
     */
    public String getUserUuid(String token) throws Exception {
        JWTClaimsSet claimsSet = JwtUtils.parseToken(token);
        return claimsSet.getClaim("userUuid") != null ? 
                claimsSet.getClaim("userUuid").toString() : null;
    }

    /**
     * JWT 토큰에서 시설 그룹 ID를 추출합니다.
     *
     * @param token JWT 토큰
     * @return 시설 그룹 ID
     * @throws Exception 토큰 파싱 실패 시
     */
    public Long getFacilityGroupId(String token) throws Exception {
        JWTClaimsSet claimsSet = JwtUtils.parseToken(token);
        return claimsSet.getClaim("facilityGroupId") != null ? 
                Long.valueOf(claimsSet.getClaim("facilityGroupId").toString()) : null;
    }

    /**
     * JWT 토큰을 검증합니다.
     *
     * @param token JWT 토큰
     * @return 검증 성공 여부
     */
    public boolean validateToken(String token) {
        try {
            // 알고리즘에 따라 검증 키 선택
            if (JWSAlgorithm.RS256.equals(algorithm)) {
                // RS256의 경우 Public Key 사용
                return JwtUtils.verifyToken(token, rsaPublicKey);
            } else {
                // HS256의 경우 Secret Key 사용
                return JwtUtils.verifyToken(token, secretKey);
            }
        } catch (Exception e) {
            log.warn("[JwtTokenProviderNimbus] validateToken() - 토큰 검증 실패: {}", e.getMessage());
            return false;
        }
    }

    /**
     * JWT 토큰의 모든 Claims를 Map으로 반환합니다.
     *
     * @param token JWT 토큰
     * @return Claims Map
     * @throws Exception 토큰 파싱 실패 시
     */
    public Map<String, Object> getClaims(String token) throws Exception {
        JWTClaimsSet claimsSet = JwtUtils.parseToken(token);
        return claimsSet.getClaims();
    }
}

