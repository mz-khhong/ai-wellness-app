package com.aiwellness.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * com.aiwellness.common.dto
 * <p>
 * ResponseCodeData
 * <p>
 * 응답 코드 정보를 구조화된 형태로 제공하는 DTO
 * <p>
 * 공통 코드와 서버별 코드를 비즈니스 영역별로 분리하여 제공합니다.
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
@Builder
@AllArgsConstructor
@Schema(description = "응답 코드 데이터 구조")
public class ResponseCodeData {
    
    @Schema(
        description = "공통 응답 코드 목록",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<ResponseCodeInfo> common;
    
    @Schema(
        description = "서버별 응답 코드 (서버명 > 비즈니스 영역 > 코드 목록)",
        example = "{\"admin\": {\"admin\": [...]}, \"manager\": {\"manager\": [...]}, \"customer\": {\"customer\": [...]}}",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Map<String, Map<String, List<ResponseCodeInfo>>> servers;
}

