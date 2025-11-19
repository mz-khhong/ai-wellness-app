package com.aiwellness.admin.domain.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * com.aiwellness.admin.domain.model.enums
 * <p>
 * AdminStatus
 * <p>
 * 관리자 상태를 나타내는 Enum
 * <p>
 * 데이터베이스 컬럼: admins.status (VARCHAR)
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 14.    메가존 시스템            최초 생성
 *  2025. 11. 17.    메가존 시스템            @Schema 어노테이션 추가
 * </pre>
 */
@Schema(
    description = "관리자 상태 Enum (테이블: admins.status)",
    allowableValues = {"ACTIVE", "INACTIVE", "SUSPENDED"}
)
public enum AdminStatus {
    @Schema(description = "활성 상태", example = "ACTIVE")
    ACTIVE,
    
    @Schema(description = "비활성 상태", example = "INACTIVE")
    INACTIVE,
    
    @Schema(description = "정지 상태", example = "SUSPENDED")
    SUSPENDED
}

