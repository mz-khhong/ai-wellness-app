package com.aiwellness.customer.domain.model.enums;

import com.aiwellness.common.security.RoleInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * com.aiwellness.customer.domain.model.enums
 * <p>
 * CustomerRole
 * <p>
 * Customer 서버 전용 Role 정의
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
public enum CustomerRole implements RoleInterface {
    /**
     * VIP 고객 - 모든 프리미엄 기능 사용 가능
     */
    VIP("ROLE_VIP"),
    
    /**
     * 프리미엄 고객 - 프리미엄 기능 사용 가능
     */
    PREMIUM("ROLE_PREMIUM"),
    
    /**
     * 일반 고객 - 기본 기능 사용 가능
     */
    CUSTOMER("ROLE_CUSTOMER"),
    
    /**
     * 게스트 - 제한된 기능만 사용 가능
     */
    GUEST("ROLE_GUEST");
    
    private final String value;
    
    CustomerRole(String value) {
        this.value = value;
    }
    
    /**
     * 프리미엄 기능 사용 권한이 있는 Role인지 확인
     */
    public boolean hasPremiumAccess() {
        return this == VIP || this == PREMIUM;
    }
    
    /**
     * 기본 기능 사용 권한이 있는 Role인지 확인
     */
    public boolean hasBasicAccess() {
        return this != GUEST;
    }
    
    /**
     * Role 값으로부터 CustomerRole을 찾습니다.
     */
    public static CustomerRole fromValue(String value) {
        for (CustomerRole role : values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown CustomerRole: " + value);
    }
}

