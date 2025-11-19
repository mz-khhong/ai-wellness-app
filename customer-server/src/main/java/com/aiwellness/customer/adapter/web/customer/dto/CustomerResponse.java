package com.aiwellness.customer.adapter.web.customer.dto;

import com.aiwellness.customer.domain.model.enums.CustomerStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * com.aiwellness.customer.adapter.web.customer.dto
 * <p>
 * CustomerResponse
 * <p>
 * 고객 응답 DTO
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 17.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 17.    메가존 시스템            최초 생성
 *  2025. 11. 17.    메가존 시스템            @Schema 어노테이션 추가
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "고객 응답 DTO")
public class CustomerResponse {
    
    @Schema(description = "고객 ID", example = "1")
    private Long id;
    
    @Schema(description = "소속 시설 그룹 ID", example = "1")
    private Long facilityGroupId;
    
    @Schema(description = "사용자 UUID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String uuid;
    
    @Schema(description = "이메일 주소", example = "customer@example.com")
    private String email;
    
    @Schema(description = "고객 이름", example = "고객")
    private String name;
    
    @Schema(
        description = "고객 상태",
        example = "ACTIVE",
        allowableValues = {"ACTIVE", "INACTIVE", "SUSPENDED"}
    )
    private CustomerStatus status;
    
    @Schema(description = "생성 일시", example = "2025-11-17 10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;
    
    @Schema(description = "수정 일시", example = "2025-11-17 10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime updatedAt;
    
    // Domain Model을 Response DTO로 변환
    public static CustomerResponse from(com.aiwellness.customer.domain.model.customer.Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .facilityGroupId(customer.getFacilityGroupId())
                .uuid(customer.getUuid())
                .email(customer.getEmail())
                .name(customer.getName())
                .status(customer.getStatus())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}

