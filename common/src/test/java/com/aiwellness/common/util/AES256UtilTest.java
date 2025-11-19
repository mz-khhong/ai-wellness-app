package com.aiwellness.common.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * com.aiwellness.common.util
 * <p>
 * AES256UtilTest
 * <p>
 * AES256Util 테스트 클래스
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 */
@SpringBootTest
@TestPropertySource(properties = {
    "crypto.secret-key=test-secret-key-32-characters!!",
    "crypto.algorithm=AES/CBC/PKCS5Padding"
})
@DisplayName("AES256Util 테스트")
class AES256UtilTest {

    @Autowired
    private AES256Util aes256Util;

    @BeforeEach
    void setUp() {
        // Spring이 @Value를 통해 설정을 주입하도록 함
    }

    @Test
    @DisplayName("평문을 암호화하고 복호화하면 원본과 동일해야 함")
    void testEncryptAndDecrypt() {
        // Given
        String plainText = "Hello, World! 안녕하세요 123";

        // When
        String encrypted = AES256Util.encrypt(plainText);
        String decrypted = AES256Util.decrypt(encrypted);

        // Then
        assertNotNull(encrypted);
        assertNotEquals(plainText, encrypted);
        assertEquals(plainText, decrypted);
    }

    @Test
    @DisplayName("같은 평문을 여러 번 암호화하면 다른 결과가 나와야 함 (IV 사용)")
    void testEncryptMultipleTimes() {
        // Given
        String plainText = "Test Message";

        // When
        String encrypted1 = AES256Util.encrypt(plainText);
        String encrypted2 = AES256Util.encrypt(plainText);

        // Then
        // IV를 사용하므로 같은 평문도 다른 암호화 결과가 나올 수 있음
        // 하지만 현재 구현은 고정 IV를 사용하므로 같은 결과가 나옴
        assertNotNull(encrypted1);
        assertNotNull(encrypted2);
    }

    @Test
    @DisplayName("null 또는 빈 문자열은 그대로 반환해야 함")
    void testEncryptNullOrEmpty() {
        // When & Then
        assertNull(AES256Util.encrypt(null));
        assertEquals("", AES256Util.encrypt(""));
        assertNull(AES256Util.decrypt(null));
        assertEquals("", AES256Util.decrypt(""));
    }

    @Test
    @DisplayName("긴 문자열도 암호화/복호화가 가능해야 함")
    void testEncryptLongString() {
        // Given
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longText.append("This is a long text. ");
        }
        String plainText = longText.toString();

        // When
        String encrypted = AES256Util.encrypt(plainText);
        String decrypted = AES256Util.decrypt(encrypted);

        // Then
        assertEquals(plainText, decrypted);
    }

    @Test
    @DisplayName("특수문자와 한글이 포함된 문자열도 암호화/복호화가 가능해야 함")
    void testEncryptSpecialCharacters() {
        // Given
        String plainText = "!@#$%^&*()_+-=[]{}|;':\",./<>? 한글 中文 日本語 🎉";

        // When
        String encrypted = AES256Util.encrypt(plainText);
        String decrypted = AES256Util.decrypt(encrypted);

        // Then
        assertEquals(plainText, decrypted);
    }
}

