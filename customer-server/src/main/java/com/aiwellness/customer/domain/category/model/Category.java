package com.aiwellness.customer.domain.category.model;

import lombok.*;

/**
 * com.aiwellness.customer.domain.category.model
 * <p>
 * Category
 * <p>
 * 카테고리 도메인 모델
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Category {
    
    private Long id;
    private String name;
    private String description;
    private Integer displayOrder;
    private Boolean isActive;
    
    // 비즈니스 로직 메서드
    public void activate() {
        this.isActive = true;
    }
    
    public void deactivate() {
        this.isActive = false;
    }
    
    public void updateDisplayOrder(Integer order) {
        this.displayOrder = order;
    }
}

