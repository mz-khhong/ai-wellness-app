package com.aiwellness.common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * com.aiwellness.common.dto.request
 * <p>
 * SortRequest
 * <p>
 * 정렬 요청 DTO
 * <p>
 * Controller에서 정렬 파라미터를 받을 때 사용합니다.
 * <p>
 * 참고: 현재는 {@link PageRequest}에 sort와 direction이 포함되어 있어 별도로 사용하지 않을 수 있습니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "정렬 요청")
public class SortRequest {
    
    @Schema(
        description = "정렬 필드 목록 (예: name,createdAt)",
        example = "name,createdAt",
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
     * 정렬 필드 목록을 파싱하여 반환
     * <p>
     * 예: "name,createdAt" -> ["name", "createdAt"]
     *
     * @return 정렬 필드 목록
     */
    public List<String> getSortFields() {
        List<String> fields = new ArrayList<>();
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
     * 예: "name ASC, createdAt DESC"
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

