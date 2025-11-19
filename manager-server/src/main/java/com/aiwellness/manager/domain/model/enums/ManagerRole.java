package com.aiwellness.manager.domain.model.enums;

import com.aiwellness.common.security.RoleInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * com.aiwellness.manager.domain.model.enums
 * <p>
 * ManagerRole
 * <p>
 * Manager 서버 전용 Role 정의
 * <p>
 * 각 서버는 자체 Role 정책을 독립적으로 관리할 수 있습니다.
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
@Getter
public enum ManagerRole implements RoleInterface {
    /**
     * 시니어 매니저 - 모든 매니저 권한
     */
    SENIOR_MANAGER("ROLE_SENIOR_MANAGER"),
    
    /**
     * 일반 매니저 - 기본 매니저 권한
     */
    MANAGER("ROLE_MANAGER"),
    
    /**
     * 주니어 매니저 - 제한된 매니저 권한
     */
    JUNIOR_MANAGER("ROLE_JUNIOR_MANAGER"),
    
    /**
     * 팀 리더 - 팀 관리 권한
     */
    TEAM_LEADER("ROLE_TEAM_LEADER");
    
    private final String value;
    
    ManagerRole(String value) {
        this.value = value;
    }
    
    /**
     * 매니저 권한이 있는 Role인지 확인
     */
    public boolean hasManagerPermission() {
        return this == SENIOR_MANAGER || this == MANAGER || this == TEAM_LEADER;
    }
    
    /**
     * 고객 관리 권한이 있는 Role인지 확인
     */
    public boolean hasCustomerManagementPermission() {
        return this == SENIOR_MANAGER || this == MANAGER;
    }
    
    /**
     * Role 값으로부터 ManagerRole을 찾습니다.
     */
    public static ManagerRole fromValue(String value) {
        for (ManagerRole role : values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown ManagerRole: " + value);
    }
}

