package com.aiwellness.admin.domain.model.enums;

import com.aiwellness.common.security.RoleInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * com.aiwellness.admin.domain.model.enums
 * <p>
 * AdminRole
 * <p>
 * Admin 서버 전용 Role 정의
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
public enum AdminRole implements RoleInterface {
    /**
     * 최고 관리자 - 모든 권한
     */
    SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    
    /**
     * 일반 관리자 - 기본 관리 권한
     */
    ADMIN("ROLE_ADMIN"),
    
    /**
     * 운영 관리자 - 운영 관련 권한만
     */
    OPERATOR("ROLE_OPERATOR"),
    
    /**
     * 조회 전용 관리자 - 조회 권한만
     */
    VIEWER("ROLE_VIEWER");
    
    private final String value;
    
    AdminRole(String value) {
        this.value = value;
    }
    
    /**
     * 관리 권한이 있는 Role인지 확인
     */
    public boolean hasAdminPermission() {
        return this == SUPER_ADMIN || this == ADMIN || this == OPERATOR;
    }
    
    /**
     * 수정 권한이 있는 Role인지 확인
     */
    public boolean hasModifyPermission() {
        return this == SUPER_ADMIN || this == ADMIN;
    }
    
    /**
     * Role 값으로부터 AdminRole을 찾습니다.
     */
    public static AdminRole fromValue(String value) {
        for (AdminRole role : values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown AdminRole: " + value);
    }
}

