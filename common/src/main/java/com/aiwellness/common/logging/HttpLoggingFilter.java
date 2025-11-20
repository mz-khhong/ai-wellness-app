package com.aiwellness.common.logging;

import com.aiwellness.common.code.MaskingType;
import com.aiwellness.common.config.FilterPathConfig;
import com.aiwellness.common.util.MaskingUtil;
import com.aiwellness.common.util.RegexUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * com.aiwellness.common.logging
 * <p>
 * HttpLoggingFilter
 * <p>
 * HTTP 요청/응답을 간결하게 로깅하는 Filter
 * <p>
 * 요청/응답 각각 1줄로 로깅하여 대량의 요청을 효율적으로 관리합니다.
 * ISMS-P 대응을 위해 민감한 정보 및 개인정보는 마스킹 처리합니다.
 * <p>
 * <b>지원하는 Content-Type:</b>
 * <ul>
 *   <li>application/json - JSON 요청/응답 Body 로깅</li>
 *   <li>application/xml, text/xml - XML 요청/응답 Body 로깅</li>
 *   <li>text/plain - 텍스트 요청/응답 Body 로깅</li>
 *   <li>multipart/form-data - multipart 요청의 경우 파라미터만 로깅 (Body는 로깅하지 않음)</li>
 *   <li>application/x-www-form-urlencoded - Form 파라미터 로깅</li>
 * </ul>
 * <p>
 * <b>주의사항:</b>
 * <ul>
 *   <li>multipart/form-data는 파일 업로드를 포함할 수 있어 Body 전체를 로깅하지 않습니다.</li>
 *   <li>대신 요청 파라미터만 로깅하여 성능과 보안을 고려합니다.</li>
 * </ul>
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
 *  2025. 11. 20.    메가존 시스템            HttpLoggingInterceptor 로직 통합
 * </pre>
 */
@Slf4j
@Component
public class HttpLoggingFilter extends OncePerRequestFilter {
    
    private final FilterPathConfig filterPathConfig;
    
    /**
     * HTTP 로깅 활성화 여부
     * application.yml의 logging.http.enabled로 제어 가능
     * 기본값: true
     */
    @Value("${logging.http.enabled:true}")
    private boolean httpLoggingEnabled;
    
    /**
     * HTTP 로깅 시 Body 최대 길이 (초과 시 잘림)
     * application.yml의 logging.http.max-body-length로 제어 가능
     * 기본값: 200자
     */
    @Value("${logging.http.max-body-length:200}")
    private int maxBodyLength;
    
    public HttpLoggingFilter(FilterPathConfig filterPathConfig) {
        this.filterPathConfig = filterPathConfig;
    }
    
    // 민감한 정보가 포함된 필드명 목록 (마스킹 처리)
    private static final List<String> SENSITIVE_FIELDS = Arrays.asList(
            "password", "pwd", "secret", "token", "authorization", "apiKey", "apikey"
    );
    
    // 개인정보 필드명과 마스킹 타입 매핑
    private static final List<FieldMaskingRule> PERSONAL_INFO_FIELDS = Arrays.asList(
            new FieldMaskingRule("email", MaskingType.EMAIL),
            new FieldMaskingRule("phone", MaskingType.PHONE_NUMBER),
            new FieldMaskingRule("phoneNumber", MaskingType.PHONE_NUMBER),
            new FieldMaskingRule("mobile", MaskingType.PHONE_NUMBER),
            new FieldMaskingRule("name", MaskingType.NAME),
            new FieldMaskingRule("userName", MaskingType.NAME),
            new FieldMaskingRule("cardNo", MaskingType.CREDIT_CARD),
            new FieldMaskingRule("cardNumber", MaskingType.CREDIT_CARD),
            new FieldMaskingRule("accountNo", MaskingType.ACCOUNT),
            new FieldMaskingRule("accountNumber", MaskingType.ACCOUNT),
            new FieldMaskingRule("address", MaskingType.ADDRESS),
            new FieldMaskingRule("businessNo", MaskingType.BUSINESS_NO),
            new FieldMaskingRule("businessNumber", MaskingType.BUSINESS_NO),
            new FieldMaskingRule("id", MaskingType.ID),
            new FieldMaskingRule("userId", MaskingType.ID)
    );
    
    /**
     * 필드 마스킹 규칙
     */
    private static class FieldMaskingRule {
        private final String fieldName;
        private final MaskingType maskingType;
        private final Pattern pattern;
        
        FieldMaskingRule(String fieldName, MaskingType maskingType) {
            this.fieldName = fieldName;
            this.maskingType = maskingType;
            // JSON 필드 패턴: "fieldName": "value" (RegexUtil에서 패턴 관리)
            this.pattern = Pattern.compile(
                    String.format(RegexUtil.JSON_FIELD_PATTERN, fieldName),
                    Pattern.CASE_INSENSITIVE
            );
        }
        
        String apply(String json) {
            Matcher matcher = pattern.matcher(json);
            StringBuffer result = new StringBuffer();
            
            while (matcher.find()) {
                String value = matcher.group(1);
                String maskedValue = MaskingUtil.maskType(maskingType, value);
                matcher.appendReplacement(result, 
                        String.format("\"%s\":\"%s\"", fieldName, maskedValue));
            }
            matcher.appendTail(result);
            
            return result.toString();
        }
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Request/Response Body를 읽을 수 있도록 래핑
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        
        try {
            // HTTP 로깅이 활성화되어 있고, 로깅 대상인 경우에만 로깅
            if (httpLoggingEnabled && shouldLog(request)) {
                logRequest(wrappedRequest);
            }
            
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } catch (Exception e) {
            // 예외 발생 시 로깅 (로깅 활성화 시에만)
            if (httpLoggingEnabled && shouldLog(request)) {
                log.error("[HTTP] API Error - URI: {}, Exception: {}", request.getRequestURI(), e.getMessage(), e);
            }
            throw e;
        } finally {
            // 응답 로깅 (로깅 활성화 시에만)
            if (httpLoggingEnabled && shouldLog(request)) {
                logResponse(request, wrappedResponse);
            }
            
            // Response Body를 클라이언트에 전송
            wrappedResponse.copyBodyToResponse();
        }
    }
    
    /**
     * 요청 로깅
     */
    private void logRequest(ContentCachingRequestWrapper request) {
        try {
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String queryString = request.getQueryString();
            String fullUri = queryString != null ? uri + "?" + queryString : uri;
            
            // Content-Type 확인
            String contentType = request.getContentType();
            boolean isMultipart = contentType != null && contentType.toLowerCase().startsWith("multipart/");
            boolean isFormUrlEncoded = contentType != null && contentType.toLowerCase().startsWith("application/x-www-form-urlencoded");
            
            // Request Body 또는 Parameters 추출 (간결하게)
            String bodyOrParams = "";
            if (isMultipart || isFormUrlEncoded) {
                // Form 파라미터만 간단히 요약
                int paramCount = request.getParameterMap().size();
                bodyOrParams = String.format("[FormParams: %d개]", paramCount);
            } else {
                byte[] contentAsByteArray = request.getContentAsByteArray();
                if (contentAsByteArray.length > 0) {
                    String body = new String(contentAsByteArray, StandardCharsets.UTF_8);
                    String maskedBody = maskSensitiveData(body);
                    // Body가 너무 길면 자르기 (설정 파일에서 관리)
                    if (maskedBody.length() > maxBodyLength) {
                        bodyOrParams = maskedBody.substring(0, maxBodyLength) + "...";
                    } else {
                        bodyOrParams = maskedBody;
                    }
                }
            }
            
            // 1줄로 간결하게 로깅
            if (bodyOrParams.isEmpty()) {
                log.info("[HTTP Request] {} {}", method, fullUri);
            } else {
                log.info("[HTTP Request] {} {} | Body: {}", method, fullUri, bodyOrParams);
            }
        } catch (Exception e) {
            log.warn("[HTTP] Failed to log request: {}", e.getMessage());
        }
    }
    
    /**
     * 응답 로깅
     */
    private void logResponse(HttpServletRequest request, ContentCachingResponseWrapper response) {
        try {
            String method = request.getMethod();
            String uri = request.getRequestURI();
            int status = response.getStatus();
            
            // Response Body 추출 (간결하게)
            String body = "";
            byte[] contentAsByteArray = response.getContentAsByteArray();
            if (contentAsByteArray.length > 0) {
                String responseBody = new String(contentAsByteArray, StandardCharsets.UTF_8);
                String maskedBody = maskSensitiveData(responseBody);
                // Body가 너무 길면 자르기 (설정 파일에서 관리)
                if (maskedBody.length() > maxBodyLength) {
                    body = maskedBody.substring(0, maxBodyLength) + "...";
                } else {
                    body = maskedBody;
                }
            }
            
            if (body.isEmpty()) {
                log.info("[HTTP Response] {} {} | Status: {}", method, uri, status);
            } else {
                log.info("[HTTP Response] {} {} | Status: {} | Body: {}", method, uri, status, body);
            }
        } catch (Exception e) {
            log.warn("[HTTP] Failed to log response: {}", e.getMessage());
        }
    }
    
    /**
     * ISMS-P 대응: 민감한 정보 및 개인정보 마스킹 처리
     *
     * @param json JSON 문자열
     * @return 마스킹된 JSON 문자열
     */
    private String maskSensitiveData(String json) {
        if (json == null || json.isEmpty()) {
            return json;
        }
        
        try {
            // 1. 민감한 필드 마스킹 (비밀번호, 토큰 등)
            for (String field : SENSITIVE_FIELDS) {
                Pattern pattern = Pattern.compile(
                        String.format(RegexUtil.JSON_FIELD_PATTERN, field),
                        Pattern.CASE_INSENSITIVE
                );
                json = pattern.matcher(json).replaceAll(
                        String.format("\"%s\":\"****\"", field)
                );
            }
            
            // 2. 개인정보 필드 마스킹 (이메일, 전화번호, 이름 등)
            for (FieldMaskingRule rule : PERSONAL_INFO_FIELDS) {
                json = rule.apply(json);
            }
        } catch (Exception e) {
            log.debug("[HTTP] Failed to mask sensitive data: {}", e.getMessage());
        }
        
        return json;
    }
    
    /**
     * 로깅할지 여부 확인
     */
    private boolean shouldLog(HttpServletRequest request) {
        String path = request.getRequestURI();
        // 설정 파일에서 관리하는 제외 경로 확인
        return !filterPathConfig.isExcluded(path);
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 설정 파일에서 관리하는 제외 경로 확인
        String path = request.getRequestURI();
        return filterPathConfig.isExcluded(path);
    }
}

