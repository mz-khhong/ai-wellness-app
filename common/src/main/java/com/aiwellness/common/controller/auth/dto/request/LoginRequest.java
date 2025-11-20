package com.aiwellness.common.controller.auth.dto.request;

import com.aiwellness.common.util.RegexUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * com.aiwellness.common.controller.auth.dto.request
 * <p>
 * LoginRequest
 * <p>
 * 로그인 요청 DTO
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
 *  2025. 11. 20.    메가존 시스템            controller/auth/dto/request로 이동
 * </pre>
 */
@Data
@Schema(description = "로그인 요청 DTO")
public class LoginRequest {
    
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

