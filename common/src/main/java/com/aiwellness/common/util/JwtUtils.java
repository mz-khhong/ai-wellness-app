package com.aiwellness.common.util;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * com.aiwellness.common.util
 * <p>
 * JwtUtils
 * <p>
 * JWT 토큰 파싱 및 검증 유틸리티
 * <p>
 * icn/iasds-sso 프로젝트의 JwtUtils를 참고하여 구현
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
public class JwtUtils {

    /**
     * JWT 토큰을 파싱하여 JWTClaimsSet을 반환합니다.
     *
     * @param jwtToken JWT 토큰 문자열
     * @return JWTClaimsSet
     * @throws Exception 파싱 실패 시
     */
    public static JWTClaimsSet parseToken(String jwtToken) throws Exception {
        JWSObject jwsObject = JWSObject.parse(jwtToken);
        Map<String, Object> jsonPayload = jwsObject.getPayload().toJSONObject();
        return JWTClaimsSet.parse(jsonPayload);
    }

    /**
     * RS256 알고리즘으로 JWT 토큰을 검증합니다.
     *
     * @param jwsObject JWSObject
     * @param publicKey RSAPublicKey
     * @return 검증 성공 여부
     * @throws Exception 검증 실패 시
     */
    public static boolean verify(JWSObject jwsObject, RSAPublicKey publicKey) throws Exception {
        JWSHeader header = jwsObject.getHeader();
        JWSAlgorithm algorithm = header.getAlgorithm();

        JWSVerifier verifier = new RSASSAVerifier(publicKey);
        return jwsObject.verify(verifier);
    }

    /**
     * HS256 알고리즘으로 JWT 토큰을 검증합니다.
     *
     * @param jwsObject JWSObject
     * @param secretKey HS256 Secret Key
     * @return 검증 성공 여부
     * @throws Exception 검증 실패 시
     */
    public static boolean verifyWithKey(JWSObject jwsObject, String secretKey) throws Exception {
        JWSVerifier verifier = new MACVerifier(secretKey);
        return jwsObject.verify(verifier);
    }

    /**
     * JWT 토큰을 검증합니다 (RS256 또는 HS256 자동 판별).
     *
     * @param token JWT 토큰 문자열
     * @param publicKey RS256의 경우 Public Key (Base64 인코딩된 문자열), HS256의 경우 Secret Key
     * @return 검증 성공 여부
     */
    public static boolean verifyToken(String token, String publicKey) {
        try {
            JWTClaimsSet jwtClaimsSet = parseToken(token);

            JWSObject jwsObject = JWSObject.parse(token);
            JWSHeader header = jwsObject.getHeader();
            JWSAlgorithm algorithm = header.getAlgorithm();

            boolean verify = false;

            // RS256 알고리즘인 경우
            if (JWSAlgorithm.RS256.equals(algorithm)) {
                JWSVerifier verifier = new RSASSAVerifier(loadRS256PublicKey(publicKey));
                verify = jwsObject.verify(verifier);
            }
            // HS256 알고리즘인 경우
            else {
                JWSVerifier verifier = new MACVerifier(publicKey);
                verify = jwsObject.verify(verifier);
            }

            if (!verify) {
                log.warn("[JwtUtils] verifyToken() - 토큰 검증 실패");
                return false;
            }

            // 만료 시간 확인
            Date currentTime = new Date();
            Date expirationTime = jwtClaimsSet.getExpirationTime();
            if (expirationTime != null) {
                long diff = (long) Math.floor((expirationTime.getTime() - currentTime.getTime()) / 1000);
                if (diff <= 0) {
                    log.warn("[JwtUtils] verifyToken() - 토큰 만료됨: {}초 전", Math.abs(diff));
                    return false;
                }
            }

            return true;
        } catch (Exception ex) {
            log.error("[JwtUtils] verifyToken() - 토큰 검증 중 오류 발생: {}", ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * Base64로 인코딩된 RSA Public Key를 로드합니다.
     *
     * @param publicKey Base64로 인코딩된 Public Key 문자열
     * @return RSAPublicKey
     * @throws Exception 키 로드 실패 시
     */
    public static RSAPublicKey loadRS256PublicKey(String publicKey) throws Exception {
        // Base64 디코드
        byte[] pubdecode = Base64.getDecoder().decode(publicKey);

        // Public Key 생성
        X509EncodedKeySpec ks = new X509EncodedKeySpec(pubdecode);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) kf.generatePublic(ks);
        return rsaPublicKey;
    }
}

