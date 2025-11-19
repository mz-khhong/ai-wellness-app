package com.aiwellness.customer.adapter.web.category.dto;

import com.aiwellness.customer.domain.category.model.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * com.aiwellness.customer.adapter.web.category.dto
 * <p>
 * CategoryResponse
 * <p>
 * 카테고리 응답 DTO
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 19.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 19.    메가존 시스템            최초 생성
 * </pre>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "카테고리 응답 DTO")
public class CategoryResponse {
    
    @Schema(
        description = "카테고리 ID (Primary Key, 필수)",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long id;
    
    @Schema(
        description = "카테고리 이름 (필수)",
        example = "건강관리",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;
    
    @Schema(
        description = "카테고리 설명",
        example = "건강 관리 관련 카테고리입니다."
    )
    private String description;
    
    @Schema(
        description = "표시 순서 (필수)",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer displayOrder;
    
    @Schema(
        description = "활성화 여부 (필수)",
        example = "true",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Boolean isActive;
    
    /**
     * Category 도메인 모델을 CategoryResponse로 변환
     */
    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .displayOrder(category.getDisplayOrder())
                .isActive(category.getIsActive())
                .build();
    }
}
