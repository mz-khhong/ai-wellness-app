package com.aiwellness.common.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API 문서화를 위한 메타 어노테이션
 * 컨트롤러나 메서드에 간편하게 적용할 수 있습니다.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Operation
@Tag(name = "API")
public @interface ApiDocumentation {
    String summary() default "";
    String description() default "";
    String[] tags() default {};
}

