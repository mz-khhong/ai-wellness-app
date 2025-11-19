package com.aiwellness.common.response;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * com.aiwellness.common.response
 * <p>
 * ApiResponseWellness
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
 *  2025. 11. 19.    메가존 시스템            @Schema 어노테이션 추가
 * </pre>
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "API 응답 공통 구조")
public class ApiResponseWellness<T> {

  @Schema(
      description = "응답 코드 (필수)",
      example = "20000",
      requiredMode = Schema.RequiredMode.REQUIRED
  )
  private String code;

  @Schema(
      description = "응답 메시지 (필수)",
      example = "성공입니다.",
      requiredMode = Schema.RequiredMode.REQUIRED
  )
  private String message;

  @Schema(
      description = "응답 데이터",
      example = "{}"
  )
  private T data;

  @Schema(
      description = "응답 시간 (필수)",
      example = "2025-11-19 10:00:00",
      requiredMode = Schema.RequiredMode.REQUIRED
  )
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
  private LocalDateTime timestamp;

  public ApiResponseWellness(ApiResponseWellnessCode code, String message, T data) {
    this.code = code.getCode();
    this.message = message; // 이미 변환된 메시지 또는 메시지 키
    this.data = data;
    this.timestamp = LocalDateTime.now();
  }

}
