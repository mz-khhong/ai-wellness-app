package com.aiwellness.common.controller.appCode;

import com.aiwellness.common.controller.appCode.dto.response.AppCodeData;
import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.service.ResponseCodeService;
import com.aiwellness.common.support.ApiResponseGenerator;
import com.aiwellness.common.util.MessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.aiwellness.common.controller.appCode
 * <p>
 * AppCodeController
 * <p>
 * 응답 코드 정보를 제공하는 컨트롤러 (FE 연동용)
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 19.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 19.    메가존 시스템            최초 생성
 *  2025. 11. 20.    메가존 시스템            AppCodeController로 변경
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/appCode")
@RequiredArgsConstructor
@Tag(name = "App Code", description = "응답 코드 정보 API (FE 연동용)")
public class AppCodeController {
    
    private final ResponseCodeService responseCodeService;
    
    @GetMapping
    @Operation(
        summary = "응답 코드 목록 조회",
        description = "시스템에서 사용하는 모든 응답 코드(성공/에러) 목록을 조회합니다. " +
                "Accept-Language 헤더(ko, en)를 통해 다국어 메시지를 제공합니다. " +
                "FE에서 에러 처리 및 성공 메시지 표시에 활용할 수 있습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "성공",
            content = @Content(schema = @Schema(implementation = ApiResponseWellness.class))
        )
    })
    public ApiResponseWellness<AppCodeData> getAppCodes() {
        // Locale 결정 (Accept-Language 헤더 기반)
        Locale locale = MessageUtil.getLocaleFromRequest();

        AppCodeData data = responseCodeService.getAllResponseCodes(locale);
        
        return ApiResponseGenerator.success(data);
    }
}

