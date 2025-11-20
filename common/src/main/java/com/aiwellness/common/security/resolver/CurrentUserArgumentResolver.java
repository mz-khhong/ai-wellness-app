package com.aiwellness.common.security.resolver;

import com.aiwellness.common.security.CurrentUser;
import com.aiwellness.common.security.annotation.AuthenticatedUser;
import com.aiwellness.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * com.aiwellness.common.security.resolver
 * <p>
 * CurrentUserArgumentResolver
 * <p>
 * @AuthenticatedUser 어노테이션이 붙은 파라미터를 CurrentUser로 자동 주입하는 ArgumentResolver
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
@Component
@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
    
    private final MessageUtil messageUtil;
    
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // @AuthenticatedUser 어노테이션이 있고, CurrentUser 타입인 경우
        return parameter.hasParameterAnnotation(AuthenticatedUser.class) &&
               CurrentUser.class.isAssignableFrom(parameter.getParameterType());
    }
    
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !(authentication.getPrincipal() instanceof CurrentUser)) {
            // @AuthenticatedUser 어노테이션의 required 속성 확인
            AuthenticatedUser annotation = parameter.getParameterAnnotation(AuthenticatedUser.class);
            if (annotation != null && !annotation.required()) {
                return null;
            }
            // required = true이거나 기본값이면 예외 발생
            String message = messageUtil.getMessage("auth.userInfoNotFound");
            throw new IllegalStateException(message);
        }
        
        return (CurrentUser) authentication.getPrincipal();
    }
}

