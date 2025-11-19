package com.aiwellness.customer.application.service.category;

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
}

