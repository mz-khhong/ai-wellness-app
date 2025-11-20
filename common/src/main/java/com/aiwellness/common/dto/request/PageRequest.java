package com.aiwellness.common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * com.aiwellness.common.dto.request
 * <p>
 * PageRequest
 * <p>
 * 페이징 요청 DTO
 * <p>
 * Controller에서 사용자로부터 페이징 파라미터(page, size, sort, direction)를 받을 때 사용합니다.
 * 페이징 관련 유틸리티 메서드(getOffset, toOrderByClause 등)도 제공합니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "페이징 요청")
public class PageRequest {
    
    @Schema(
        description = "페이지 번호 (1부터 시작)",
        example = "1",
        minimum = "1",
        defaultValue = "1"
    )
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    private Integer page = 1;
    
    @Schema(
        description = "페이지 크기 (10, 50, 100 중 선택 가능)",
        example = "10",
        allowableValues = {"10", "50", "100"},
        defaultValue = "10"
    )
    @Min(value = 10, message = "페이지 크기는 최소 10이어야 합니다.")
    @Max(value = 100, message = "페이지 크기는 최대 100이어야 합니다.")
    private Integer size = 10;
    
    @Schema(
        description = "정렬 필드 (예: name,createdAt)",
        example = "name",
        defaultValue = ""
    )
    private String sort = "";
    
    @Schema(
        description = "정렬 방향 (ASC, DESC)",
        example = "ASC",
        allowableValues = {"ASC", "DESC"},
        defaultValue = "ASC"
    )
    private String direction = "ASC";
    
    /**
     * MyBatis에서 사용할 offset 계산
     *
     * @return offset
     */
    public int getOffset() {
        return (page - 1) * size;
    }
    
    /**
     * 페이징이 활성화되어 있는지 확인
     *
     * @return 페이징 활성화 여부
     */
    public boolean isPagingEnabled() {
        return page != null && page > 0 && size != null && size > 0;
    }
    
    /**
     * 페이지 크기를 표준 크기로 정규화 (10, 50, 100)
     *
     * @return 정규화된 페이지 크기
     */
    public int getNormalizedSize() {
        if (size == null || size <= 0) {
            return 10;
        }
        if (size <= 10) {
            return 10;
        } else if (size <= 50) {
            return 50;
        } else {
            return 100;
        }
    }
    
    /**
     * 정렬 필드 목록을 파싱하여 반환
     *
     * @return 정렬 필드 목록
     */
    public java.util.List<String> getSortFields() {
        java.util.List<String> fields = new java.util.ArrayList<>();
        if (sort != null && !sort.trim().isEmpty()) {
            String[] parts = sort.split(",");
            for (String part : parts) {
                String field = part.trim();
                if (!field.isEmpty()) {
                    fields.add(field);
                }
            }
        }
        return fields;
    }
    
    /**
     * 정렬 방향이 내림차순인지 확인
     *
     * @return 내림차순 여부
     */
    public boolean isDescending() {
        return "DESC".equalsIgnoreCase(direction);
    }
    
    /**
     * MyBatis에서 사용할 ORDER BY 절 생성
     * <p>
     * 예: "name ASC, created_at DESC"
     *
     * @param allowedFields 허용된 정렬 필드 목록 (SQL Injection 방지)
     * @return ORDER BY 절
     */
    public String toOrderByClause(List<String> allowedFields) {
        List<String> sortFields = getSortFields();
        if (sortFields.isEmpty() || allowedFields == null || allowedFields.isEmpty()) {
            return "";
        }
        
        List<String> orderByParts = new ArrayList<>();
        for (String field : sortFields) {
            // 허용된 필드만 사용 (SQL Injection 방지)
            if (allowedFields.contains(field)) {
                String directionStr = isDescending() ? "DESC" : "ASC";
                // snake_case 변환 (예: createdAt -> created_at)
                String dbField = camelToSnakeCase(field);
                orderByParts.add(dbField + " " + directionStr);
            }
        }
        
        return orderByParts.isEmpty() ? "" : String.join(", ", orderByParts);
    }
    
    /**
     * camelCase를 snake_case로 변환
     * <p>
     * 예: "createdAt" -> "created_at"
     *
     * @param camelCase camelCase 문자열
     * @return snake_case 문자열
     */
    private String camelToSnakeCase(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return camelCase;
        }
        return camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}

