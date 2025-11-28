package com.aiwellness.admin.adapter.web.external;

import com.aiwellness.admin.adapter.infrastructure.external.sso.dto.ExternalApiResponse;
import com.aiwellness.admin.application.service.external.ExternalApiService;
import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.support.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * com.aiwellness.admin.adapter.web.external
 * <p>
 * ExternalApiController
 * <p>
 * 외부 SSO 연계 API 호출을 위한 REST Controller
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/external")
@RequiredArgsConstructor
@Tag(name = "External API", description = "외부 SSO 연계 API")
public class ExternalApiController {
    
    private final ExternalApiService externalApiService;
    
    @PostMapping("/process")
    @Operation(summary = "외부 SSO 연계 API 호출", description = "SSO 토큰을 사용하여 외부 API를 호출합니다.")
    public ApiResponseWellness<ExternalApiResponse> callExternalApi(
            @RequestParam @NotBlank String data,
            @RequestParam @NotBlank String type) {
        return ApiResponseGenerator.success(
                externalApiService.callExternalApi(data, type)
        );
    }
}

