package com.aiwellness.admin.adapter.persistence.admin;

import com.aiwellness.admin.domain.model.admin.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AdminMapper {
    int insert(Admin admin);
    int update(Admin admin);
    Admin findById(Long id);
    Admin findByEmail(String email);
    int deleteById(Long id);
}

