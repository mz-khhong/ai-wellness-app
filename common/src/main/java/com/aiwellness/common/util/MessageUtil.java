package com.aiwellness.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * com.aiwellness.common.util
 * <p>
 * MessageUtil
 * <p>
 * 다국어 메시지를 조회하는 유틸리티 클래스
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
@Component
@RequiredArgsConstructor
public class MessageUtil {
    
    private final MessageSource messageSource;
    
    /**
     * 현재 Locale에 맞는 메시지 조회
     */
    public String getMessage(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, null, locale);
    }
    
    /**
     * 특정 Locale에 맞는 메시지 조회
     */
    public String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }
    
    /**
     * 파라미터가 있는 메시지 조회
     */
    public String getMessage(String code, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, locale);
    }
    
    /**
     * Request의 Accept-Language 헤더를 기반으로 Locale 결정
     */
    public static Locale getLocaleFromRequest() {
        try {
            ServletRequestAttributes attributes = 
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String acceptLanguage = request.getHeader("Accept-Language");
                
                if (acceptLanguage != null) {
                    if (acceptLanguage.startsWith("en")) {
                        return Locale.ENGLISH;
                    } else if (acceptLanguage.startsWith("ko")) {
                        return Locale.KOREAN;
                    }
                }
            }
        } catch (Exception e) {
            // 예외 발생 시 기본 Locale 반환
        }
        return Locale.KOREAN; // 기본값: 한국어
    }
}

