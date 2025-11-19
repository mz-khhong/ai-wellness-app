package com.aiwellness.common.persistence.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * com.aiwellness.common.persistence.interceptor
 * <p>
 * MapperQueryId
 * <p>
 * MyBatis Mapper 인터페이스 또는 메서드에 QueryId의 prefix/suffix를 지정하는 어노테이션
 * <p>
 * 사용 예시:
 * <pre>
 * {@code
 * @MapperQueryId(prefix = "CUSTOM")
 * public interface AdminMapper {
 *     @MapperQueryId(prefix = "ADMIN", suffix = "SELECT")
 *     Admin findById(Long id);
 * }
 * }
 * </pre>
 * <p>
 * 위 예시의 경우 생성되는 QueryId는 다음과 같습니다:
 * - 클래스 레벨: `CUSTOM.AdminMapper.findById`
 * - 메서드 레벨: `ADMIN.AdminMapper.findById.SELECT`
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 19.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 19.    메가존 시스템            최초 생성
 * </pre>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperQueryId {
    
    /**
     * QueryId의 prefix
     * 예: "CUSTOM", "ADMIN", "USER" 등
     */
    String prefix();
    
    /**
     * QueryId의 suffix (선택적)
     * 예: "SELECT", "INSERT", "UPDATE" 등
     */
    String suffix() default "";
}
