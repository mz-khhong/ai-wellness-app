package com.aiwellness.common.support;

import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * com.aiwellness.common.support
 * <p>
 * ApiResponsePostProcessor
 * <p>
 * ApiResponseWellness의 메시지 키를 실제 다국어 메시지로 변환
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
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiResponsePostProcessor implements ResponseBodyAdvice<ApiResponseWellness<?>> {

    private final MessageUtil messageUtil;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // ResponseEntity로 감싸진 경우와 직접 반환하는 경우 모두 처리
        Class<?> paramType = returnType.getParameterType();
        if (ApiResponseWellness.class.isAssignableFrom(paramType)) {
            return true;
        }
        // ResponseEntity<ApiResponseWellness>인 경우
        if (org.springframework.http.ResponseEntity.class.isAssignableFrom(paramType)) {
            return returnType.getNestedParameterType() != null &&
                   ApiResponseWellness.class.isAssignableFrom(returnType.getNestedParameterType());
        }
        return false;
    }

    @Override
    public ApiResponseWellness<?> beforeBodyWrite(
            ApiResponseWellness<?> body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        if (body == null || body.getMessage() == null) {
            return body;
        }

        // 메시지 키 패턴 확인 (response.로 시작하는 경우)
        String message = body.getMessage();
        if (message != null && (message.startsWith("response.") || message.startsWith("admin.") || 
            message.startsWith("manager.") || message.startsWith("customer."))) {
            try {
                // 메시지 키를 실제 다국어 메시지로 변환
                String translatedMessage = messageUtil.getMessage(message);
                // 항상 변환된 메시지로 설정 (MessageSource가 메시지를 찾지 못하면 원본 키를 반환하지만,
                // 그래도 MessageSource를 통해 처리하는 것이 올바른 방식)
                body.setMessage(translatedMessage);
                log.debug("Translated message: {} -> {}", message, translatedMessage);
            } catch (org.springframework.context.NoSuchMessageException e) {
                // 메시지 키가 존재하지 않는 경우
                log.warn("Message key not found: {}", message);
                // 원본 메시지 키 유지
            } catch (Exception e) {
                log.warn("Failed to translate message key: {}", message, e);
                // 변환 실패 시 원본 메시지 키 유지
            }
        }

        return body;
    }
}

