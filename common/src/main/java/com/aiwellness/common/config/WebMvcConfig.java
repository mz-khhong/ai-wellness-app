package com.aiwellness.common.config;

import com.aiwellness.common.security.interceptor.RoleCheckInterceptor;
import com.aiwellness.common.security.resolver.CurrentUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * com.aiwellness.common.config
 * <p>
 * WebMvcConfig
 * <p>
 * Web MVC 설정 (Interceptor, ArgumentResolver 등)
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
 *  2025. 11. 20.    메가존 시스템            CurrentUserArgumentResolver 추가
 * </pre>
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    
    private final RoleCheckInterceptor roleCheckInterceptor;
    private final CurrentUserArgumentResolver currentUserArgumentResolver;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Role 체크 인터셉터
        registry.addInterceptor(roleCheckInterceptor)
                .addPathPatterns("/api/**");
    }
    
    /**
     * ArgumentResolver 등록
     * <p>
     * @AuthenticatedUser 어노테이션을 사용할 수 있도록 CurrentUserArgumentResolver를 등록합니다.
     * 모든 서버(admin, manager, customer)에서 공통으로 사용됩니다.
     *
     * @param resolvers ArgumentResolver 목록
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserArgumentResolver);
    }
}

