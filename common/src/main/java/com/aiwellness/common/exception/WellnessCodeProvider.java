package com.aiwellness.common.exception;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import org.springframework.http.HttpStatus;

/**
 * com.aiwellness.common.exception
 * <p>
 * WellnessCodeProvider
 * <p>
 * 서버별 BusinessException이 ApiResponseWellnessCode와 HttpStatus를 제공할 수 있도록 하는 인터페이스
 * 리플렉션 없이 타입 안전하게 처리하기 위함
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 17.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 17.    메가존 시스템            최초 생성
 * </pre>
 */
public interface WellnessCodeProvider {
    
    /**
     * ApiResponseWellnessCode를 반환합니다.
     * 서버별 BusinessException이 구현해야 합니다.
     * 
     * @return ApiResponseWellnessCode, 없으면 null
     */
    default ApiResponseWellnessCode getWellnessCode() {
        return null;
    }
    
    /**
     * HttpStatus를 반환합니다.
     * 서버별 BusinessException이 구현해야 합니다.
     * 
     * @return HttpStatus, 없으면 null
     */
    default HttpStatus getHttpStatus() {
        return null;
    }
    
    /**
     * 메시지 키를 반환합니다.
     * 서버별 BusinessException이 구현해야 합니다.
     * messageKey가 있으면 이를 우선 사용하고, 없으면 ApiResponseWellnessCode의 messageKey를 사용합니다.
     * 
     * @return 메시지 키, 없으면 null
     */
    default String getMessageKey() {
        return null;
    }
}

