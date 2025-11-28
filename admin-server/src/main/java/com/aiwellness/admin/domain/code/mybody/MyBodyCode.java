package com.aiwellness.admin.domain.code.mybody;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.aiwellness.common.code.ServerResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * com.aiwellness.admin.domain.code.mybody
 * <p>
 * MyBodyCode
 * <p>
 * MY BODY 도메인 전용 코드 (ErrorCode + ResponseCode 통합)
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
public enum MyBodyCode implements ServerResponseCode {
    
    // ========== SUCCESS Codes ==========
    MYBODY_CREATE_SUCCESS("22000", "mybody.create.success", CodeType.SUCCESS),
    MYBODY_UPDATE_SUCCESS("22001", "mybody.update.success", CodeType.SUCCESS),
    MYBODY_DELETE_SUCCESS("22002", "mybody.delete.success", CodeType.SUCCESS),
    
    // ========== ERROR Codes ==========
    MYBODY_NOT_FOUND("40411", "mybody.not.found", CodeType.ERROR, HttpStatus.NOT_FOUND),
    MYBODY_ALREADY_EXISTS("40911", "mybody.already.exists", CodeType.ERROR, HttpStatus.CONFLICT),
    MYBODY_DELETE_FAILED("40011", "mybody.delete.failed", CodeType.ERROR, HttpStatus.BAD_REQUEST),
    MYBODY_UPDATE_FAILED("40012", "mybody.update.failed", CodeType.ERROR, HttpStatus.BAD_REQUEST);
    
    private final String code;
    private final String messageKey;
    private final CodeType type;
    private final HttpStatus httpStatus;
    
    /**
     * SUCCESS 코드용 생성자
     */
    MyBodyCode(String code, String messageKey, CodeType type) {
        this.code = code;
        this.messageKey = messageKey;
        this.type = type;
        this.httpStatus = null;
    }
    
    /**
     * ERROR 코드용 생성자
     */
    MyBodyCode(String code, String messageKey, CodeType type, HttpStatus httpStatus) {
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
            case MYBODY_NOT_FOUND -> ApiResponseWellnessCode.NO_DATA_FOUND;
            case MYBODY_ALREADY_EXISTS -> ApiResponseWellnessCode.DATA_PROCESSING_FAILURE;
            case MYBODY_DELETE_FAILED, MYBODY_UPDATE_FAILED -> ApiResponseWellnessCode.DATA_PROCESSING_FAILURE;
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

