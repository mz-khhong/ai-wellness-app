package com.aiwellness.admin.exception;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * com.aiwellness.admin.exception
 * <p>
 * AdminErrorCode
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
@Getter
public enum AdminErrorCode {
    // Admin 도메인 특화 에러 코드
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "admin.not.found"),
    ADMIN_ALREADY_EXISTS(HttpStatus.CONFLICT, "admin.already.exists"),
    ADMIN_EMAIL_DUPLICATE(HttpStatus.CONFLICT, "admin.email.duplicate"),
    ADMIN_INACTIVE(HttpStatus.FORBIDDEN, "admin.inactive"),
    ADMIN_SUSPENDED(HttpStatus.FORBIDDEN, "admin.suspended"),
    ADMIN_PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "admin.password.mismatch"),
    ADMIN_DELETE_FAILED(HttpStatus.BAD_REQUEST, "admin.delete.failed"),
    ADMIN_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "admin.update.failed");
    
    private final HttpStatus status;
    private final String messageKey;  // 메시지 키 (다국어 지원)
    
    AdminErrorCode(HttpStatus status, String messageKey) {
        this.status = status;
        this.messageKey = messageKey;
    }
    
    /**
     * ApiResponseWellnessCode로 변환
     */
    public ApiResponseWellnessCode toApiResponseWellnessCode() {
        return switch (this) {
            case ADMIN_NOT_FOUND -> ApiResponseWellnessCode.NO_DATA_FOUND;
            case ADMIN_ALREADY_EXISTS, ADMIN_EMAIL_DUPLICATE -> ApiResponseWellnessCode.DATA_PROCESSING_FAILURE;
            case ADMIN_INACTIVE, ADMIN_SUSPENDED -> ApiResponseWellnessCode.CI_VALUE_IS_ERROR;
            case ADMIN_PASSWORD_MISMATCH -> ApiResponseWellnessCode.AUTH_PASSWORD_MISMATCH;
            case ADMIN_DELETE_FAILED, ADMIN_UPDATE_FAILED -> ApiResponseWellnessCode.DATA_PROCESSING_FAILURE;
            default -> ApiResponseWellnessCode.SYSTEM_ERROR;
        };
    }
}

