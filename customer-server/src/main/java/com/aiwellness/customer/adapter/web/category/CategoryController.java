package com.aiwellness.customer.adapter.web.category;

import com.aiwellness.common.dto.request.PageRequest;
import com.aiwellness.common.dto.response.PageResponse;
import com.aiwellness.customer.adapter.web.category.dto.response.CategoryResponse;
import com.aiwellness.customer.application.service.category.CategoryService;
import com.aiwellness.customer.domain.category.model.Category;
import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.security.CurrentUser;
import com.aiwellness.common.security.annotation.AuthenticatedUser;
import com.aiwellness.common.security.annotation.RequiredRole;
import com.aiwellness.common.support.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * com.aiwellness.customer.adapter.web.category
 * <p>
 * CategoryController
 * <p>
 * 카테고리 도메인의 인바운드 어댑터 (REST API)
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "카테고리 API")
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping
    @Operation(summary = "카테고리 목록 조회", description = "모든 카테고리 목록을 조회합니다.")
    public ApiResponseWellness<List<CategoryResponse>> getAllCategories(
            @AuthenticatedUser CurrentUser user) {
        log.info("[Adapter/Web] CategoryController.getAllCategories() - HTTP 요청: GET /api/v1/categories, userId={}, email={}", 
                user.getUserId(), user.getEmail());
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryResponse> responses = categories.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
        log.info("[Adapter/Web] CategoryController.getAllCategories() - HTTP 응답: 200 OK, count={}", responses.size());
        return ApiResponseGenerator.success(responses);
    }
    
    @GetMapping("/active")
    @Operation(summary = "활성 카테고리 목록 조회", description = "활성화된 카테고리 목록을 조회합니다.")
    public ApiResponseWellness<List<CategoryResponse>> getActiveCategories(
            @AuthenticatedUser CurrentUser user) {
        log.info("[Adapter/Web] CategoryController.getActiveCategories() - HTTP 요청: GET /api/v1/categories/active, userId={}, email={}", 
                user.getUserId(), user.getEmail());
        List<Category> categories = categoryService.getActiveCategories();
        List<CategoryResponse> responses = categories.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
        log.info("[Adapter/Web] CategoryController.getActiveCategories() - HTTP 응답: 200 OK, count={}", responses.size());
        return ApiResponseGenerator.success(responses);
    }
    
    @GetMapping("/paged")
    @Operation(
        summary = "카테고리 목록 조회 (페이징)", 
        description = "페이징을 적용한 카테고리 목록을 조회합니다. " +
                "페이지 크기는 10, 50, 100 중 선택 가능합니다."
    )
    public ApiResponseWellness<PageResponse<CategoryResponse>> getAllCategoriesWithPaging(
            @Valid PageRequest pageRequest,
            @AuthenticatedUser CurrentUser user) {
        log.info("[Adapter/Web] CategoryController.getAllCategoriesWithPaging() - HTTP 요청: GET /api/v1/categories/paged, page={}, size={}, userId={}, email={}", 
                pageRequest.getPage(), pageRequest.getSize(), user.getUserId(), user.getEmail());
        
        PageResponse<CategoryResponse> response = categoryService.getAllCategoriesWithPaging(pageRequest);
        
        log.info("[Adapter/Web] CategoryController.getAllCategoriesWithPaging() - HTTP 응답: 200 OK, totalElements={}, totalPages={}", 
                response.getTotalElements(), response.getTotalPages());
        return ApiResponseGenerator.success(response);
    }
    
    @GetMapping("/active/paged")
    @Operation(
        summary = "활성 카테고리 목록 조회 (페이징)", 
        description = "페이징을 적용한 활성 카테고리 목록을 조회합니다. " +
                "페이지 크기는 10, 50, 100 중 선택 가능합니다."
    )
    public ApiResponseWellness<PageResponse<CategoryResponse>> getActiveCategoriesWithPaging(
            @Valid PageRequest pageRequest,
            @AuthenticatedUser CurrentUser user) {
        log.info("[Adapter/Web] CategoryController.getActiveCategoriesWithPaging() - HTTP 요청: GET /api/v1/categories/active/paged, page={}, size={}, userId={}, email={}", 
                pageRequest.getPage(), pageRequest.getSize(), user.getUserId(), user.getEmail());
        
        PageResponse<CategoryResponse> response = categoryService.getActiveCategoriesWithPaging(pageRequest);
        
        log.info("[Adapter/Web] CategoryController.getActiveCategoriesWithPaging() - HTTP 응답: 200 OK, totalElements={}, totalPages={}", 
                response.getTotalElements(), response.getTotalPages());
        return ApiResponseGenerator.success(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "카테고리 조회", description = "ID로 카테고리 정보를 조회합니다.")
    public ApiResponseWellness<CategoryResponse> getCategory(
            @PathVariable Long id,
            @AuthenticatedUser CurrentUser user) {
        log.info("[Adapter/Web] CategoryController.getCategory() - HTTP 요청: GET /api/v1/categories/{}, userId={}, email={}", 
                id, user.getUserId(), user.getEmail());
        Category category = categoryService.getCategory(id);
        CategoryResponse response = CategoryResponse.from(category);
        log.info("[Adapter/Web] CategoryController.getCategory() - HTTP 응답: 200 OK");
        return ApiResponseGenerator.success(response);
    }
    
    @PostMapping
    @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다.")
    @RequiredRole({"ROLE_VIP", "ROLE_PREMIUM"})
    public ResponseEntity<ApiResponseWellness<CategoryResponse>> createCategory(
            @RequestBody Category category,
            @AuthenticatedUser CurrentUser user) {
        log.info("[Adapter/Web] CategoryController.createCategory() - HTTP 요청: POST /api/v1/categories, userId={}, email={}", 
                user.getUserId(), user.getEmail());
        Category created = categoryService.createCategory(category);
        CategoryResponse response = CategoryResponse.from(created);
        log.info("[Adapter/Web] CategoryController.createCategory() - HTTP 응답: 201 CREATED");
        return ApiResponseGenerator.success(response, HttpStatus.CREATED);
    }
}

