package com.aiwellness.common.code;

import lombok.Getter;

/**
 * com.aiwellness.common.code
 * <p>
 * ApiResponseWellnessCode
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
@Getter
public enum ApiResponseWellnessCode {

  SUCCESS("20000", "response.success"),
  DATA_PROCESSING_FAILURE("20001", "response.dataProcessingFailure"),
  NO_DATA_FOUND("20002", "response.nodatafound"),
  CI_VALUE_IS_ERROR("20100", "response.notMatchCi"),

  // 인증 관련 오류 코드
  AUTH_USER_NOT_FOUND("20101", "auth.userNotFound"),
  AUTH_PASSWORD_MISMATCH("20102", "auth.passwordMismatch"),
  AUTH_ACCOUNT_DISABLED("20103", "auth.accountDisabled"),
  AUTH_TOKEN_NOT_FOUND("20104", "auth.tokenNotFound"),
  AUTH_TOKEN_INVALID("20105", "auth.tokenInvalid"),
  AUTH_TOKEN_EXPIRED("20106", "auth.tokenExpired"),
  MEMBER_VALUE_IS_ERROR("20200", "response.memberNumberInvalid"),
  DOES_NOT_EXIST_DELY_ERROR("30000", "response.delyNotExistError"),
  DOES_NOT_EXIST_ORDR_RQST_ERROR("30001", "response.ordrRqstNotExistError"),
  SYSTEM_ERROR("90000", "response.systemError");

  private final String code;
  private final String messageKey;  // 메시지 키 (다국어 지원)

  ApiResponseWellnessCode(String code, String messageKey) {
    this.code = code;
    this.messageKey = messageKey;
  }
  
  /**
   * 다국어 메시지를 조회합니다.
   * MessageUtil을 통해 실제 메시지를 가져옵니다.
   */
  public String getMessage() {
    return this.messageKey; // MessageUtil에서 실제 메시지로 변환
  }
}
