package com.aiwellness.admin.adapter.security;

import com.aiwellness.admin.domain.model.admin.Admin;
import com.aiwellness.admin.domain.model.enums.AdminRole;
import com.aiwellness.admin.domain.model.enums.AdminStatus;
import com.aiwellness.admin.domain.port.admin.AdminRepositoryPort;
import com.aiwellness.common.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * com.aiwellness.admin.adapter.security
 * <p>
 * AdminUserDetailsService
 * <p>
 * Admin 서버의 UserDetailsService 구현
 * JWT 토큰에서 추출한 userId를 기반으로 DB에서 사용자 정보를 조회합니다.
 * <p>
 * <b>구현 위치:</b>
 * <ul>
 *   <li>각 서버의 adapter/security 패키지에 구현</li>
 *   <li>예: admin-server/src/main/java/com/aiwellness/admin/adapter/security/AdminUserDetailsService.java</li>
 * </ul>
 * <p>
 * <b>사용 방법:</b>
 * <ul>
 *   <li>SecurityConfig에서 @Primary로 지정하여 주입</li>
 *   <li>JwtAuthenticationFilter에서 토큰의 userId로 loadUserByUsername() 호출</li>
 * </ul>
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 20.    메가존 시스템            최초 생성
 * </pre>
 */
@Slf4j
@Component
@org.springframework.context.annotation.Primary
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {
    
    private final AdminRepositoryPort adminRepositoryPort;
    
    /**
     * 사용자 ID(문자열로 변환된 userId)로 사용자 정보를 조회합니다.
     * <p>
     * JWT 토큰에서 추출한 userId를 문자열로 변환하여 전달받습니다.
     * DB에서 최신 사용자 정보를 조회하여 CurrentUser를 생성합니다.
     *
     * @param userIdStr 사용자 ID (문자열)
     * @return CurrentUser (UserDetails 구현체)
     * @throws UsernameNotFoundException 사용자를 찾을 수 없을 때
     */
    @Override
    public UserDetails loadUserByUsername(String userIdStr) throws UsernameNotFoundException {
        log.debug("[Adapter/Security] AdminUserDetailsService.loadUserByUsername() - 사용자 조회: userId={}", userIdStr);
        
        try {
            Long userId = Long.parseLong(userIdStr);
            
            // DB에서 사용자 정보 조회
            Admin admin = adminRepositoryPort.findById(userId)
                    .orElseThrow(() -> {
                        log.warn("[Adapter/Security] AdminUserDetailsService.loadUserByUsername() - 사용자를 찾을 수 없음: userId={}", userId);
                        return new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId);
                    });
            
            // 상태 검증 (활성화된 사용자만 허용)
            if (admin.getStatus() != AdminStatus.ACTIVE) {
                log.warn("[Adapter/Security] AdminUserDetailsService.loadUserByUsername() - 비활성화된 사용자: userId={}, status={}", 
                        userId, admin.getStatus());
                throw new UsernameNotFoundException("비활성화된 사용자입니다: " + userId);
            }
            
            // 역할 변환
            List<String> roles = List.of(AdminRole.ADMIN.getValue());
            
            log.debug("[Adapter/Security] AdminUserDetailsService.loadUserByUsername() - 사용자 조회 성공: userId={}, email={}", 
                    admin.getId(), admin.getEmail());
            
            // CurrentUser 생성 및 반환
            return CurrentUser.builder()
                    .userId(admin.getId())
                    .userUuid(admin.getUuid())
                    .email(admin.getEmail())
                    .password(null)  // JWT 인증에서는 password 불필요
                    .name(admin.getName())
                    .facilityGroupId(admin.getFacilityGroupId())
                    .roles(roles)
                    .build();
                    
        } catch (NumberFormatException e) {
            log.error("[Adapter/Security] AdminUserDetailsService.loadUserByUsername() - 잘못된 userId 형식: {}", userIdStr);
            throw new UsernameNotFoundException("잘못된 사용자 ID 형식입니다: " + userIdStr);
        }
    }
}

