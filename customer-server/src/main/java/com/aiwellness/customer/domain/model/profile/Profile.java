package com.aiwellness.customer.domain.model.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * com.aiwellness.customer.domain.model.profile
 * <p>
 * Profile
 * <p>
 * 프로필 도메인 모델
 * <p>
 * 데이터베이스 테이블: profiles (1:1 매핑)
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
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    description = "프로필 도메인 모델 (테이블: profiles)",
    example = "Profile(id=1, customerId=1, nickname=홍길동)"
)
public class Profile {
    
    @Schema(
        description = "프로필 ID (Primary Key, 컬럼: id)",
        example = "1"
    )
    private Long id;
    
    @Schema(
        description = "고객 ID (컬럼: customer_id, Foreign Key: customers.id)",
        example = "1"
    )
    private Long customerId;
    
    @Schema(
        description = "닉네임 (컬럼: nickname)",
        example = "홍길동"
    )
    private String nickname;
    
    @Schema(
        description = "프로필 이미지 URL (컬럼: profile_image_url)",
        example = "https://example.com/image.jpg"
    )
    private String profileImageUrl;
    
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

