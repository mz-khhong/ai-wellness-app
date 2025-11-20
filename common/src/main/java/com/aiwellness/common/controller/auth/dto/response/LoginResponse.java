package com.aiwellness.common.controller.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * com.aiwellness.common.controller.auth.dto.response
 * <p>
 * LoginResponse
 * <p>
 * 로그인 응답 DTO
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 20.    메가존 시스템            최초 생성
 *  2025. 11. 20.    메가존 시스템            controller/auth/dto/response로 이동
 * </pre>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "로그인 응답 DTO")
public class LoginResponse {
    
    @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    @Schema(description = "토큰 타입", example = "Bearer")
    private String tokenType;
    
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
    
    @Schema(description = "사용자 UUID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String userUuid;
    
    @Schema(description = "이메일 주소", example = "admin@example.com")
    private String email;
    
    @Schema(description = "사용자 이름", example = "관리자")
    private String name;
    
    @Schema(description = "소속 시설 그룹 ID", example = "1")
    private Long facilityGroupId;
    
    @Schema(description = "사용자 역할 목록", example = "[\"ADMIN\"]")
    private List<String> roles;
}

