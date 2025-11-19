package com.aiwellness.common.support;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.aiwellness.common.response.ApiResponseWellness;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * com.aiwellness.common.support
 * <p>
 * ApiResponseBodyAdvice
 * <p>
 * ApiResponseWellness를 ResponseEntity로 변환하여 HttpStatus를 제어할 수 있도록 합니다.
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
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<ApiResponseWellness<?>> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return ApiResponseWellness.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public ApiResponseWellness<?> beforeBodyWrite(
            ApiResponseWellness<?> body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        if (body == null) {
            return null;
        }

        // timestamp 설정
        if (body.getTimestamp() == null) {
            body.setTimestamp(LocalDateTime.now());
        }

        // HttpStatus 결정
        HttpStatus httpStatus = determineHttpStatus(body);
        response.setStatusCode(httpStatus);

        log.debug("ApiResponseWellness converted - Code: {}, HttpStatus: {}", body.getCode(), httpStatus);

        return body;
    }

    /**
     * ApiResponseWellness의 code를 기반으로 HttpStatus를 결정합니다.
     * ApiResponseWellnessCode enum을 사용하여 매핑합니다.
     */
    private HttpStatus determineHttpStatus(ApiResponseWellness<?> body) {
        String code = body.getCode();
        
        if (code == null) {
            return HttpStatus.OK;
        }

        // ApiResponseWellnessCode enum에서 해당 코드 찾기
        try {
            for (ApiResponseWellnessCode wellnessCode : ApiResponseWellnessCode.values()) {
                if (wellnessCode.getCode().equals(code)) {
                    return mapWellnessCodeToHttpStatus(wellnessCode);
                }
            }
        } catch (Exception e) {
            log.warn("Failed to determine HttpStatus for code: {}", code, e);
        }

        // 기본값: 성공 응답
        return HttpStatus.OK;
    }
    
    /**
     * ApiResponseWellnessCode를 HttpStatus로 매핑합니다.
     */
    private HttpStatus mapWellnessCodeToHttpStatus(ApiResponseWellnessCode wellnessCode) {
        return switch (wellnessCode) {
            case SUCCESS -> HttpStatus.OK;
            case DATA_PROCESSING_FAILURE, CI_VALUE_IS_ERROR, MEMBER_VALUE_IS_ERROR -> HttpStatus.BAD_REQUEST;
            case NO_DATA_FOUND, DOES_NOT_EXIST_DELY_ERROR, DOES_NOT_EXIST_ORDR_RQST_ERROR -> HttpStatus.NOT_FOUND;
            case AUTH_USER_NOT_FOUND, AUTH_PASSWORD_MISMATCH, AUTH_ACCOUNT_DISABLED, 
                 AUTH_TOKEN_NOT_FOUND, AUTH_TOKEN_INVALID, AUTH_TOKEN_EXPIRED -> HttpStatus.UNAUTHORIZED;
            case SYSTEM_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}

