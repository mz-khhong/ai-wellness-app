package com.aiwellness.common.controller.message.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.aiwellness.common.controller.message.dto.response
 * <p>
 * MessagePublishResponse
 * <p>
 * 메시지 발행 응답 DTO
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "메시지 발행 응답 DTO")
public class MessagePublishResponse {
    
    @Schema(description = "발행된 토픽 이름", example = "wellness-message-event")
    private String topic;
    
    @Schema(description = "메시지 키", example = "user-123")
    private String key;
    
    @Schema(description = "발행 성공 여부", example = "true")
    private Boolean success;
    
    @Schema(description = "응답 메시지", example = "메시지가 성공적으로 발행되었습니다.")
    private String message;
}

