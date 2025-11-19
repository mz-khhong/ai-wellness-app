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
}

