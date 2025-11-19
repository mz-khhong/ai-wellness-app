package com.aiwellness.manager.adapter.persistence.manager;

import com.aiwellness.manager.domain.model.manager.Manager;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManagerMapper {
    int save(Manager manager);
    Manager findById(Long id);
    Manager findByEmail(String email);
    int deleteById(Long id);
}

