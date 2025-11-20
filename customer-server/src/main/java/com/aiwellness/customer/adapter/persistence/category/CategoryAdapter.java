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

    @Override
    public List<Category> findAllWithPaging(int offset, int limit) {
        return findAllWithPaging(offset, limit, null);
    }
    
    @Override
    public List<Category> findAllWithPaging(int offset, int limit, String orderBy) {
        return categoryMapper.findAllWithPaging(offset, limit, orderBy);
    }

    @Override
    public List<Category> findAllActiveWithPaging(int offset, int limit) {
        return findAllActiveWithPaging(offset, limit, null);
    }
    
    @Override
    public List<Category> findAllActiveWithPaging(int offset, int limit, String orderBy) {
        return categoryMapper.findAllActiveWithPaging(offset, limit, orderBy);
    }

    @Override
    public long countAll() {
        return categoryMapper.countAll();
    }

    @Override
    public long countAllActive() {
        return categoryMapper.countAllActive();
    }
}

