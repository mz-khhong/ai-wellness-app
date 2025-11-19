package com.aiwellness.common.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * com.aiwellness.common.config
 * <p>
 * FeignConfig
 * <p>
 * Feign Client 전역 설정
 * 외부/내부 서비스 연동을 위한 Feign Client 설정
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 18.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 18.    메가존 시스템            최초 생성
 * </pre>
 */
@Slf4j
@Configuration
@EnableFeignClients(basePackages = "com.aiwellness.**.adapter.infrastructure.client")
public class FeignConfig {
    
    /**
     * Feign 로깅 레벨 설정
     * FULL: 요청/응답 헤더, 본문 모두 로깅
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    
    /**
     * 요청 인터셉터: JWT 토큰 및 Accept-Language 헤더 전달
     * 내부 서비스 간 호출 시 인증 토큰과 언어 설정을 자동으로 전달
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = 
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                
                // JWT 토큰 전달
                String authorization = request.getHeader("Authorization");
                if (authorization != null) {
                    requestTemplate.header("Authorization", authorization);
                }
                
                // Accept-Language 헤더 전달
                String acceptLanguage = request.getHeader("Accept-Language");
                if (acceptLanguage != null) {
                    requestTemplate.header("Accept-Language", acceptLanguage);
                } else {
                    // 기본값: 한국어
                    requestTemplate.header("Accept-Language", "ko");
                }
            }
        };
    }
    
    /**
     * 에러 디코더: Feign 에러 처리
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
    
    /**
     * Feign 에러 디코더 구현
     */
    @Slf4j
    public static class FeignErrorDecoder implements ErrorDecoder {
        
        private final ErrorDecoder defaultErrorDecoder = new Default();
        
        @Override
        public Exception decode(String methodKey, feign.Response response) {
            log.error("Feign Client Error - Method: {}, Status: {}, Reason: {}", 
                methodKey, response.status(), response.reason());
            
            // 4xx 에러는 기본 디코더 사용
            if (response.status() >= 400 && response.status() < 500) {
                return defaultErrorDecoder.decode(methodKey, response);
            }
            
            // 5xx 에러는 커스텀 처리
            return new RuntimeException(
                String.format("Feign Client Error: %s - Status: %d, Reason: %s", 
                    methodKey, response.status(), response.reason())
            );
        }
    }
}

