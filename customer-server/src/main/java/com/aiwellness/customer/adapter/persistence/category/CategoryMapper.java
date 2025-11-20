package com.aiwellness.customer.adapter.persistence.category;

import com.aiwellness.customer.domain.category.model.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    int save(Category category);
    Category findById(Long id);
    Category findByName(String name);
    List<Category> findAll();
    List<Category> findAllActive();
    int deleteById(Long id);
    
    // 페이징 조회 (정렬 지원)
    List<Category> findAllWithPaging(int offset, int limit, String orderBy);
    List<Category> findAllActiveWithPaging(int offset, int limit, String orderBy);
    
    // 전체 개수 조회
    long countAll();
    long countAllActive();
}

