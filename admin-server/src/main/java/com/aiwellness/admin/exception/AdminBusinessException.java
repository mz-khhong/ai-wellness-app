package com.aiwellness.admin.exception;

import com.aiwellness.admin.domain.code.admin.AdminCode;
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
 *  2025. 11. 20.    메가존 시스템            AdminCode로 변경
 * </pre>
 */
@Getter
public class AdminBusinessException extends BusinessException implements WellnessCodeProvider {
    
    private final ApiResponseWellnessCode wellnessCode;
    private final AdminCode adminCode;
    
    public AdminBusinessException(AdminCode adminCode) {
        super(adminCode.getMessageKey());
        if (!adminCode.isError()) {
            throw new IllegalArgumentException("AdminBusinessException은 ERROR 코드만 사용할 수 있습니다: " + adminCode);
        }
        this.adminCode = adminCode;
        this.wellnessCode = adminCode.toApiResponseWellnessCode();
    }
    
    @Override
    public HttpStatus getHttpStatus() {
        return adminCode.getHttpStatus();
    }
    
    @Override
    public String getMessageKey() {
        return adminCode.getMessageKey();
    }
}