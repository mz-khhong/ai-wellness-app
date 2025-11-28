package com.aiwellness.admin.domain.code.mymind;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.aiwellness.common.code.ServerResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * com.aiwellness.admin.domain.code.mymind
 * <p>
 * MyMindCode
 * <p>
 * MY MIND 도메인 전용 코드 (ErrorCode + ResponseCode 통합)
 * <p>
 * 헥사고날 아키텍처 원칙에 따라 도메인별로 Code를 관리합니다.
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
@Getter
public enum MyMindCode implements ServerResponseCode {
    
    // ========== SUCCESS Codes ==========
    MYMIND_CREATE_SUCCESS("24000", "mymind.create.success", CodeType.SUCCESS),
    MYMIND_UPDATE_SUCCESS("24001", "mymind.update.success", CodeType.SUCCESS),
    MYMIND_DELETE_SUCCESS("24002", "mymind.delete.success", CodeType.SUCCESS),
    
    // ========== ERROR Codes ==========
    MYMIND_NOT_FOUND("40431", "mymind.not.found", CodeType.ERROR, HttpStatus.NOT_FOUND),
    MYMIND_ALREADY_EXISTS("40931", "mymind.already.exists", CodeType.ERROR, HttpStatus.CONFLICT),
    MYMIND_DELETE_FAILED("40031", "mymind.delete.failed", CodeType.ERROR, HttpStatus.BAD_REQUEST),
    MYMIND_UPDATE_FAILED("40032", "mymind.update.failed", CodeType.ERROR, HttpStatus.BAD_REQUEST);
    
    private final String code;
    private final String messageKey;
    private final CodeType type;
    private final HttpStatus httpStatus;
    
    /**
     * SUCCESS 코드용 생성자
     */
    MyMindCode(String code, String messageKey, CodeType type) {
        this.code = code;
        this.messageKey = messageKey;
        this.type = type;
        this.httpStatus = null;
    }
    
    /**
     * ERROR 코드용 생성자
     */
    MyMindCode(String code, String messageKey, CodeType type, HttpStatus httpStatus) {
        this.code = code;
        this.messageKey = messageKey;
        this.type = type;
        this.httpStatus = httpStatus;
    }
    
    @Override
    public String getCode() {
        return code;
    }
    
    @Override
    public String getMessageKey() {
        return messageKey;
    }
    
    /**
     * 코드 타입 반환 (SUCCESS 또는 ERROR)
     */
    public CodeType getType() {
        return type;
    }
    
    /**
     * HttpStatus 반환 (ERROR 코드만)
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    
    /**
     * ERROR 코드인지 확인
     */
    public boolean isError() {
        return type == CodeType.ERROR;
    }
    
    /**
     * SUCCESS 코드인지 확인
     */
    public boolean isSuccess() {
        return type == CodeType.SUCCESS;
    }
    
    /**
     * ApiResponseWellnessCode로 변환 (ERROR 코드만)
     */
    public ApiResponseWellnessCode toApiResponseWellnessCode() {
        if (!isError() || httpStatus == null) {
            return ApiResponseWellnessCode.SUCCESS;
        }
        
        return switch (this) {
            case MYMIND_NOT_FOUND -> ApiResponseWellnessCode.NO_DATA_FOUND;
            case MYMIND_ALREADY_EXISTS -> ApiResponseWellnessCode.DATA_PROCESSING_FAILURE;
            case MYMIND_DELETE_FAILED, MYMIND_UPDATE_FAILED -> ApiResponseWellnessCode.DATA_PROCESSING_FAILURE;
            default -> ApiResponseWellnessCode.SYSTEM_ERROR;
        };
    }
    
    /**
     * 코드 타입 enum
     */
    public enum CodeType {
        SUCCESS,
        ERROR
    }
}

