package com.aiwellness.common.controller.message.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

/**
 * com.aiwellness.common.controller.message.dto.request
 * <p>
 * MessagePublishRequest
 * <p>
 * 메시지 발행 요청 DTO
 * <p>
 * 테스트 편의를 위해 인증 없이 메시지를 발행할 수 있는 API의 요청 DTO입니다.
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
 * </pre>
 */
@Data
@Schema(description = "메시지 발행 요청 DTO")
public class MessagePublishRequest {
    
    @Schema(
        description = "Kafka 토픽 이름 (필수)",
        example = "wellness-message-event",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "{validation.NotBlank}")
    private String topic;
    
    @Schema(
        description = "메시지 키 (선택, 파티셔닝에 사용)",
        example = "user-123"
    )
    private String key;
    
    @Schema(
        description = "발행할 메시지 데이터 (필수)",
        example = "{\"eventType\": \"USER_CREATED\", \"userId\": 1, \"email\": \"user@example.com\"}",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "{validation.NotNull}")
    private Map<String, Object> message;
}

