package com.aiwellness.manager.adapter.security;

import com.aiwellness.manager.domain.model.manager.Manager;
import com.aiwellness.manager.domain.model.enums.ManagerRole;
import com.aiwellness.manager.domain.model.enums.ManagerStatus;
import com.aiwellness.manager.domain.port.manager.ManagerRepositoryPort;
import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.aiwellness.common.security.AuthenticationException;
import com.aiwellness.common.security.AuthenticationResult;
import com.aiwellness.common.security.port.AuthenticationPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * com.aiwellness.manager.adapter.security
 * <p>
 * ManagerAuthenticationAdapter
 * <p>
 * Manager 서버의 인증 어댑터
 * AuthenticationPort를 구현하여 Manager 도메인의 인증 로직을 제공합니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 */
@Slf4j
@Component
@org.springframework.context.annotation.Primary
@RequiredArgsConstructor
public class ManagerAuthenticationAdapter implements AuthenticationPort {
    
    private final ManagerRepositoryPort managerRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public AuthenticationResult authenticate(String email, String password) {
        log.debug("[Adapter/Security] ManagerAuthenticationAdapter.authenticate() - 인증 시도: email={}", email);
        
        // 이메일로 Manager 조회
        Manager manager = managerRepositoryPort.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("[Adapter/Security] ManagerAuthenticationAdapter.authenticate() - 사용자를 찾을 수 없음: email={}", email);
                    return new AuthenticationException(
                            ApiResponseWellnessCode.AUTH_USER_NOT_FOUND,
                            ApiResponseWellnessCode.AUTH_USER_NOT_FOUND.getMessageKey());
                });
        
        // 비밀번호 검증
        if (!passwordEncoder.matches(password, manager.getPassword())) {
            log.warn("[Adapter/Security] ManagerAuthenticationAdapter.authenticate() - 비밀번호 불일치: email={}", email);
            throw new AuthenticationException(
                    ApiResponseWellnessCode.AUTH_PASSWORD_MISMATCH,
                    ApiResponseWellnessCode.AUTH_PASSWORD_MISMATCH.getMessageKey());
        }
        
        // 상태 검증 (활성화된 사용자만 로그인 가능)
        if (manager.getStatus() != ManagerStatus.ACTIVE) {
            log.warn("[Adapter/Security] ManagerAuthenticationAdapter.authenticate() - 비활성화된 사용자: email={}, status={}", 
                    email, manager.getStatus());
            throw new AuthenticationException(
                    ApiResponseWellnessCode.AUTH_ACCOUNT_DISABLED,
                    ApiResponseWellnessCode.AUTH_ACCOUNT_DISABLED.getMessageKey());
        }
        
        // 역할 변환 (기본적으로 MANAGER 역할 사용)
        List<String> roles = List.of(ManagerRole.MANAGER.getValue());
        
        log.info("[Adapter/Security] ManagerAuthenticationAdapter.authenticate() - 인증 성공: userId={}, email={}", 
                manager.getId(), email);
        
        return AuthenticationResult.builder()
                .userId(manager.getId())
                .userUuid(manager.getUuid())
                .email(manager.getEmail())
                .name(manager.getName())
                .facilityGroupId(manager.getFacilityGroupId())
                .roles(roles)
                .build();
    }
}

