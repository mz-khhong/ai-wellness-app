package com.aiwellness.common.security;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import lombok.Getter;

/**
 * com.aiwellness.common.security
 * <p>
 * AuthenticationException
 * <p>
 * 인증 실패 시 발생하는 예외
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 */
@Getter
public class AuthenticationException extends RuntimeException {
    
    private final ApiResponseWellnessCode errorCode;
    
    /**
     * 오류 코드와 메시지 키를 지정하는 생성자
     * 메시지 키는 다국어 처리를 위해 사용됩니다.
     * 
     * @param errorCode 오류 코드
     * @param messageKey 메시지 키 (예: "auth.userNotFound")
     */
    public AuthenticationException(ApiResponseWellnessCode errorCode, String messageKey) {
        super(messageKey); // 메시지 키를 super에 전달 (로깅용)
        this.errorCode = errorCode;
    }
}

