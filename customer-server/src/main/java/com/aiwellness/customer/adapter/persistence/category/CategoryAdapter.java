package com.aiwellness.customer.adapter.persistence.category;

import com.aiwellness.customer.domain.category.model.Category;
import com.aiwellness.customer.domain.category.port.CategoryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryAdapter implements CategoryRepositoryPort {

    private final CategoryMapper categoryMapper;

    @Override
    public Category save(Category category) {
        categoryMapper.save(category);
        return category;
    }

    @Override
    public Optional<Category> findById(Long id) {
        Category category = categoryMapper.findById(id);
        return Optional.ofNullable(category);
    }

    @Override
    public Optional<Category> findByName(String name) {
        Category category = categoryMapper.findByName(name);
        return Optional.ofNullable(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    @Override
    public List<Category> findAllActive() {
        return categoryMapper.findAllActive();
    }

    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }
}

