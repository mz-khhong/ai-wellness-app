package com.aiwellness.manager.code;

import com.aiwellness.common.code.ServerResponseCode;
import lombok.Getter;

/**
 * com.aiwellness.manager.code
 * <p>
 * ManagerResponseCode
 * <p>
 * Manager 서버 전용 Response 코드
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
public enum ManagerResponseCode implements ServerResponseCode {
    
    // Manager 서버 전용 코드
    MANAGER_CREATE_SUCCESS("22000", "manager.create.success"),
    MANAGER_UPDATE_SUCCESS("22001", "manager.update.success"),
    MANAGER_DELETE_SUCCESS("22002", "manager.delete.success"),
    MANAGER_NOT_FOUND("22003", "manager.not.found"),
    MANAGER_ALREADY_EXISTS("22004", "manager.already.exists"),
    MANAGER_EMAIL_DUPLICATE("22005", "manager.email.duplicate");
    
    private final String code;
    private final String messageKey;
    
    ManagerResponseCode(String code, String messageKey) {
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

