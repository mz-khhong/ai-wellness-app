package com.aiwellness.common.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * com.aiwellness.common.dto.response
 * <p>
 * PageResponse
 * <p>
 * 페이징 응답 DTO
 * <p>
 * Controller에서 페이징 결과를 반환할 때 사용합니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "페이징 응답")
public class PageResponse<T> {
    
    @Schema(description = "조회된 데이터 목록", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<T> content;
    
    @Schema(description = "현재 페이지 번호 (1부터 시작)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int page;
    
    @Schema(description = "페이지 크기", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private int size;
    
    @Schema(description = "전체 데이터 개수", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private long totalElements;
    
    @Schema(description = "전체 페이지 수", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private int totalPages;
    
    @Schema(description = "다음 페이지 존재 여부", example = "true")
    private boolean hasNext;
    
    @Schema(description = "이전 페이지 존재 여부", example = "false")
    private boolean hasPrevious;
    
    /**
     * 페이징 결과 생성
     *
     * @param content 조회된 데이터 목록
     * @param page 현재 페이지 번호
     * @param size 페이지 크기
     * @param totalElements 전체 데이터 개수
     * @return PageResponse
     */
    public static <T> PageResponse<T> of(List<T> content, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return PageResponse.<T>builder()
                .content(content)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .hasNext(page < totalPages)
                .hasPrevious(page > 1)
                .build();
    }
}

