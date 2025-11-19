package com.aiwellness.admin.fixture;

import com.aiwellness.admin.domain.model.admin.Admin;

import com.aiwellness.admin.domain.model.enums.AdminStatus;
import java.time.LocalDateTime;

/**
 * com.aiwellness.admin.fixture
 * <p>
 * MockData
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
public class MockData {
    public static Admin createAdmin() {
        return Admin.builder()
                .email("admin@wellness.com")
                .name("Admin User")
                .password("password123")
                .status(AdminStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Admin createAdminWithEmail(String email) {
        return Admin.builder()
                .email(email)
                .name("Admin User")
                .password("password123")
                .status(AdminStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

