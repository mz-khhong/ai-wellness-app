package com.aiwellness.customer.adapter.web.profile.dto.response;

import com.aiwellness.customer.domain.model.profile.Profile;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * com.aiwellness.customer.adapter.web.profile.dto
 * <p>
 * ProfileResponse
 * <p>
 * 프로필 조회 응답 DTO
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로필 조회 응답")
public class ProfileResponse {
    
    @Schema(description = "프로필 ID", example = "1")
    private Long id;
    
    @Schema(description = "고객 ID", example = "1")
    private Long customerId;
    
    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;
    
    @Schema(description = "프로필 이미지 URL", example = "https://example.com/image.jpg")
    private String profileImageUrl;
    
    @Schema(description = "생성 일시", example = "2025-11-17 10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;
    
    @Schema(description = "수정 일시", example = "2025-11-17 10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime updatedAt;
    
    /**
     * Domain Model을 Response DTO로 변환
     *
     * @param profile 프로필 도메인 모델
     * @return ProfileResponse DTO
     */
    public static ProfileResponse from(Profile profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .customerId(profile.getCustomerId())
                .nickname(profile.getNickname())
                .profileImageUrl(profile.getProfileImageUrl())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}

