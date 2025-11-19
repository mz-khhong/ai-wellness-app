package com.aiwellness.common.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * com.aiwellness.common.domain.event
 * <p>
 * DomainEvent
 * <p>
 * 도메인 이벤트 기본 인터페이스
 * 모든 도메인 이벤트는 이 인터페이스를 구현해야 합니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 */
public interface DomainEvent {
    
    /**
     * 이벤트 ID
     */
    UUID getEventId();
    
    /**
     * 이벤트 발생 시각
     */
    LocalDateTime getOccurredAt();
    
    /**
     * 이벤트 타입
     */
    String getEventType();
}

