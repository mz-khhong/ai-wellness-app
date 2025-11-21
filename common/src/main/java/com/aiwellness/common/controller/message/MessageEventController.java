package com.aiwellness.common.controller.message;

import com.aiwellness.common.controller.message.dto.request.MessagePublishRequest;
import com.aiwellness.common.controller.message.dto.response.MessagePublishResponse;
import com.aiwellness.common.domain.port.message.MessageEventPort;
import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.support.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * com.aiwellness.common.controller.message
 * <p>
 * MessageEventController
 * <p>
 * 메시지 이벤트 발행 API 컨트롤러 (Infrastructure/Web Adapter 계층)
 * <p>
 * 테스트 편의를 위해 인증 없이 메시지를 발행할 수 있는 API를 제공합니다.
 * <p>
 * 이 API는 ai-wellness-message-app과의 상호 호출을 위한 테스트용 엔드포인트입니다.
 * <p>
 * <b>주의:</b> 프로덕션 환경에서는 인증을 추가하거나 제거해야 할 수 있습니다.
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
@Slf4j
@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Tag(name = "Message Event", description = "메시지 이벤트 발행 API (테스트용, 인증 없음)")
public class MessageEventController {
    
    private final MessageEventPort messageEventPort;
    
    /**
     * 메시지 이벤트를 Kafka 토픽에 발행합니다.
     * <p>
     * 테스트 편의를 위해 인증 없이 접근 가능합니다.
     *
     * @param request 메시지 발행 요청
     * @return 메시지 발행 결과
     */
    @PostMapping("/publish")
    @Operation(
        summary = "메시지 이벤트 발행",
        description = "Kafka 토픽에 메시지 이벤트를 발행합니다. (테스트용, 인증 없음)"
    )
    public ResponseEntity<ApiResponseWellness<MessagePublishResponse>> publishMessage(
            @Valid @RequestBody MessagePublishRequest request) {
        
        log.info("[MessageEventController] Publishing message. Topic: {}, Key: {}", 
                request.getTopic(), request.getKey());
        
        try {
            // 메시지 발행
            if (request.getKey() != null && !request.getKey().isEmpty()) {
                messageEventPort.publish(request.getTopic(), request.getKey(), request.getMessage());
            } else {
                messageEventPort.publish(request.getTopic(), request.getMessage());
            }
            
            MessagePublishResponse response = MessagePublishResponse.builder()
                    .topic(request.getTopic())
                    .key(request.getKey())
                    .success(true)
                    .message("메시지가 성공적으로 발행되었습니다.")
                    .build();
            
            return ResponseEntity.ok(ApiResponseGenerator.success(response));
            
        } catch (Exception e) {
            log.error("[MessageEventController] Failed to publish message. Topic: {}, Key: {}", 
                    request.getTopic(), request.getKey(), e);
            
            MessagePublishResponse response = MessagePublishResponse.builder()
                    .topic(request.getTopic())
                    .key(request.getKey())
                    .success(false)
                    .message("메시지 발행에 실패했습니다: " + e.getMessage())
                    .build();
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseGenerator.fail(ApiResponseWellnessCode.SYSTEM_ERROR, response));
        }
    }
}

