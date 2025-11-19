package com.aiwellness.customer.adapter.web.category;

import com.aiwellness.customer.adapter.web.category.dto.CategoryResponse;
import com.aiwellness.customer.application.service.category.CategoryService;
import com.aiwellness.customer.domain.category.model.Category;
import com.aiwellness.customer.domain.model.enums.CustomerRole;
import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.security.annotation.RequiredRole;
import com.aiwellness.common.support.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ApiResponseWellness<List<CategoryResponse>> getAllCategories() {
        log.info("[Adapter/Web] CategoryController.getAllCategories() - HTTP 요청: GET /api/v1/categories");
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryResponse> responses = categories.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
        log.info("[Adapter/Web] CategoryController.getAllCategories() - HTTP 응답: 200 OK, count={}", responses.size());
        return ApiResponseGenerator.success(responses);
    }
    
    @GetMapping("/active")
    @Operation(summary = "활성 카테고리 목록 조회", description = "활성화된 카테고리 목록을 조회합니다.")
    public ApiResponseWellness<List<CategoryResponse>> getActiveCategories() {
        log.info("[Adapter/Web] CategoryController.getActiveCategories() - HTTP 요청: GET /api/v1/categories/active");
        List<Category> categories = categoryService.getActiveCategories();
        List<CategoryResponse> responses = categories.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
        log.info("[Adapter/Web] CategoryController.getActiveCategories() - HTTP 응답: 200 OK, count={}", responses.size());
        return ApiResponseGenerator.success(responses);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "카테고리 조회", description = "ID로 카테고리 정보를 조회합니다.")
    public ApiResponseWellness<CategoryResponse> getCategory(@PathVariable Long id) {
        log.info("[Adapter/Web] CategoryController.getCategory() - HTTP 요청: GET /api/v1/categories/{}", id);
        Category category = categoryService.getCategory(id);
        CategoryResponse response = CategoryResponse.from(category);
        log.info("[Adapter/Web] CategoryController.getCategory() - HTTP 응답: 200 OK");
        return ApiResponseGenerator.success(response);
    }
    
    @PostMapping
    @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다.")
    @RequiredRole({"ROLE_VIP", "ROLE_PREMIUM"})
    public ResponseEntity<ApiResponseWellness<CategoryResponse>> createCategory(@RequestBody Category category) {
        log.info("[Adapter/Web] CategoryController.createCategory() - HTTP 요청: POST /api/v1/categories");
        Category created = categoryService.createCategory(category);
        CategoryResponse response = CategoryResponse.from(created);
        log.info("[Adapter/Web] CategoryController.createCategory() - HTTP 응답: 201 CREATED");
        return ApiResponseGenerator.success(response, HttpStatus.CREATED);
    }
}

