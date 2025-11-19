package com.aiwellness.customer.adapter.web.profile;

import com.aiwellness.customer.adapter.web.profile.dto.ProfileResponse;
import com.aiwellness.customer.application.service.profile.ProfileService;
import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.support.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.aiwellness.customer.adapter.web.profile
 * <p>
 * ProfileController
 * <p>
 * 프로필 REST API 컨트롤러
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
@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "프로필 관리 API")
public class ProfileController {
    
    private final ProfileService profileService;
    
    @GetMapping("/{id}")
    @Operation(summary = "프로필 조회", description = "ID로 프로필 정보를 조회합니다.")
    public ApiResponseWellness<ProfileResponse> getProfile(@PathVariable Long id) {
        log.info("[Adapter/Web] ProfileController.getProfile() - HTTP 요청: GET /api/profile/{}", id);
        ApiResponseWellness<ProfileResponse> response = ApiResponseGenerator.success(profileService.getProfile(id));
        log.info("[Adapter/Web] ProfileController.getProfile() - HTTP 응답: 200 OK");
        return response;
    }
    
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "고객별 프로필 조회", description = "고객 ID로 프로필 정보를 조회합니다.")
    public ApiResponseWellness<ProfileResponse> getProfileByCustomerId(@PathVariable Long customerId) {
        log.info("[Adapter/Web] ProfileController.getProfileByCustomerId() - HTTP 요청: GET /api/profile/customer/{}", customerId);
        ApiResponseWellness<ProfileResponse> response = ApiResponseGenerator.success(profileService.getProfileByCustomerId(customerId));
        log.info("[Adapter/Web] ProfileController.getProfileByCustomerId() - HTTP 응답: 200 OK");
        return response;
    }
}

