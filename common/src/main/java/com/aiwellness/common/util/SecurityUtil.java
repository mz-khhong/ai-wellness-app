package com.aiwellness.common.util;

import com.aiwellness.common.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * com.aiwellness.common.util
 * <p>
 * SecurityUtil
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
public class SecurityUtil {
    
    public static SecurityUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !(authentication.getPrincipal() instanceof SecurityUser)) {
            return null;
        }
        
        return (SecurityUser) authentication.getPrincipal();
    }
    
    public static Long getCurrentUserId() {
        SecurityUser user = getCurrentUser();
        return user != null ? user.getUserId() : null;
    }
    
    public static String getCurrentUserEmail() {
        SecurityUser user = getCurrentUser();
        return user != null ? user.getEmail() : null;
    }
    
    public static boolean hasRole(String role) {
        SecurityUser user = getCurrentUser();
        if (user == null) {
            return false;
        }
        
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }
}

