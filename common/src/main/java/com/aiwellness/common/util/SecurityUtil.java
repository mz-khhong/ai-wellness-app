package com.aiwellness.common.util;

import com.aiwellness.common.security.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * com.aiwellness.common.util
 * <p>
 * SecurityUtil
 * <p>
 * 현재 인증된 사용자 정보를 조회하는 유틸리티 클래스
 * <p>
 * Service, Repository 등 Controller가 아닌 계층에서 사용합니다.
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
 *  2025. 11. 20.    메가존 시스템            SecurityUser → CurrentUser로 변경
 * </pre>
 */
public class SecurityUtil {
    
    /**
     * 현재 인증된 사용자 정보를 조회합니다.
     *
     * @return CurrentUser (인증되지 않은 경우 null)
     */
    public static CurrentUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !(authentication.getPrincipal() instanceof CurrentUser)) {
            return null;
        }
        
        return (CurrentUser) authentication.getPrincipal();
    }
    
    /**
     * 현재 인증된 사용자 ID를 조회합니다.
     *
     * @return 사용자 ID (인증되지 않은 경우 null)
     */
    public static Long getCurrentUserId() {
        CurrentUser user = getCurrentUser();
        return user != null ? user.getUserId() : null;
    }
    
    /**
     * 현재 인증된 사용자 이메일을 조회합니다.
     *
     * @return 사용자 이메일 (인증되지 않은 경우 null)
     */
    public static String getCurrentUserEmail() {
        CurrentUser user = getCurrentUser();
        return user != null ? user.getEmail() : null;
    }
    
    /**
     * 현재 인증된 사용자가 지정한 역할을 가지고 있는지 확인합니다.
     *
     * @param role 확인할 역할 (예: "ADMIN", "MANAGER")
     * @return 역할 보유 여부
     */
    public static boolean hasRole(String role) {
        CurrentUser user = getCurrentUser();
        if (user == null) {
            return false;
        }
        
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }
}

