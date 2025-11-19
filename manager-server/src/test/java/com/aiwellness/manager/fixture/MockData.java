package com.aiwellness.manager.fixture;

import com.aiwellness.manager.domain.model.manager.Manager;
import com.aiwellness.manager.domain.model.enums.ManagerStatus;

import java.time.LocalDateTime;

/**
 * com.aiwellness.manager.fixture
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
    public static Manager createManager() {
        return Manager.builder()
                .email("manager@wellness.com")
                .name("Manager User")
                .password("password123")
                .status(ManagerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Manager createManagerWithEmail(String email) {
        return Manager.builder()
                .email(email)
                .name("Manager User")
                .password("password123")
                .status(ManagerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

