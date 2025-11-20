package com.aiwellness.common.service;

import com.aiwellness.common.controller.auth.dto.response.LoginResponse;
import com.aiwellness.common.security.AuthenticationResult;
import com.aiwellness.common.security.JwtTokenProvider;
import com.aiwellness.common.security.port.AuthenticationPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * com.aiwellness.common.service
 * <p>
 * AuthService
 * <p>
 * 인증 관련 비즈니스 로직을 처리하는 Application Service
 * 헥사고날 아키텍처의 Application 계층에 해당합니다.
 * <p>
 * <b>호출 흐름:</b>
 * <pre>
 * Controller (Infrastructure/Web Adapter)
 *   ↓ @Valid 입력 검증
 *   ↓ AuthService.login()
 * Service (Application Service) ← 현재 위치
 *   ↓ AuthenticationPort.authenticate()
 * Port (Domain Port)
 *   ↓ 구현체 호출
 * Adapter (Infrastructure Adapter)
 *   ↓ AdminRepositoryPort.findByEmail()
 *   ↓ 비밀번호 검증, 상태 검증 (비즈니스 검증)
 * Repository Port (Domain Port)
 *   ↓ 구현체 호출
 * Repository Adapter (Infrastructure/Persistence Adapter)
 *   ↓ DB 조회
 * </pre>
 * <p>
 * <b>검증 계층:</b>
 * <ul>
 *   <li>입력 검증: Controller에서 @Valid (Bean Validation) → MethodArgumentNotValidException</li>
 *   <li>비즈니스 검증: Adapter에서 수행 (비밀번호, 상태 등) → AuthenticationException</li>
 * </ul>
 * <p>
 * <b>예외 처리:</b>
 * <ul>
 *   <li>MethodArgumentNotValidException → GlobalExceptionHandler → 400 BAD_REQUEST</li>
 *   <li>AuthenticationException → GlobalExceptionHandler → 401 UNAUTHORIZED</li>
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
@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final AuthenticationPort authenticationPort;
    private final JwtTokenProvider jwtTokenProvider;
    
    /**
     * 로그인 처리
     * <p>
     * 1. AuthenticationPort를 통한 사용자 인증
     *    - 사용자 조회 (AdminRepositoryPort)
     *    - 비밀번호 검증 (비즈니스 검증)
     *    - 상태 검증 (비즈니스 검증)
     * 2. JWT 토큰 생성
     * 3. LoginResultDto 반환
     * <p>
     * <b>예외:</b>
     * <ul>
     *   <li>AuthenticationException: 인증 실패 시 (사용자 없음, 비밀번호 불일치, 계정 비활성화)</li>
     * </ul>
     *
     * @param email 이메일
     * @param password 비밀번호
     * @return LoginResponse (토큰 및 사용자 정보)
     * @throws com.aiwellness.common.security.AuthenticationException 인증 실패 시
     */
    public LoginResponse login(String email, String password) {
        log.debug("[Application/Service] AuthService.login() - 로그인 시도: email={}", email);
        
        // AuthenticationPort를 통한 인증
        // - 사용자 조회, 비밀번호 검증, 상태 검증이 내부에서 수행됨
        AuthenticationResult authResult = authenticationPort.authenticate(email, password);
        
        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(
                authResult.getEmail(),
                authResult.getUserId(),
                authResult.getUserUuid(),
                authResult.getFacilityGroupId(),
                authResult.getRoles()
        );
        
        log.info("[Application/Service] AuthService.login() - 로그인 성공: userId={}, email={}", authResult.getUserId(), email);
        
        // LoginResponse 생성 및 반환
        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(authResult.getUserId())
                .userUuid(authResult.getUserUuid())
                .email(authResult.getEmail())
                .name(authResult.getName())
                .facilityGroupId(authResult.getFacilityGroupId())
                .roles(authResult.getRoles())
                .build();
    }
}
