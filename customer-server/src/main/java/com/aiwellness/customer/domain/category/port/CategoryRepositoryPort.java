package com.aiwellness.customer.domain.category.port;

import com.aiwellness.customer.domain.category.model.Category;

import java.util.List;
import java.util.Optional;

/**
 * com.aiwellness.customer.domain.category.port
 * <p>
 * CategoryRepositoryPort
 * <p>
 * 카테고리 도메인의 아웃바운드 포트
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 */
public interface CategoryRepositoryPort {
    Category save(Category category);
    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);
    List<Category> findAll();
    List<Category> findAllActive();
    void deleteById(Long id);
    
    // 페이징 조회
    List<Category> findAllWithPaging(int offset, int limit);
    List<Category> findAllActiveWithPaging(int offset, int limit);
    
    // 페이징 조회 (정렬 지원)
    List<Category> findAllWithPaging(int offset, int limit, String orderBy);
    List<Category> findAllActiveWithPaging(int offset, int limit, String orderBy);
    
    // 전체 개수 조회
    long countAll();
    long countAllActive();
}

