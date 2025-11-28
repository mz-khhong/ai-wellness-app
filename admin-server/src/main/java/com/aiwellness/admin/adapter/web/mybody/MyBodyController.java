package com.aiwellness.admin.adapter.web.mybody;

import com.aiwellness.admin.adapter.web.mybody.dto.response.MyBodyResponse;
import com.aiwellness.admin.application.service.mybody.MyBodyService;
import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.security.CurrentUser;
import com.aiwellness.common.security.annotation.AuthenticatedUser;
import com.aiwellness.common.support.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.aiwellness.admin.adapter.web.mybody
 * <p>
 * MyBodyController
 * <p>
 * MY BODY REST API 컨트롤러
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
@RestController
@RequestMapping("/api/v1/mybody")
@RequiredArgsConstructor
@Tag(name = "MY BODY", description = "MY BODY 관리 API")
public class MyBodyController {
    
    private final MyBodyService myBodyService;
    
    @GetMapping("/{id}")
    @Operation(summary = "MY BODY 조회", description = "ID로 MY BODY 정보를 조회합니다.")
    public ApiResponseWellness<MyBodyResponse> getMyBody(
            @PathVariable Long id,
            @AuthenticatedUser CurrentUser user) {
        return ApiResponseGenerator.success(myBodyService.getMyBody(id));
    }
    
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "고객별 MY BODY 조회", description = "고객 ID로 MY BODY 정보를 조회합니다.")
    public ApiResponseWellness<MyBodyResponse> getMyBodyByCustomerId(
            @PathVariable Long customerId,
            @AuthenticatedUser CurrentUser user) {
        return ApiResponseGenerator.success(myBodyService.getMyBodyByCustomerId(customerId));
    }
}

