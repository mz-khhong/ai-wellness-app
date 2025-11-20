package com.aiwellness.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * com.aiwellness.common.security
 * <p>
 * JwtAuthenticationFilter
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
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    
    private final JwtTokenProvider jwtTokenProvider;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String token = resolveToken(request);
        
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            String email = jwtTokenProvider.getEmail(token);
            Long userId = jwtTokenProvider.getUserId(token);
            String userUuid = jwtTokenProvider.getUserUuid(token);
            Long facilityGroupId = jwtTokenProvider.getFacilityGroupId(token);
            List<String> roles = jwtTokenProvider.getRoles(token);
            
            CurrentUser currentUser = CurrentUser.builder()
                    .userId(userId)
                    .userUuid(userUuid)
                    .email(email)
                    .password(null)  // JWT 인증에서는 password 불필요
                    .name(null)  // 토큰에 name 정보가 없으면 null
                    .facilityGroupId(facilityGroupId)
                    .roles(roles)
                    .build();
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Set Authentication for user: {}", email);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}

