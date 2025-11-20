package com.aiwellness.manager.adapter.web.manager;

import com.aiwellness.manager.adapter.web.manager.dto.request.ManagerCreateRequest;
import com.aiwellness.manager.adapter.web.manager.dto.request.ManagerUpdateRequest;
import com.aiwellness.manager.adapter.web.manager.dto.response.ManagerResponse;
import com.aiwellness.manager.application.service.manager.ManagerService;
import com.aiwellness.manager.code.ManagerResponseCode;
import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.support.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * com.aiwellness.manager.adapter.web
 * <p>
 * ManagerController
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
@RequestMapping("/api/v1/managers")
@RequiredArgsConstructor
@Tag(name = "Manager", description = "매니저 API")
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping("/{id}")
    @Operation(summary = "매니저 조회", description = "ID로 매니저 정보를 조회합니다.")
    public ApiResponseWellness<ManagerResponse> getManager(@PathVariable Long id) {
        log.info("[Adapter/Web] ManagerController.getManager() - HTTP 요청: GET /api/v1/managers/{}", id);
        ApiResponseWellness<ManagerResponse> response = ApiResponseGenerator.success(managerService.getManager(id));
        log.info("[Adapter/Web] ManagerController.getManager() - HTTP 응답: 200 OK");
        return response;
    }

    @PostMapping
    @Operation(summary = "매니저 생성", description = "새로운 매니저를 생성합니다.")
    public ResponseEntity<ApiResponseWellness<ManagerResponse>> createManager(
            @Valid @RequestBody ManagerCreateRequest request) {
        log.info("[Adapter/Web] ManagerController.createManager() - HTTP 요청: POST /api/v1/managers, email={}", request.getEmail());
        ResponseEntity<ApiResponseWellness<ManagerResponse>> response = ApiResponseGenerator.success(
                ManagerResponseCode.MANAGER_CREATE_SUCCESS, 
                managerService.createManager(request), 
                HttpStatus.CREATED
        );
        log.info("[Adapter/Web] ManagerController.createManager() - HTTP 응답: 201 CREATED");
        return response;
    }

    @PutMapping("/{id}")
    @Operation(summary = "매니저 수정", description = "매니저 정보를 수정합니다.")
    public ApiResponseWellness<ManagerResponse> updateManager(
            @PathVariable Long id,
            @Valid @RequestBody ManagerUpdateRequest request) {
        log.info("[Adapter/Web] ManagerController.updateManager() - HTTP 요청: PUT /api/v1/managers/{}", id);
        ApiResponseWellness<ManagerResponse> response = ApiResponseGenerator.success(
                ManagerResponseCode.MANAGER_UPDATE_SUCCESS, 
                managerService.updateManager(id, request)
        );
        log.info("[Adapter/Web] ManagerController.updateManager() - HTTP 응답: 200 OK");
        return response;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "매니저 삭제", description = "매니저를 삭제합니다.")
    public ApiResponseWellness<Void> deleteManager(@PathVariable Long id) {
        log.info("[Adapter/Web] ManagerController.deleteManager() - HTTP 요청: DELETE /api/v1/managers/{}", id);
        managerService.deleteManager(id);
        log.info("[Adapter/Web] ManagerController.deleteManager() - HTTP 응답: 200 OK");
        return ApiResponseGenerator.success();
    }
}

