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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * com.aiwellness.common.security
 * <p>
 * AuthenticationEntryPointHandler
 * <p>
 * 인증이 필요한 요청에 대해 인증되지 않은 경우 처리
 * <p>
 * GlobalExceptionHandler와 동일한 형식으로 응답을 반환합니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    
    private final ObjectMapper objectMapper;
    
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.warn("AuthenticationEntryPointHandler - 인증이 필요합니다: {}", request.getRequestURI());
        
        // 토큰이 없는지 확인
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        boolean tokenNotFound = bearerToken == null || bearerToken.trim().isEmpty() || !bearerToken.startsWith(BEARER_PREFIX);
        
        // 토큰이 없으면 AUTH_TOKEN_NOT_FOUND, 있으면 AUTH_TOKEN_INVALID
        ApiResponseWellnessCode errorCode = tokenNotFound 
                ? ApiResponseWellnessCode.AUTH_TOKEN_NOT_FOUND 
                : ApiResponseWellnessCode.AUTH_TOKEN_INVALID;
        
        // GlobalExceptionHandler와 동일한 형식으로 응답 생성
        var apiResponse = ApiResponseGenerator.fail(errorCode);
        
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}

