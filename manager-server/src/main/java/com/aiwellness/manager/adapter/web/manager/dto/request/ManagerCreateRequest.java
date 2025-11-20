package com.aiwellness.manager.adapter.web.manager.dto.request;

import com.aiwellness.common.util.RegexUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * com.aiwellness.manager.adapter.web.manager.dto
 * <p>
 * ManagerCreateRequest
 * <p>
 * 매니저 생성 요청 DTO
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 17.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 17.    메가존 시스템            최초 생성
 *  2025. 11. 17.    메가존 시스템            @Schema 어노테이션 추가
 * </pre>
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "매니저 생성 요청 DTO")
public class ManagerCreateRequest {
    
    @Schema(
        description = "이메일 주소 (필수, UNIQUE)",
        example = "manager@example.com"
    )
    @NotBlank(message = "{validation.NotBlank}")
    @Pattern(regexp = RegexUtil.EMAIL, message = "{validation.regex.email}")
    private String email;
    
    @Schema(
        description = "매니저 이름 (필수, 2-50자, 한글 또는 영문)",
        example = "매니저"
    )
    @NotBlank(message = "{validation.NotBlank}")
    @Size(min = 2, max = 50, message = "{validation.Size}")
    @Pattern(regexp = RegexUtil.KOREAN_NAME + "|" + RegexUtil.ENGLISH_NAME, message = "{validation.regex.name}")
    private String name;
    
    @Schema(
        description = "비밀번호 (필수, 8자 이상, 영문/숫자/특수문자 포함)",
        example = "password123!"
    )
    @NotBlank(message = "{validation.NotBlank}")
    @Size(min = 8, max = 100, message = "{validation.Size}")
    @Pattern(regexp = RegexUtil.PASSWORD, message = "{validation.regex.password}")
    private String password;
    
    @Schema(
        description = "소속 시설 그룹 ID (선택적, Foreign Key: facility_groups.id)",
        example = "1"
    )
    private Long facilityGroupId;
}

