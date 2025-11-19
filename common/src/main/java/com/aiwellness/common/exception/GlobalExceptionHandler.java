package com.aiwellness.common.exception;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.security.AuthenticationException;
import com.aiwellness.common.support.ApiResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseWellness<Void>> handleBusinessException(BusinessException e) {
        log.error("BusinessException: {}", e.getMessage(), e);

        if (e instanceof WellnessCodeProvider provider) {
            ApiResponseWellnessCode wellnessCode = provider.getWellnessCode();
            HttpStatus httpStatus = provider.getHttpStatus();
            String messageKey = provider.getMessageKey();
            
            if (wellnessCode != null && httpStatus != null) {
                if (messageKey != null && !messageKey.isEmpty()) {
                    return ApiResponseGenerator.fail(wellnessCode, messageKey, httpStatus);
                } else {
                    return ApiResponseGenerator.fail(wellnessCode, httpStatus);
                }
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseGenerator.fail(ApiResponseWellnessCode.SYSTEM_ERROR));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseWellness<ValidationErrorResponse>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {
        log.error("MethodArgumentNotValidException: {}", e.getMessage());
        
        // 요청 정보 추출
        String requestUri = request.getRequestURI();
        String requestMethod = request.getMethod();
        String handlerClass = null;
        String handlerMethod = null;
        
        // 어떤 컨트롤러 메서드에서 발생했는지 확인
        Object handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        if (handler instanceof HandlerMethod handlerMethodObj) {
            handlerClass = handlerMethodObj.getBeanType().getSimpleName();
            handlerMethod = handlerMethodObj.getMethod().getName();
            log.debug("Validation failed in: {}.{}", handlerClass, handlerMethod);
        }
        
        // 입력받은 DTO 전체 로그 출력 (디버깅용)
        Object dto = e.getBindingResult().getTarget();
        if (dto != null) {
            log.debug("Input DTO: {}", dto.getClass().getSimpleName());
        }
        
        // Validation 에러 상세 정보 수집
        List<ValidationErrorDetail> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationErrorDetail(
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue()
                ))
                .collect(Collectors.toList());
        
        // 요청 정보를 포함한 ValidationErrorResponse 생성
        ValidationErrorResponse errorResponse = ValidationErrorResponse.builder()
                .requestUri(requestUri)
                .requestMethod(requestMethod)
                .handlerClass(handlerClass)
                .handlerMethod(handlerMethod)
                .errors(errors)
                .build();
        
        log.debug("Validation errors: uri={}, method={}, handler={}.{}, field={}, count={}", 
                requestUri, requestMethod, handlerClass, handlerMethod,
                errors.stream().map(ValidationErrorDetail::getField).collect(Collectors.joining(", ")),
                errors.size());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseGenerator.fail(
                        ApiResponseWellnessCode.DATA_PROCESSING_FAILURE,
                        errorResponse
                ));
    }
    
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponseWellness<ValidationErrorResponse>> handleBindException(
            BindException e,
            HttpServletRequest request) {
        log.error("BindException: {}", e.getMessage());
        
        // 요청 정보 추출
        String requestUri = request.getRequestURI();
        String requestMethod = request.getMethod();
        String handlerClass = null;
        String handlerMethod = null;
        
        // 어떤 컨트롤러 메서드에서 발생했는지 확인
        Object handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        if (handler instanceof HandlerMethod handlerMethodObj) {
            handlerClass = handlerMethodObj.getBeanType().getSimpleName();
            handlerMethod = handlerMethodObj.getMethod().getName();
            log.debug("Validation failed in: {}.{}", handlerClass, handlerMethod);
        }
        
        // 입력받은 DTO 전체 로그 출력 (디버깅용)
        Object dto = e.getBindingResult().getTarget();
        if (dto != null) {
            log.debug("Input DTO: {}", dto.getClass().getSimpleName());
        }
        
        // Validation 에러 상세 정보 수집
        List<ValidationErrorDetail> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationErrorDetail(
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue()
                ))
                .collect(Collectors.toList());
        
        // 요청 정보를 포함한 ValidationErrorResponse 생성
        ValidationErrorResponse errorResponse = ValidationErrorResponse.builder()
                .requestUri(requestUri)
                .requestMethod(requestMethod)
                .handlerClass(handlerClass)
                .handlerMethod(handlerMethod)
                .errors(errors)
                .build();
        
        log.debug("Validation errors: uri={}, method={}, handler={}.{}, field={}, count={}", 
                requestUri, requestMethod, handlerClass, handlerMethod,
                errors.stream().map(ValidationErrorDetail::getField).collect(Collectors.joining(", ")),
                errors.size());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseGenerator.fail(
                        ApiResponseWellnessCode.DATA_PROCESSING_FAILURE,
                        errorResponse
                ));
    }
    
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponseWellness<Void>> handleAuthenticationException(AuthenticationException e) {
        log.warn("AuthenticationException: {} - errorCode: {}", e.getMessage(), e.getErrorCode());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponseGenerator.fail(e.getErrorCode()));
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseWellness<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseGenerator.fail(ApiResponseWellnessCode.DATA_PROCESSING_FAILURE));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseWellness<Void>> handleException(Exception e) {
        log.error("Unexpected exception: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseGenerator.fail(ApiResponseWellnessCode.SYSTEM_ERROR));
    }
}

