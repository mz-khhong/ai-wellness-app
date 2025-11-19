package com.aiwellness.admin.code;

import com.aiwellness.common.code.ServerResponseCode;
import lombok.Getter;

/**
 * com.aiwellness.admin.code
 * <p>
 * AdminResponseCode
 * <p>
 * Admin 서버 전용 Response 코드
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
public enum AdminResponseCode implements ServerResponseCode {
    
    // Admin 서버 전용 코드
    ADMIN_CREATE_SUCCESS("21000", "admin.create.success"),
    ADMIN_UPDATE_SUCCESS("21001", "admin.update.success"),
    ADMIN_DELETE_SUCCESS("21002", "admin.delete.success"),
    ADMIN_NOT_FOUND("21003", "admin.not.found"),
    ADMIN_ALREADY_EXISTS("21004", "admin.already.exists"),
    ADMIN_EMAIL_DUPLICATE("21005", "admin.email.duplicate");
    
    private final String code;
    private final String messageKey;
    
    AdminResponseCode(String code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }
    
    @Override
    public String getCode() {
        return code;
    }
    
    @Override
    public String getMessageKey() {
        return messageKey;
    }
}

