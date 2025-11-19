package com.aiwellness.customer.domain.model.membership;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * com.aiwellness.customer.domain.model.membership
 * <p>
 * MembershipPoint
 * <p>
 * 멤버십 포인트 도메인 모델
 * <p>
 * 데이터베이스 테이블: membership_points (1:1 매핑)
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 18.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 18.    메가존 시스템            최초 생성
 * </pre>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    description = "멤버십 포인트 도메인 모델 (테이블: membership_points)",
    example = "MembershipPoint(id=1, customerId=1, points=1000)"
)
public class MembershipPoint {
    
    @Schema(
        description = "포인트 ID (Primary Key, 컬럼: id)",
        example = "1"
    )
    private Long id;
    
    @Schema(
        description = "고객 ID (컬럼: customer_id, Foreign Key: customers.id)",
        example = "1"
    )
    private Long customerId;
    
    @Schema(
        description = "포인트 (컬럼: points)",
        example = "1000"
    )
    private Integer points;
    
    @Schema(
        description = "생성 일시 (컬럼: created_at, NOT NULL)",
        example = "2025-11-17T10:00:00"
    )
    private LocalDateTime createdAt;
    
    @Schema(
        description = "수정 일시 (컬럼: updated_at, NOT NULL)",
        example = "2025-11-17T10:00:00"
    )
    private LocalDateTime updatedAt;
}

