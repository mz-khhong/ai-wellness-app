package com.aiwellness.common.controller.appCode.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * com.aiwellness.common.controller.appCode.dto.response
 * <p>
 * AppCodeInfo
 * <p>
 * 응답 코드 정보 DTO
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
 *  2025. 11. 20.    메가존 시스템            AppCodeInfo로 변경
 * </pre>
 */
@Getter
@AllArgsConstructor
@Schema(description = "응답 코드 정보")
public class AppCodeInfo {
    
    @Schema(
        description = "응답 코드 (필수)",
        example = "20000",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String code;
    
    @Schema(
        description = "번역된 메시지 (필수)",
        example = "성공입니다.",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String message;
    
    @Schema(
        description = "코드 타입 (SUCCESS 또는 ERROR, 필수)",
        example = "SUCCESS",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String type;
}

