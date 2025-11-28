package com.aiwellness.common.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * com.aiwellness.common.config
 * <p>
 * SsoTokenManagerConfig
 * <p>
 * SSO 토큰 관리를 위한 RestTemplate 설정
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 */
@Configuration
public class SsoTokenManagerConfig {
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }
}