package com.aiwellness.customer.exception;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.aiwellness.common.exception.BusinessException;
import com.aiwellness.common.exception.WellnessCodeProvider;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Customer Server 전용 BusinessException
 * CustomerErrorCode를 사용하여 도메인 특화 예외를 처리합니다.
 */
@Getter
public class CustomerBusinessException extends BusinessException implements WellnessCodeProvider {
    
    private final ApiResponseWellnessCode wellnessCode;
    private final CustomerErrorCode customerErrorCode;
    
    public CustomerBusinessException(CustomerErrorCode customerErrorCode) {
        super(customerErrorCode.getMessageKey());
        this.customerErrorCode = customerErrorCode;
        this.wellnessCode = customerErrorCode.toApiResponseWellnessCode();
    }
    
    @Override
    public HttpStatus getHttpStatus() {
        return customerErrorCode.getStatus();
    }
    
    @Override
    public String getMessageKey() {
        return customerErrorCode.getMessageKey();
    }
}

