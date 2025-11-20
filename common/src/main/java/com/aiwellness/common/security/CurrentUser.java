package com.aiwellness.common.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * com.aiwellness.common.security
 * <p>
 * CurrentUser
 * <p>
 * 현재 인증된 사용자 정보를 담는 클래스
 * <p>
 * UserDetails를 구현하여 Spring Security와 호환되며, 컨트롤러에서 @AuthenticatedUser 어노테이션과 함께 사용됩니다.
 * <p>
 * 사용 예시:
 * <pre>
 * {@code
 * @GetMapping("/profile")
 * public ResponseEntity<ApiResponseWellness<UserResponse>> getProfile(@AuthenticatedUser CurrentUser user) {
 *     Long userId = user.getUserId();
 *     String email = user.getEmail();
 *     // ...
 * }
 * }
 * </pre>
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
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUser implements UserDetails {
    private Long userId;
    private String userUuid;        // 사용자 UUID
    private String email;
    private String password;
    private String name;
    private Long facilityGroupId;    // 소속 시설 그룹 ID
    private List<String> roles;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            return List.of();
        }
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return email;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}

