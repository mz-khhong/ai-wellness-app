package com.aiwellness.common.support;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.aiwellness.common.code.ServerResponseCode;
import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * com.aiwellness.common.support
 * <p>
 * ApiResponseGenerator
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
public class ApiResponseGenerator {

  private static MessageUtil messageUtil;
  private final ApplicationContext applicationContext;

  /**
   * MessageUtil을 정적 필드에 주입
   */
  @PostConstruct
  public void init() {
    if (messageUtil == null) {
      messageUtil = applicationContext.getBean(MessageUtil.class);
    }
  }

  /**
   * 성공 응답 (200 OK)
   */
  public static ApiResponseWellness<Void> success() {
    return new ApiResponseWellness<>(
        ApiResponseWellnessCode.SUCCESS, 
        getMessage(ApiResponseWellnessCode.SUCCESS.getMessageKey()), 
        null
    );
  }

  /**
   * 성공 응답 (200 OK) with data
   */
  public static <D> ApiResponseWellness<D> success(D data) {
    return new ApiResponseWellness<>(
        ApiResponseWellnessCode.SUCCESS, 
        getMessage(ApiResponseWellnessCode.SUCCESS.getMessageKey()), 
        data
    );
  }

  /**
   * 성공 응답 with custom message key
   */
  public static <D> ApiResponseWellness<D> success(String messageKey, D data) {
    return new ApiResponseWellness<>(
        ApiResponseWellnessCode.SUCCESS, 
        getMessage(messageKey), 
        data
    );
  }

  /**
   * 성공 응답 with ServerResponseCode
   */
  public static <D> ApiResponseWellness<D> success(ServerResponseCode responseCode, D data) {
    return new ApiResponseWellness<>(
        ApiResponseWellnessCode.SUCCESS, 
        getMessage(responseCode.getMessageKey()), 
        data
    );
  }

  /**
   * 성공 응답 with custom HttpStatus
   */
  public static <D> ResponseEntity<ApiResponseWellness<D>> success(D data, HttpStatus httpStatus) {
    ApiResponseWellness<D> response = new ApiResponseWellness<>(
        ApiResponseWellnessCode.SUCCESS, 
        getMessage(ApiResponseWellnessCode.SUCCESS.getMessageKey()), 
        data
    );
    return ResponseEntity.status(httpStatus).body(response);
  }

  /**
   * 성공 응답 with custom message key and HttpStatus
   */
  public static <D> ResponseEntity<ApiResponseWellness<D>> success(String messageKey, D data, HttpStatus httpStatus) {
    ApiResponseWellness<D> response = new ApiResponseWellness<>(
        ApiResponseWellnessCode.SUCCESS, 
        getMessage(messageKey), 
        data
    );
    return ResponseEntity.status(httpStatus).body(response);
  }

  /**
   * 성공 응답 with ServerResponseCode and HttpStatus
   */
  public static <D> ResponseEntity<ApiResponseWellness<D>> success(ServerResponseCode responseCode, D data, HttpStatus httpStatus) {
    ApiResponseWellness<D> response = new ApiResponseWellness<>(
        ApiResponseWellnessCode.SUCCESS, 
        getMessage(responseCode.getMessageKey()), 
        data
    );
    return ResponseEntity.status(httpStatus).body(response);
  }

  /**
   * 실패 응답
   */
  public static ApiResponseWellness<Void> fail() {
    return new ApiResponseWellness<>(
        ApiResponseWellnessCode.SYSTEM_ERROR, 
        getMessage(ApiResponseWellnessCode.SYSTEM_ERROR.getMessageKey()), 
        null
    );
  }

  /**
   * 실패 응답 with code
   */
  public static ApiResponseWellness<Void> fail(ApiResponseWellnessCode code) {
    return new ApiResponseWellness<>(
        code, 
        getMessage(code.getMessageKey()), 
        null
    );
  }

  /**
   * 실패 응답 with ServerResponseCode
   */
  public static ApiResponseWellness<Void> fail(ServerResponseCode responseCode) {
    return new ApiResponseWellness<>(
        ApiResponseWellnessCode.SYSTEM_ERROR, 
        getMessage(responseCode.getMessageKey()), 
        null
    );
  }

  /**
   * 실패 응답 with code and data
   */
  public static <D> ApiResponseWellness<D> fail(ApiResponseWellnessCode code, D data) {
    return new ApiResponseWellness<>(
        code, 
        getMessage(code.getMessageKey()), 
        data
    );
  }

  /**
   * 실패 응답 with code and custom HttpStatus
   */
  public static <D> ResponseEntity<ApiResponseWellness<D>> fail(ApiResponseWellnessCode code, HttpStatus httpStatus) {
    ApiResponseWellness<D> response = new ApiResponseWellness<>(
        code, 
        getMessage(code.getMessageKey()), 
        null
    );
    return ResponseEntity.status(httpStatus).body(response);
  }

  /**
   * 실패 응답 with code, messageKey and custom HttpStatus
   */
  public static <D> ResponseEntity<ApiResponseWellness<D>> fail(ApiResponseWellnessCode code, String messageKey, HttpStatus httpStatus) {
    ApiResponseWellness<D> response = new ApiResponseWellness<>(
        code, 
        getMessage(messageKey), 
        null
    );
    return ResponseEntity.status(httpStatus).body(response);
  }

  /**
   * 메시지 키로부터 실제 메시지 조회
   */
  private static String getMessage(String messageKey) {
    if (messageUtil != null) {
      return messageUtil.getMessage(messageKey);
    }
    // MessageUtil이 아직 초기화되지 않은 경우 기본값 반환
    return messageKey;
  }
}
