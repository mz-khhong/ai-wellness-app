package com.aiwellness.common.security;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.aiwellness.common.support.ApiResponseGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * com.aiwellness.common.security
 * <p>
 * AccessDeniedHandlerImpl
 * <p>
 * 인증은 되었지만 권한이 없는 요청에 대해 처리
 * <p>
 * GlobalExceptionHandler와 동일한 형식으로 응답을 반환합니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 20.    메가존 시스템            최초 생성
 * </pre>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    
    private final ObjectMapper objectMapper;
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        log.warn("AccessDeniedHandlerImpl - 접근 권한이 없습니다: {} - {}",
                request.getRequestURI(), accessDeniedException.getMessage());
        
        // GlobalExceptionHandler와 동일한 형식으로 응답 생성
        var apiResponse = ApiResponseGenerator.fail(ApiResponseWellnessCode.AUTH_ACCESS_DENIED);
        
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}

