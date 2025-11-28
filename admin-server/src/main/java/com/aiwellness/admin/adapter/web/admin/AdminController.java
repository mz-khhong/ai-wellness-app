package com.aiwellness.admin.adapter.web.admin;

import com.aiwellness.admin.adapter.web.admin.dto.request.AdminCreateRequest;
import com.aiwellness.admin.adapter.web.admin.dto.request.AdminUpdateRequest;
import com.aiwellness.admin.adapter.web.admin.dto.response.AdminResponse;
import com.aiwellness.admin.application.service.admin.AdminService;
import com.aiwellness.admin.domain.code.admin.AdminCode;
import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.support.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "관리자 API")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/{id}")
    @Operation(summary = "관리자 조회", description = "ID로 관리자 정보를 조회합니다.")
    public ApiResponseWellness<AdminResponse> getAdmin(@PathVariable Long id) {
        return ApiResponseGenerator.success(adminService.getAdmin(id));
    }

    @PostMapping
    @Operation(summary = "관리자 생성", description = "새로운 관리자를 생성합니다.")
    public ResponseEntity<ApiResponseWellness<AdminResponse>> createAdmin(@Valid @RequestBody AdminCreateRequest request) {
        return ApiResponseGenerator.success(AdminCode.ADMIN_CREATE_SUCCESS, adminService.createAdmin(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "관리자 수정", description = "관리자 정보를 수정합니다.")
    public ApiResponseWellness<AdminResponse> updateAdmin(
            @PathVariable Long id,
            @Valid @RequestBody AdminUpdateRequest request) {
        return ApiResponseGenerator.success(
                AdminCode.ADMIN_UPDATE_SUCCESS, adminService.updateAdmin(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "관리자 삭제", description = "관리자를 삭제합니다.")
    public ApiResponseWellness<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ApiResponseGenerator.success();
    }
}

