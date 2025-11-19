package com.aiwellness.customer.adapter.security;

import com.aiwellness.customer.domain.model.customer.Customer;
import com.aiwellness.customer.domain.model.enums.CustomerRole;
import com.aiwellness.customer.domain.port.customer.CustomerRepositoryPort;
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
 * com.aiwellness.customer.adapter.security
 * <p>
 * CustomerAuthenticationAdapter
 * <p>
 * Customer 서버의 인증 어댑터
 * AuthenticationPort를 구현하여 Customer 도메인의 인증 로직을 제공합니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 */
@Slf4j
@Component
@org.springframework.context.annotation.Primary
@RequiredArgsConstructor
public class CustomerAuthenticationAdapter implements AuthenticationPort {
    
    private final CustomerRepositoryPort customerRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public AuthenticationResult authenticate(String email, String password) {
        log.debug("[Adapter/Security] CustomerAuthenticationAdapter.authenticate() - 인증 시도: email={}", email);
        
        // 이메일로 Customer 조회
        Customer customer = customerRepositoryPort.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("[Adapter/Security] CustomerAuthenticationAdapter.authenticate() - 사용자를 찾을 수 없음: email={}", email);
                    return new AuthenticationException(
                            ApiResponseWellnessCode.AUTH_USER_NOT_FOUND,
                            ApiResponseWellnessCode.AUTH_USER_NOT_FOUND.getMessageKey());
                });
        
        // 비밀번호 검증
        if (!passwordEncoder.matches(password, customer.getPassword())) {
            log.warn("[Adapter/Security] CustomerAuthenticationAdapter.authenticate() - 비밀번호 불일치: email={}", email);
            throw new AuthenticationException(
                    ApiResponseWellnessCode.AUTH_PASSWORD_MISMATCH,
                    ApiResponseWellnessCode.AUTH_PASSWORD_MISMATCH.getMessageKey());
        }
        
        // 상태 검증 (활성화된 사용자만 로그인 가능)
        if (customer.getStatus() != com.aiwellness.customer.domain.model.enums.CustomerStatus.ACTIVE) {
            log.warn("[Adapter/Security] CustomerAuthenticationAdapter.authenticate() - 비활성화된 사용자: email={}, status={}", 
                    email, customer.getStatus());
            throw new AuthenticationException(
                    ApiResponseWellnessCode.AUTH_ACCOUNT_DISABLED,
                    ApiResponseWellnessCode.AUTH_ACCOUNT_DISABLED.getMessageKey());
        }
        
        // 역할 변환 (기본적으로 CUSTOMER 역할 사용)
        List<String> roles = List.of(CustomerRole.CUSTOMER.getValue());
        
        log.info("[Adapter/Security] CustomerAuthenticationAdapter.authenticate() - 인증 성공: userId={}, email={}", 
                customer.getId(), email);
        
        return AuthenticationResult.builder()
                .userId(customer.getId())
                .userUuid(customer.getUuid())
                .email(customer.getEmail())
                .name(customer.getName())
                .facilityGroupId(customer.getFacilityGroupId())
                .roles(roles)
                .build();
    }
}

