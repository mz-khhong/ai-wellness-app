package com.aiwellness.common.domain.port.message;

/**
 * com.aiwellness.common.domain.port.message
 * <p>
 * MessageEventPort
 * <p>
 * 메시지 이벤트 발행을 위한 아웃바운드 포트 인터페이스
 * <p>
 * 헥사고날 아키텍처의 아웃바운드 포트로, Kafka 메시지 발행을 추상화합니다.
 * <p>
 * 이 포트는 도메인 계층에서 외부 시스템(ai-wellness-message-app)과의 통신을 위한 인터페이스를 제공합니다.
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
public interface MessageEventPort {
    
    /**
     * 메시지 이벤트를 Kafka 토픽에 발행합니다.
     * <p>
     * 이 메서드는 비동기로 실행되며, 발행 실패 시 예외를 발생시킵니다.
     *
     * @param topic Kafka 토픽 이름
     * @param key 메시지 키 (파티셔닝에 사용)
     * @param message 발행할 메시지 객체
     * @param <T> 메시지 타입
     * @throws RuntimeException 메시지 발행 실패 시
     */
    <T> void publish(String topic, String key, T message);
    
    /**
     * 메시지 이벤트를 Kafka 토픽에 발행합니다 (키 없이).
     * <p>
     * 이 메서드는 비동기로 실행되며, 발행 실패 시 예외를 발생시킵니다.
     *
     * @param topic Kafka 토픽 이름
     * @param message 발행할 메시지 객체
     * @param <T> 메시지 타입
     * @throws RuntimeException 메시지 발행 실패 시
     */
    <T> void publish(String topic, T message);
}

