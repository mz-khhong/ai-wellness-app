package com.aiwellness.admin.adapter.security;

import com.aiwellness.admin.domain.model.admin.Admin;
import com.aiwellness.admin.domain.model.enums.AdminRole;
import com.aiwellness.admin.domain.model.enums.AdminStatus;
import com.aiwellness.admin.domain.port.admin.AdminRepositoryPort;
import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.aiwellness.common.security.AuthenticationException;
import com.aiwellness.common.security.AuthenticationResult;
import com.aiwellness.common.security.port.AuthenticationPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * com.aiwellness.admin.adapter.security
 * <p>
 * AdminAuthenticationAdapter
 * <p>
 * Admin 서버의 인증 어댑터
 * AuthenticationPort를 구현하여 Admin 도메인의 인증 로직을 제공합니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 */
@Slf4j
@Component
@Primary
@RequiredArgsConstructor
public class AdminAuthenticationAdapter implements AuthenticationPort {
    
    private final AdminRepositoryPort adminRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public AuthenticationResult authenticate(String email, String password) {
        log.debug("[Adapter/Security] AdminAuthenticationAdapter.authenticate() - 인증 시도: email={}", email);
        
        // 이메일로 Admin 조회
        Admin admin = adminRepositoryPort.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("[Adapter/Security] AdminAuthenticationAdapter.authenticate() - 사용자를 찾을 수 없음: email={}", email);
                    return new AuthenticationException(
                            ApiResponseWellnessCode.AUTH_USER_NOT_FOUND,
                            ApiResponseWellnessCode.AUTH_USER_NOT_FOUND.getMessageKey());
                });
        
        // 비밀번호 검증
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new AuthenticationException(
                    ApiResponseWellnessCode.AUTH_PASSWORD_MISMATCH,
                    ApiResponseWellnessCode.AUTH_PASSWORD_MISMATCH.getMessageKey());
        }
        
        // 상태 검증 (활성화된 사용자만 로그인 가능)
        if (admin.getStatus() != AdminStatus.ACTIVE) {
            log.warn("[Adapter/Security] AdminAuthenticationAdapter.authenticate() - 비활성화된 사용자: email={}, status={}", 
                    email, admin.getStatus());
            throw new AuthenticationException(
                    ApiResponseWellnessCode.AUTH_ACCOUNT_DISABLED,
                    ApiResponseWellnessCode.AUTH_ACCOUNT_DISABLED.getMessageKey());
        }
        
        // 역할 변환 (기본적으로 ADMIN 역할 사용)
        List<String> roles = List.of(AdminRole.ADMIN.getValue());
        
        log.debug("[Adapter/Security] AdminAuthenticationAdapter.authenticate() - 인증 성공: userId={}, email={}", admin.getId(), email);
        
        return AuthenticationResult.builder()
                .userId(admin.getId())
                .userUuid(admin.getUuid())
                .email(admin.getEmail())
                .name(admin.getName())
                .facilityGroupId(admin.getFacilityGroupId())
                .roles(roles)
                .build();
    }
}

