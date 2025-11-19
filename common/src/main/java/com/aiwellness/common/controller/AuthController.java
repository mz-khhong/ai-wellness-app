package com.aiwellness.common.controller;

import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.security.AuthenticationException;
import com.aiwellness.common.security.AuthenticationResult;
import com.aiwellness.common.security.JwtTokenProvider;
import com.aiwellness.common.security.port.AuthenticationPort;
import com.aiwellness.common.support.ApiResponseGenerator;
import com.aiwellness.common.util.RegexUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * com.aiwellness.common.controller
 * <p>
 * AuthController
 * <p>
 * 인증 API 컨트롤러
 * 각 서버(admin, manager, customer)에서 AuthenticationPort를 구현하여 사용합니다.
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
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 API")
public class AuthController {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationPort authenticationPort;
    
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하여 JWT 토큰을 발급받습니다.")
    public ResponseEntity<ApiResponseWellness<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        log.info("[Adapter/Web] AuthController.login() - HTTP 요청: POST /api/v1/auth/login, email={}", request.getEmail());
        
        // AuthenticationPort를 통한 인증
        AuthenticationResult authResult = authenticationPort.authenticate(request.getEmail(), request.getPassword());
        
        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(
                authResult.getEmail(),
                authResult.getUserId(),
                authResult.getUserUuid(),
                authResult.getFacilityGroupId(),
                authResult.getRoles()
        );
        
        // LoginResponse 생성
        LoginResponse response = LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(authResult.getUserId())
                .userUuid(authResult.getUserUuid())
                .email(authResult.getEmail())
                .name(authResult.getName())
                .facilityGroupId(authResult.getFacilityGroupId())
                .roles(authResult.getRoles())
                .build();
        
        log.info("[Adapter/Web] AuthController.login() - HTTP 응답: 200 OK, userId={}", authResult.getUserId());
        return ApiResponseGenerator.success("auth.login.success", response, HttpStatus.OK);
    }
    
    @Data
    @Schema(description = "로그인 요청 DTO")
    public static class LoginRequest {
        
        @Schema(
            description = "이메일 주소 (필수)",
            example = "admin@example.com"
        )
        @NotBlank(message = "{validation.NotBlank}")
        @Pattern(regexp = RegexUtil.EMAIL, message = "{validation.regex.email}")
        private String email;
        
        @Schema(
            description = "비밀번호 (필수)",
            example = "password123!"
        )
        @NotBlank(message = "{validation.NotBlank}")
        private String password;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "로그인 응답 DTO")
    public static class LoginResponse {
        
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
        private java.util.List<String> roles;
    }
}

