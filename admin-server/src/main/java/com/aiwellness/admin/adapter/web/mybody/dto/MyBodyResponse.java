package com.aiwellness.admin.adapter.web.mybody.dto;

import com.aiwellness.admin.domain.model.mybody.MyBody;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * com.aiwellness.admin.adapter.web.mybody.dto
 * <p>
 * MyBodyResponse
 * <p>
 * MY BODY 조회 응답 DTO
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "MY BODY 조회 응답")
public class MyBodyResponse {
    
    @Schema(description = "MY BODY ID", example = "1")
    private Long id;
    
    @Schema(description = "고객 ID", example = "1")
    private Long customerId;
    
    @Schema(description = "소속 시설 그룹 ID", example = "1")
    private Long facilityGroupId;
    
    @Schema(description = "생성 일시", example = "2025-11-17 10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;
    
    @Schema(description = "수정 일시", example = "2025-11-17 10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime updatedAt;
    
    /**
     * Domain Model을 Response DTO로 변환
     *
     * @param myBody MY BODY 도메인 모델
     * @return MyBodyResponse DTO
     */
    public static MyBodyResponse from(MyBody myBody) {
        return MyBodyResponse.builder()
                .id(myBody.getId())
                .customerId(myBody.getCustomerId())
                .facilityGroupId(myBody.getFacilityGroupId())
                .createdAt(myBody.getCreatedAt())
                .updatedAt(myBody.getUpdatedAt())
                .build();
    }
}

