package com.aiwellness.admin.exception;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.aiwellness.common.exception.BusinessException;
import com.aiwellness.common.exception.WellnessCodeProvider;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * com.aiwellness.admin.exception
 * <p>
 * AdminBusinessException
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
public class AdminBusinessException extends BusinessException implements WellnessCodeProvider {
    
    private final ApiResponseWellnessCode wellnessCode;
    private final AdminErrorCode adminErrorCode;
    
    public AdminBusinessException(AdminErrorCode adminErrorCode) {
        super(adminErrorCode.getMessageKey());
        this.adminErrorCode = adminErrorCode;
        this.wellnessCode = adminErrorCode.toApiResponseWellnessCode();
    }
    
    @Override
    public HttpStatus getHttpStatus() {
        return adminErrorCode.getStatus();
    }
    
    @Override
    public String getMessageKey() {
        return adminErrorCode.getMessageKey();
    }
}

