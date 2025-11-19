package com.aiwellness.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class SwaggerSecurityConfig {
    
    /**
     * Swagger UI 접근을 위한 Security 설정
     * 각 서버의 SecurityConfig에서 이 메서드를 참고하여 Swagger 경로를 permitAll에 추가해야 합니다.
     */
    public static void configureSwaggerSecurity(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                ).permitAll()
        );
    }
    
    /**
     * WebSecurityCustomizer를 사용하는 경우
     */
    public static WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                );
    }
}

