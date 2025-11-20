package com.aiwellness.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * com.aiwellness.common.config
 * <p>
 * SecurityPathConfig
 * <p>
 * Spring Security에서 인증 없이 접근 가능한 경로를 설정 파일로 관리하는 Configuration
 * <p>
 * application-common.yml의 security.public-paths에서 경로 목록을 관리합니다.
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
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityPathConfig {
    
    /**
     * 인증 없이 접근 가능한 경로 목록 (Public endpoints)
     * <p>
     * 이 경로들은 Spring Security의 인증 체크를 통과합니다.
     * 필터는 실행되지만 JWT 인증이 필요하지 않습니다.
     */
    private List<String> publicPaths = new ArrayList<>();

    /**
     * Public 경로 목록을 배열로 반환 (SecurityConfig에서 사용)
     * <p>
     * 예시:
     * <ul>
     *   <li>publicPaths = ["/api/v1/auth/**", "/actuator/**", "/swagger-ui/**"]</li>
     *   <li>반환값 = ["/api/v1/auth/**", "/actuator/**", "/swagger-ui/**"] (모든 경로 포함)</li>
     * </ul>
     * <p>
     * requestMatchers(String... patterns)는 가변 인자이므로 배열의 모든 요소가 전달됩니다.
     *
     * @return Public 경로 배열 (모든 경로 포함)
     */
    public String[] getPublicPathArray() {
        if (publicPaths == null || publicPaths.isEmpty()) {
            return new String[0];
        }
        // List의 모든 요소를 배열로 변환 (하나가 아니라 모든 경로가 포함됨)
        return publicPaths.toArray(new String[0]);
    }
}

