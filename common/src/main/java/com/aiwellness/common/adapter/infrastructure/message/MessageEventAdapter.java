package com.aiwellness.common.adapter.infrastructure.message;

import com.aiwellness.common.domain.port.message.MessageEventPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

/**
 * com.aiwellness.common.adapter.infrastructure.message
 * <p>
 * MessageEventAdapter
 * <p>
 * Kafka 메시지 발행을 위한 인프라스트럭처 어댑터
 * <p>
 * 헥사고날 아키텍처의 아웃바운드 어댑터로, MessageEventPort를 구현합니다.
 * <p>
 * 이 어댑터는 Kafka Producer를 사용하여 메시지를 발행합니다.
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
@Repository
@RequiredArgsConstructor
public class MessageEventAdapter implements MessageEventPort {
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    @Override
    public <T> void publish(String topic, String key, T message) {
        try {
            String messageJson = objectMapper.writeValueAsString(message);
            
            log.info("[MessageEventAdapter] Publishing message to topic: {}, key: {}, message: {}", 
                    topic, key, messageJson);
            
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, messageJson);
            
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("[MessageEventAdapter] Message published successfully. Topic: {}, Key: {}, Offset: {}, Partition: {}", 
                            topic, key, result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
                } else {
                    log.error("[MessageEventAdapter] Failed to publish message. Topic: {}, Key: {}, Error: {}", 
                            topic, key, ex.getMessage(), ex);
                    // 비동기 콜백에서 예외를 던지면 무시되므로, 로그만 남깁니다.
                    // 실제 에러 처리는 Controller에서 처리합니다.
                }
            });
            
            // 동기적으로 결과를 기다려서 즉시 에러를 확인
            try {
                SendResult<String, String> result = future.get();
                log.debug("[MessageEventAdapter] Message sent synchronously. Topic: {}, Key: {}", topic, key);
            } catch (Exception e) {
                log.error("[MessageEventAdapter] Synchronous send failed. Topic: {}, Key: {}", topic, key, e);
                throw new RuntimeException("Failed to publish message to Kafka: " + e.getMessage(), e);
            }
        } catch (JsonProcessingException e) {
            log.error("[MessageEventAdapter] Failed to serialize message. Topic: {}, Key: {}", 
                    topic, key, e);
            throw new RuntimeException("Failed to serialize message", e);
        }
    }
    
    @Override
    public <T> void publish(String topic, T message) {
        publish(topic, null, message);
    }
}

