package com.aiwellness.customer.exception;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Customer Server 전용 ErrorCode
 * 공통 ErrorCode와 함께 사용할 수 있습니다.
 */
@Getter
public enum CustomerErrorCode {
    // Customer 도메인 특화 에러 코드
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "customer.not.found"),
    CUSTOMER_ALREADY_EXISTS(HttpStatus.CONFLICT, "customer.already.exists"),
    CUSTOMER_EMAIL_DUPLICATE(HttpStatus.CONFLICT, "customer.email.duplicate"),
    CUSTOMER_INACTIVE(HttpStatus.FORBIDDEN, "customer.inactive"),
    CUSTOMER_SUSPENDED(HttpStatus.FORBIDDEN, "customer.suspended"),
    CUSTOMER_PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "customer.password.mismatch"),
    CUSTOMER_DELETE_FAILED(HttpStatus.BAD_REQUEST, "customer.delete.failed"),
    CUSTOMER_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "customer.update.failed"),
    CUSTOMER_REGISTRATION_FAILED(HttpStatus.BAD_REQUEST, "customer.registration.failed"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "entity.not.found"),
    ENTITY_DUPLICATE(HttpStatus.CONFLICT, "entity.duplicate");
    
    private final HttpStatus status;
    private final String messageKey;  // 메시지 키 (다국어 지원)
    
    CustomerErrorCode(HttpStatus status, String messageKey) {
        this.status = status;
        this.messageKey = messageKey;
    }
    
    /**
     * ApiResponseWellnessCode로 변환
     */
    public ApiResponseWellnessCode toApiResponseWellnessCode() {
        return switch (this) {
            case CUSTOMER_NOT_FOUND, ENTITY_NOT_FOUND -> ApiResponseWellnessCode.NO_DATA_FOUND;
            case CUSTOMER_ALREADY_EXISTS, CUSTOMER_EMAIL_DUPLICATE, ENTITY_DUPLICATE -> ApiResponseWellnessCode.DATA_PROCESSING_FAILURE;
            case CUSTOMER_INACTIVE, CUSTOMER_SUSPENDED -> ApiResponseWellnessCode.CI_VALUE_IS_ERROR;
            case CUSTOMER_PASSWORD_MISMATCH -> ApiResponseWellnessCode.AUTH_PASSWORD_MISMATCH;
            case CUSTOMER_DELETE_FAILED, CUSTOMER_UPDATE_FAILED, CUSTOMER_REGISTRATION_FAILED -> ApiResponseWellnessCode.DATA_PROCESSING_FAILURE;
            default -> ApiResponseWellnessCode.SYSTEM_ERROR;
        };
    }
}

