package com.aiwellness.admin.adapter.web.admin;

import com.aiwellness.admin.adapter.web.admin.dto.AdminCreateRequest;
import com.aiwellness.admin.adapter.web.admin.dto.AdminResponse;
import com.aiwellness.admin.adapter.web.admin.dto.AdminUpdateRequest;
import com.aiwellness.admin.application.service.admin.AdminService;
import com.aiwellness.admin.code.AdminResponseCode;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "관리자 API")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/{id}")
    @Operation(summary = "관리자 조회", description = "ID로 관리자 정보를 조회합니다.")
    public ApiResponseWellness<AdminResponse> getAdmin(@PathVariable Long id) {
        log.info("[Adapter/Web] AdminController.getAdmin() - HTTP 요청: GET /api/admins/{}", id);
        ApiResponseWellness<AdminResponse> response = ApiResponseGenerator.success(adminService.getAdmin(id));
        log.info("[Adapter/Web] AdminController.getAdmin() - HTTP 응답: 200 OK");
        return response;
    }

    @PostMapping
    @Operation(summary = "관리자 생성", description = "새로운 관리자를 생성합니다.")
    public ResponseEntity<ApiResponseWellness<AdminResponse>> createAdmin(@Valid @RequestBody AdminCreateRequest request) {
        log.info("[Adapter/Web] AdminController.createAdmin() - HTTP 요청: POST /api/admins, email={}", request.getEmail());
        ResponseEntity<ApiResponseWellness<AdminResponse>> response = ApiResponseGenerator.success(AdminResponseCode.ADMIN_CREATE_SUCCESS, adminService.createAdmin(request), HttpStatus.CREATED);
        log.info("[Adapter/Web] AdminController.createAdmin() - HTTP 응답: 201 CREATED");
        return response;
    }

    @PutMapping("/{id}")
    @Operation(summary = "관리자 수정", description = "관리자 정보를 수정합니다.")
    public ApiResponseWellness<AdminResponse> updateAdmin(
            @PathVariable Long id,
            @Valid @RequestBody AdminUpdateRequest request) {
        log.info("[Adapter/Web] AdminController.updateAdmin() - HTTP 요청: PUT /api/admins/{}", id);
        ApiResponseWellness<AdminResponse> response = ApiResponseGenerator.success(
                AdminResponseCode.ADMIN_UPDATE_SUCCESS, adminService.updateAdmin(id, request));
        log.info("[Adapter/Web] AdminController.updateAdmin() - HTTP 응답: 200 OK");
        return response;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "관리자 삭제", description = "관리자를 삭제합니다.")
    public ApiResponseWellness<Void> deleteAdmin(@PathVariable Long id) {
        log.info("[Adapter/Web] AdminController.deleteAdmin() - HTTP 요청: DELETE /api/admins/{}", id);
        adminService.deleteAdmin(id);
        log.info("[Adapter/Web] AdminController.deleteAdmin() - HTTP 응답: 200 OK");
        return ApiResponseGenerator.success();
    }
}

