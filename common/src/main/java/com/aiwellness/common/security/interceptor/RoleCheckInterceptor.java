package com.aiwellness.common.security.interceptor;

import com.aiwellness.common.security.RoleInterface;
import com.aiwellness.common.security.SecurityUser;
import com.aiwellness.common.security.annotation.RequiredRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * com.aiwellness.common.security.interceptor
 * <p>
 * RoleCheckInterceptor
 * <p>
 * 각 서버별 Role을 지원하는 제네릭 Role 검증 인터셉터
 * <p>
 * RequiredRole 어노테이션에서 지정한 Role을 검증합니다.
 * 각 서버는 자체 Role enum을 사용할 수 있습니다 (RoleInterface를 구현해야 함)
 *
 * @author 메가존 시스템
 * @version 2.0
 * @since 2025. 11. 14.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 14.    메가존 시스템            최초 생성
 *  2025. 11. 14.    메가존 시스템            각 서버별 Role 지원하도록 개선
 * </pre>
 */
@Slf4j
@Component
public class RoleCheckInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiredRole requiredRole = handlerMethod.getMethodAnnotation(RequiredRole.class);
        
        if (requiredRole == null) {
            requiredRole = handlerMethod.getBeanType().getAnnotation(RequiredRole.class);
        }
        
        if (requiredRole == null) {
            return true;
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !(authentication.getPrincipal() instanceof SecurityUser)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Set<String> userRoles = securityUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        
        // RequiredRole 어노테이션에서 지정한 Role 값들
        Set<String> requiredRoles = Arrays.stream(requiredRole.value())
                .collect(Collectors.toSet());
        
        boolean hasRequiredRole = userRoles.stream()
                .anyMatch(requiredRoles::contains);
        
        if (!hasRequiredRole) {
            log.warn("Access denied for user: {} with roles: {}, required: {}", 
                    securityUser.getEmail(), userRoles, requiredRoles);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        
        return true;
    }
}

