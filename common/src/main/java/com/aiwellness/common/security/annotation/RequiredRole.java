package com.aiwellness.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * com.aiwellness.common.security.annotation
 * <p>
 * RequiredRole
 * <p>
 * 메서드 또는 클래스에 필요한 Role을 지정하는 어노테이션
 * <p>
 * 각 서버는 자체 Role enum을 사용할 수 있으며, Role enum의 getValue()로 얻은 문자열 값을 지정합니다.
 * <p>
 * 사용 예시:
 * <pre>
 * // Admin 서버
 * {@code @RequiredRole({"ROLE_SUPER_ADMIN", "ROLE_ADMIN"})}
 * 
 * // Manager 서버
 * {@code @RequiredRole({"ROLE_SENIOR_MANAGER", "ROLE_MANAGER"})}
 * 
 * // Customer 서버
 * {@code @RequiredRole({"ROLE_VIP", "ROLE_PREMIUM"})}
 * </pre>
 *
 * @author 메가존 시스템
 * @version 2.0
 * @since 2025. 11. 14.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredRole {
    /**
     * 필요한 Role 값 배열 (문자열)
     * 각 서버별 Role enum의 getValue()로 얻은 값을 지정합니다.
     * 예: AdminRole.SUPER_ADMIN.getValue() -> "ROLE_SUPER_ADMIN"
     */
    String[] value();
}

