package com.aiwellness.common.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * com.aiwellness.common.config
 * <p>
 * MessageSourceConfig
 * <p>
 * 다국어 지원을 위한 MessageSource 설정
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
@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {

    /**
     * MessageSource 설정
     * 
     * 공통 메시지와 서버별 비즈니스 메시지를 분리하여 관리합니다.
     * 
     * 우선순위:
     * 1. 서버별 메시지 (classpath:messages/message) - 비즈니스 메시지만 포함
     * 2. 공통 메시지 (classpath:messages/common-message) - response.*, auth.*, validation.*
     * 
     * 각 서버의 message.properties에는 비즈니스 메시지만 포함하고,
     * 공통 메시지는 common 모듈의 common-message.properties에만 정의합니다.
     * 
     * 여러 basename을 설정하면 먼저 설정한 것이 우선순위가 높습니다.
     * 
     * Accept-Language 헤더를 통해 언어를 결정합니다.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        
        // 여러 basename을 설정 (먼저 설정한 것이 우선순위 높음)
        // 1. 서버별 메시지 (비즈니스 메시지) - 우선순위 높음
        // 2. 공통 메시지 (response.*, auth.*, validation.*) - 우선순위 낮음
        messageSource.setBasenames(
                "classpath:messages/message",           // 서버별 비즈니스 메시지
                "classpath:messages/common-message"     // 공통 메시지
        );
        
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3600); // 1시간 캐시
        messageSource.setFallbackToSystemLocale(true); // 시스템 Locale로 fallback 허용
        messageSource.setUseCodeAsDefaultMessage(false); // 메시지 키가 없으면 NoSuchMessageException 발생
        
        return messageSource;
    }

    /**
     * LocaleResolver 설정
     * Accept-Language 헤더를 기반으로 Locale을 결정
     * Accept-Language 헤더가 없으면 기본값(한국어) 사용
     */
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.KOREAN); // 기본 언어: 한국어
        // 지원하는 Locale 목록 설정 (없으면 모든 Locale 허용)
        localeResolver.setSupportedLocales(java.util.Arrays.asList(Locale.KOREAN, Locale.ENGLISH));
        return localeResolver;
    }

    /**
     * LocaleChangeInterceptor 설정
     * lang 파라미터를 통해 언어 변경 가능
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * Validation 메시지도 다국어 지원
     */
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}

