package com.aiwellness.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * com.aiwellness.common.util
 * <p>
 * AES256Util
 * <p>
 * AES256 암호화/복호화 유틸리티
 * <p>
 * icn/iasds-be-fo 프로젝트의 AES256Util을 참고하여 구현
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
public class AES256Util {

    private static String secretKey;
    private static String algorithm;

    @Value("${crypto.secret-key:aiwellness-secret-key-32-characters!!}")
    public void setSecretKey(String secretKey) {
        AES256Util.secretKey = secretKey;
    }

    @Value("${crypto.algorithm:AES/CBC/PKCS5Padding}")
    public void setAlgorithm(String algorithm) {
        AES256Util.algorithm = algorithm;
    }

    /**
     * 평문을 AES256으로 암호화합니다.
     *
     * @param plainText 평문
     * @return Base64로 인코딩된 암호화 문자열
     */
    public static String encrypt(String plainText) {
        if (plainText == null || plainText.isEmpty()) {
            log.warn("[AES256Util] encrypt() - 평문이 null이거나 비어있습니다.");
            return plainText;
        }

        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance(algorithm);
            
            // IV (Initialization Vector) 생성 (16바이트)
            IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[16]);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            String encryptedText = Base64.getUrlEncoder().encodeToString(encrypted);
            
            log.debug("[AES256Util] encrypt() - 평문: {}, 암호화: {}", plainText, encryptedText);
            return encryptedText;
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | 
                 BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            log.error("[AES256Util] encrypt() - 암호화 실패: {}", e.getMessage(), e);
            throw new RuntimeException("암호화 처리 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 암호화된 문자열을 AES256으로 복호화합니다.
     *
     * @param encryptedText Base64로 인코딩된 암호화 문자열
     * @return 복호화된 평문
     */
    public static String decrypt(String encryptedText) {
        if (encryptedText == null || encryptedText.isEmpty()) {
            log.warn("[AES256Util] decrypt() - 암호화된 문자열이 null이거나 비어있습니다.");
            return encryptedText;
        }

        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance(algorithm);
            
            // IV (Initialization Vector) 생성 (16바이트)
            IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[16]);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

            byte[] decoded = Base64.getUrlDecoder().decode(encryptedText);
            byte[] decrypted = cipher.doFinal(decoded);
            String plainText = new String(decrypted, StandardCharsets.UTF_8);
            
            log.debug("[AES256Util] decrypt() - 암호화: {}, 평문: {}", encryptedText, plainText);
            return plainText;
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | 
                 BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            log.error("[AES256Util] decrypt() - 복호화 실패: {}", e.getMessage(), e);
            throw new RuntimeException("복호화 처리 중 오류가 발생했습니다.", e);
        }
    }
}

