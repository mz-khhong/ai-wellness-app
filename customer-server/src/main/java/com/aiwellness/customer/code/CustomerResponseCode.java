package com.aiwellness.customer.code;

import com.aiwellness.common.code.ServerResponseCode;
import lombok.Getter;

/**
 * com.aiwellness.customer.code
 * <p>
 * CustomerResponseCode
 * <p>
 * Customer 서버 전용 Response 코드
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
public enum CustomerResponseCode implements ServerResponseCode {
    
    // Customer 서버 전용 코드
    CUSTOMER_CREATE_SUCCESS("23000", "customer.create.success"),
    CUSTOMER_UPDATE_SUCCESS("23001", "customer.update.success"),
    CUSTOMER_DELETE_SUCCESS("23002", "customer.delete.success"),
    CUSTOMER_NOT_FOUND("23003", "customer.not.found"),
    CUSTOMER_ALREADY_EXISTS("23004", "customer.already.exists"),
    CUSTOMER_EMAIL_DUPLICATE("23005", "customer.email.duplicate");
    
    private final String code;
    private final String messageKey;
    
    CustomerResponseCode(String code, String messageKey) {
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

