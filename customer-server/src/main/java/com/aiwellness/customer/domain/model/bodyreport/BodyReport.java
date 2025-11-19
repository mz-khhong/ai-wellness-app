package com.aiwellness.customer.domain.model.bodyreport;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * com.aiwellness.customer.domain.model.bodyreport
 * <p>
 * BodyReport
 * <p>
 * BODY Report 도메인 모델
 * <p>
 * 데이터베이스 테이블: body_reports (1:1 매핑)
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
    description = "BODY Report 도메인 모델 (테이블: body_reports)",
    example = "BodyReport(id=1, customerId=1, myBodyId=1)"
)
public class BodyReport {
    
    @Schema(
        description = "리포트 ID (Primary Key, 컬럼: id)",
        example = "1"
    )
    private Long id;
    
    @Schema(
        description = "고객 ID (컬럼: customer_id, Foreign Key: customers.id)",
        example = "1"
    )
    private Long customerId;
    
    @Schema(
        description = "MY BODY ID (컬럼: my_body_id, Foreign Key: my_bodies.id)",
        example = "1"
    )
    private Long myBodyId;
    
    @Schema(
        description = "리포트 내용 (컬럼: report_content)",
        example = "BODY 리포트 내용"
    )
    private String reportContent;
    
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

