package com.aiwellness.manager.exception;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Manager Server 전용 ErrorCode
 * 공통 ErrorCode와 함께 사용할 수 있습니다.
 */
@Getter
public enum ManagerErrorCode {
    // Manager 도메인 특화 에러 코드
    MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "manager.not.found"),
    MANAGER_ALREADY_EXISTS(HttpStatus.CONFLICT, "manager.already.exists"),
    MANAGER_EMAIL_DUPLICATE(HttpStatus.CONFLICT, "manager.email.duplicate"),
    MANAGER_INACTIVE(HttpStatus.FORBIDDEN, "manager.inactive"),
    MANAGER_SUSPENDED(HttpStatus.FORBIDDEN, "manager.suspended"),
    MANAGER_PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "manager.password.mismatch"),
    MANAGER_DELETE_FAILED(HttpStatus.BAD_REQUEST, "manager.delete.failed"),
    MANAGER_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "manager.update.failed"),
    MANAGER_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "manager.not.authorized"),
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "customer.not.found"),
    CUSTOMER_FACILITY_GROUP_MISMATCH(HttpStatus.FORBIDDEN, "customer.facility.group.mismatch");
    
    private final HttpStatus status;
    private final String messageKey;  // 메시지 키 (다국어 지원)
    
    ManagerErrorCode(HttpStatus status, String messageKey) {
        this.status = status;
        this.messageKey = messageKey;
    }
    
    /**
     * ApiResponseWellnessCode로 변환
     */
    public ApiResponseWellnessCode toApiResponseWellnessCode() {
        return switch (this) {
            case MANAGER_NOT_FOUND, CUSTOMER_NOT_FOUND -> ApiResponseWellnessCode.NO_DATA_FOUND;
            case MANAGER_ALREADY_EXISTS, MANAGER_EMAIL_DUPLICATE -> ApiResponseWellnessCode.DATA_PROCESSING_FAILURE;
            case MANAGER_INACTIVE, MANAGER_SUSPENDED, MANAGER_NOT_AUTHORIZED, CUSTOMER_FACILITY_GROUP_MISMATCH -> ApiResponseWellnessCode.CI_VALUE_IS_ERROR;
            case MANAGER_PASSWORD_MISMATCH -> ApiResponseWellnessCode.AUTH_PASSWORD_MISMATCH;
            case MANAGER_DELETE_FAILED, MANAGER_UPDATE_FAILED -> ApiResponseWellnessCode.DATA_PROCESSING_FAILURE;
            default -> ApiResponseWellnessCode.SYSTEM_ERROR;
        };
    }
}

