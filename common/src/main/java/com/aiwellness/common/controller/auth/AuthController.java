package com.aiwellness.common.controller.auth;

import com.aiwellness.common.controller.auth.dto.request.LoginRequest;
import com.aiwellness.common.controller.auth.dto.response.LoginResponse;
import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.service.AuthService;
import com.aiwellness.common.support.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * com.aiwellness.common.controller
 * <p>
 * AuthController
 * <p>
 * 인증 API 컨트롤러 (Infrastructure/Web Adapter 계층)
 * 각 서버(admin, manager, customer)에서 AuthenticationPort를 구현하여 사용합니다.
 * <p>
 * <b>호출 및 응답 처리 흐름:</b>
 * <pre>
 * 1. HTTP 요청 수신
 *    ↓
 * 2. 입력 검증 (@Valid)
 *    - 이메일 형식 검증 (@Pattern)
 *    - 필수 필드 검증 (@NotBlank)
 *    - 실패 시: MethodArgumentNotValidException → GlobalExceptionHandler → 400 BAD_REQUEST
 *    ↓
 * 3. AuthService.login() 호출 (Application Service)
 *    ↓
 * 4. AuthenticationPort.authenticate() 호출 (Domain Port)
 *    ↓
 * 5. AdminAuthenticationAdapter.authenticate() (Infrastructure Adapter)
 *    - AdminRepositoryPort.findByEmail() 호출
 *    - 비밀번호 검증 (비즈니스 검증)
 *    - 상태 검증 (비즈니스 검증)
 *    - 실패 시: AuthenticationException → GlobalExceptionHandler → 401 UNAUTHORIZED
 *    ↓
 * 6. JWT 토큰 생성
 *    ↓
 * 7. LoginResultDto 반환
 *    ↓
 * 8. LoginResponse 생성 (DTO 변환)
 *    ↓
 * 9. HTTP 응답 반환 (200 OK)
 * </pre>
 * <p>
 * <b>검증 계층:</b>
 * <ul>
 *   <li><b>입력 검증</b>: Controller에서 @Valid (Bean Validation)
 *       <ul>
 *         <li>이메일 형식: @Pattern(regexp = RegexUtil.EMAIL)</li>
 *         <li>필수 필드: @NotBlank</li>
 *         <li>예외: MethodArgumentNotValidException</li>
 *         <li>처리: GlobalExceptionHandler → 400 BAD_REQUEST</li>
 *       </ul>
 *   </li>
 *   <li><b>비즈니스 검증</b>: Adapter에서 수행
 *       <ul>
 *         <li>사용자 존재 여부: AdminRepositoryPort.findByEmail()</li>
 *         <li>비밀번호 일치: PasswordEncoder.matches()</li>
 *         <li>계정 상태: AdminStatus.ACTIVE</li>
 *         <li>예외: AuthenticationException</li>
 *         <li>처리: GlobalExceptionHandler → 401 UNAUTHORIZED</li>
 *       </ul>
 *   </li>
 * </ul>
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
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 API")
public class AuthController {
    
    private final AuthService authService;

    /**
     * 로그인 API

     * @param request 로그인 요청 DTO (이메일, 비밀번호)
     * @return LoginResponse (토큰 및 사용자 정보)
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하여 JWT 토큰을 발급받습니다.")
    public ResponseEntity<ApiResponseWellness<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.info("[Adapter/Web] AuthController.login() - HTTP 요청: POST /api/v1/auth/login, email={}", request.getEmail());
        
        // Application Service를 통한 로그인 처리
        LoginResponse response = authService.login(request.getEmail(), request.getPassword());
        
        log.info("[Adapter/Web] AuthController.login() - HTTP 응답: 200 OK, userId={}", response.getUserId());
        return ApiResponseGenerator.success("auth.login.success", response, HttpStatus.OK);
    }
}

