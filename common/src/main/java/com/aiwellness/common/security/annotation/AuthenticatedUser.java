package com.aiwellness.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * com.aiwellness.common.security.annotation
 * <p>
 * AuthenticatedUser
 * <p>
 * 현재 인증된 사용자 정보를 컨트롤러 메서드 파라미터로 주입받기 위한 어노테이션
 * <p>
 * CurrentUserArgumentResolver를 통해 CurrentUser로 자동 주입됩니다.
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
 * 
 * // 선택적 주입 (사용자가 없을 수도 있는 경우)
 * @GetMapping("/public")
 * public ResponseEntity<ApiResponseWellness<Response>> getPublic(@AuthenticatedUser(required = false) CurrentUser user) {
 *     if (user == null) {
 *         // 인증되지 않은 사용자 처리
 *     }
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
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticatedUser {
    
    /**
     * 필수 여부
     * <p>
     * true: 사용자 정보가 없으면 예외 발생 (기본값)
     * false: 사용자 정보가 없으면 null 반환
     *
     * @return 필수 여부
     */
    boolean required() default true;
}

