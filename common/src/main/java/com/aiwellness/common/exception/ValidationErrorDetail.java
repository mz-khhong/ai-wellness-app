package com.aiwellness.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * com.aiwellness.common.exception
 * <p>
 * ValidationErrorDetail
 * <p>
 * Validation 에러 상세 정보를 담는 클래스
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
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorDetail {
    private String field;
    private String message;
    private Object rejectedValue;
}

