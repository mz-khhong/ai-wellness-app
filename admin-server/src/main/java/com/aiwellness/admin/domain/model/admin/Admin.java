package com.aiwellness.admin.domain.model.admin;

import com.aiwellness.admin.domain.model.enums.AdminStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * com.aiwellness.admin.domain.model.admin
 * <p>
 * Admin
 * <p>
 * 관리자 도메인 모델
 * <p>
 * 데이터베이스 테이블: admins (1:1 매핑)
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
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    description = "관리자 도메인 모델 (테이블: admins)",
    example = "Admin(id=1, email=admin@example.com, name=관리자, status=ACTIVE)"
)
public class Admin {
    
    @Schema(
        description = "관리자 ID (Primary Key, 컬럼: id)",
        example = "1"
    )
    private Long id;
    
    @Schema(
        description = "소속 시설 그룹 ID (컬럼: facility_group_id, Foreign Key: facility_groups.id)",
        example = "1"
    )
    private Long facilityGroupId;
    
    @Schema(
        description = "사용자 UUID (컬럼: uuid, UNIQUE)",
        example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private String uuid;
    
    @Schema(
        description = "이메일 주소 (컬럼: email, UNIQUE, NOT NULL)",
        example = "admin@example.com"
    )
    private String email;
    
    @Schema(
        description = "관리자 이름 (컬럼: name, NOT NULL)",
        example = "관리자"
    )
    private String name;
    
    @Schema(
        description = "비밀번호 (컬럼: password, 암호화 저장, NOT NULL)",
        example = "$2a$10$...",
        hidden = true  // Swagger에서 숨김
    )
    private String password;
    
    @Schema(
        description = "관리자 상태 (컬럼: status, Enum: ACTIVE/INACTIVE/SUSPENDED, 기본값: ACTIVE)",
        example = "ACTIVE",
        allowableValues = {"ACTIVE", "INACTIVE", "SUSPENDED"}
    )
    private AdminStatus status;
    
    @Schema(
        description = "생성 일시 (컬럼: created_at, NOT NULL)",
        example = "2025-11-17T10:00:00"
    )
    private LocalDateTime createdAt;
    
    @Schema(
        description = "수정 일시 (컬럼: updated_at, NOT NULL)",
        example = "2025-11-17T10:00:00"
    )
    private LocalDateTime updatedAt;
}

