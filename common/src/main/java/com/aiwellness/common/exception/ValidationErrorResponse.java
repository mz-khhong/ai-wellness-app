package com.aiwellness.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * com.aiwellness.common.exception
 * <p>
 * ValidationErrorResponse
 * <p>
 * Validation 에러 응답을 담는 클래스
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    private String requestUri;      // 요청 URI
    private String requestMethod;   // HTTP Method (GET, POST, PUT, DELETE 등)
    private String handlerClass;    // 컨트롤러 클래스명
    private String handlerMethod;    // 컨트롤러 메소드명
    private List<ValidationErrorDetail> errors;
}

