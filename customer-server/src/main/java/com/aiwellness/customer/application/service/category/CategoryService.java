package com.aiwellness.customer.application.service.category;

import com.aiwellness.common.dto.request.PageRequest;
import com.aiwellness.common.dto.response.PageResponse;
import com.aiwellness.customer.adapter.web.category.dto.response.CategoryResponse;
import com.aiwellness.customer.domain.category.model.Category;
import com.aiwellness.customer.domain.category.port.CategoryRepositoryPort;
import com.aiwellness.customer.exception.CustomerBusinessException;
import com.aiwellness.customer.exception.CustomerErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * com.aiwellness.customer.application.service.category
 * <p>
 * CategoryService
 * <p>
 * 카테고리 도메인의 Use Case 구현
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED)
public class CategoryService {
    
    private final CategoryRepositoryPort categoryRepositoryPort;
    
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Category getCategory(Long id) {
        log.info("[Application/Service] CategoryService.getCategory() - Use Case 시작: id={}", id);
        
        Category category = categoryRepositoryPort.findById(id)
                .orElseThrow(() -> new CustomerBusinessException(CustomerErrorCode.ENTITY_NOT_FOUND));
        
        log.info("[Application/Service] CategoryService.getCategory() - Use Case 완료: id={}", id);
        return category;
    }
    
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<Category> getAllCategories() {
        log.info("[Application/Service] CategoryService.getAllCategories() - Use Case 시작");
        
        List<Category> categories = categoryRepositoryPort.findAll();
        
        log.info("[Application/Service] CategoryService.getAllCategories() - Use Case 완료: count={}", categories.size());
        return categories;
    }
    
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<Category> getActiveCategories() {
        log.info("[Application/Service] CategoryService.getActiveCategories() - Use Case 시작");
        
        List<Category> categories = categoryRepositoryPort.findAllActive();
        
        log.info("[Application/Service] CategoryService.getActiveCategories() - Use Case 완료: count={}", categories.size());
        return categories;
    }
    
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Category createCategory(Category category) {
        log.info("[Application/Service] CategoryService.createCategory() - Use Case 시작: name={}", category.getName());
        
        // 이름 중복 체크
        categoryRepositoryPort.findByName(category.getName())
                .ifPresent(existing -> {
                    log.warn("[Application/Service] CategoryService.createCategory() - 이름 중복: {}", category.getName());
                    throw new CustomerBusinessException(CustomerErrorCode.ENTITY_DUPLICATE);
                });
        
        Category saved = categoryRepositoryPort.save(category);
        
        log.info("[Application/Service] CategoryService.createCategory() - Use Case 완료: id={}, name={}", 
                saved.getId(), saved.getName());
        return saved;
    }
    
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public PageResponse<CategoryResponse> getAllCategoriesWithPaging(PageRequest pageRequest) {
        log.info("[Application/Service] CategoryService.getAllCategoriesWithPaging() - Use Case 시작: page={}, size={}, sort={}, direction={}", 
                pageRequest.getPage(), pageRequest.getSize(), pageRequest.getSort(), pageRequest.getDirection());
        
        // 허용된 정렬 필드 목록 (SQL Injection 방지)
        List<String> allowedSortFields = List.of("id", "name", "displayOrder", "createdAt", "updatedAt");
        
        // ORDER BY 절 생성
        String orderBy = pageRequest.toOrderByClause(allowedSortFields);
        
        // 페이징 조회
        List<Category> categories;
        if (orderBy != null && !orderBy.isEmpty()) {
            // 정렬이 있는 경우
            categories = categoryRepositoryPort.findAllWithPaging(
                    pageRequest.getOffset(), 
                    pageRequest.getSize(), 
                    orderBy
            );
        } else {
            // 기본 정렬
            categories = categoryRepositoryPort.findAllWithPaging(
                    pageRequest.getOffset(), 
                    pageRequest.getSize()
            );
        }
        
        // 전체 개수 조회
        long totalElements = categoryRepositoryPort.countAll();
        
        // DTO 변환
        List<CategoryResponse> content = categories.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
        
        PageResponse<CategoryResponse> response = PageResponse.of(
                content,
                pageRequest.getPage(),
                pageRequest.getSize(),
                totalElements
        );
        
        log.info("[Application/Service] CategoryService.getAllCategoriesWithPaging() - Use Case 완료: totalElements={}, totalPages={}", 
                totalElements, response.getTotalPages());
        return response;
    }
    
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public PageResponse<CategoryResponse> getActiveCategoriesWithPaging(PageRequest pageRequest) {
        log.info("[Application/Service] CategoryService.getActiveCategoriesWithPaging() - Use Case 시작: page={}, size={}, sort={}, direction={}", 
                pageRequest.getPage(), pageRequest.getSize(), pageRequest.getSort(), pageRequest.getDirection());
        
        // 허용된 정렬 필드 목록 (SQL Injection 방지)
        List<String> allowedSortFields = List.of("id", "name", "displayOrder", "createdAt", "updatedAt");
        
        // ORDER BY 절 생성
        String orderBy = pageRequest.toOrderByClause(allowedSortFields);
        
        // 페이징 조회
        List<Category> categories;
        if (orderBy != null && !orderBy.isEmpty()) {
            // 정렬이 있는 경우
            categories = categoryRepositoryPort.findAllActiveWithPaging(
                    pageRequest.getOffset(), 
                    pageRequest.getSize(), 
                    orderBy
            );
        } else {
            // 기본 정렬
            categories = categoryRepositoryPort.findAllActiveWithPaging(
                    pageRequest.getOffset(), 
                    pageRequest.getSize()
            );
        }
        
        // 전체 개수 조회
        long totalElements = categoryRepositoryPort.countAllActive();
        
        // DTO 변환
        List<CategoryResponse> content = categories.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
        
        PageResponse<CategoryResponse> response = PageResponse.of(
                content,
                pageRequest.getPage(),
                pageRequest.getSize(),
                totalElements
        );
        
        log.info("[Application/Service] CategoryService.getActiveCategoriesWithPaging() - Use Case 완료: totalElements={}, totalPages={}", 
                totalElements, response.getTotalPages());
        return response;
    }
}

