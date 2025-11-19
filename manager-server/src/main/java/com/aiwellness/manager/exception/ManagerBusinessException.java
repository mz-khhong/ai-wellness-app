package com.aiwellness.manager.exception;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.aiwellness.common.exception.BusinessException;
import com.aiwellness.common.exception.WellnessCodeProvider;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Manager Server 전용 BusinessException
 * ManagerErrorCode를 사용하여 도메인 특화 예외를 처리합니다.
 */
@Getter
public class ManagerBusinessException extends BusinessException implements WellnessCodeProvider {
    
    private final ApiResponseWellnessCode wellnessCode;
    private final ManagerErrorCode managerErrorCode;
    
    public ManagerBusinessException(ManagerErrorCode managerErrorCode) {
        super(managerErrorCode.getMessageKey());
        this.managerErrorCode = managerErrorCode;
        this.wellnessCode = managerErrorCode.toApiResponseWellnessCode();
    }
    
    @Override
    public HttpStatus getHttpStatus() {
        return managerErrorCode.getStatus();
    }
    
    @Override
    public String getMessageKey() {
        return managerErrorCode.getMessageKey();
    }
}

