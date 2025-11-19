package com.aiwellness.admin.domain.port.admin;

import com.aiwellness.admin.domain.model.admin.Admin;

import java.util.Optional;

/**
 * com.aiwellness.admin.domain.port
 * <p>
 * AdminRepositoryPort
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 14.    메가존 시스템            최초 생성
 * </pre>
 */
public interface AdminRepositoryPort {
    Admin save(Admin admin);
    Optional<Admin> findById(Long id);
    Optional<Admin> findByEmail(String email);
    void deleteById(Long id);
}

