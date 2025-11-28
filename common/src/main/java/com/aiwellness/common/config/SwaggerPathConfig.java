package com.aiwellness.common.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * com.aiwellness.common.config
 * <p>
 * SwaggerPathConfig
 * <p>
 * Swagger에서 노출할 API 경로를 설정 파일로 관리하는 Configuration
 * <p>
 * application-common.yml의 swagger.api-paths에서 경로 목록을 관리합니다.
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
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerPathConfig {
    
    /**
     * Swagger에서 노출할 API 경로 목록
     * <p>
     * 각 서버별로 다른 경로를 설정할 수 있습니다.
     * 환경별로도 오버라이드 가능합니다.
     * <p>
     * 기본값이 비어있으면 자동으로 "/api/v1/**" 패턴을 사용합니다.
     */
    private List<String> apiPaths = new ArrayList<>();
    
    /**
     * 기본 API 경로 패턴 사용 여부
     * <p>
     * true인 경우, apiPaths가 비어있으면 자동으로 "/api/v1/**" 패턴을 추가합니다.
     * false인 경우, apiPaths에 명시적으로 설정된 경로만 사용합니다.
     */
    private boolean useDefaultPattern = true;
    
    /**
     * 설정이 로드된 후 확인 (디버깅용)
     */
    @PostConstruct
    public void init() {
        log.info("[SwaggerPathConfig] Loaded apiPaths: {}, useDefaultPattern: {}", apiPaths, useDefaultPattern);
        if (apiPaths != null && !apiPaths.isEmpty()) {
            log.info("[SwaggerPathConfig] API paths count: {}", apiPaths.size());
            apiPaths.forEach(path -> log.info("[SwaggerPathConfig] - {}", path));
        }
    }
    
    /**
     * Common 모듈의 공통 API 경로 목록
     * <p>
     * 모든 서버에서 공통으로 사용되는 API 경로입니다.
     * 이 경로들은 자동으로 모든 서버의 Swagger에 포함됩니다.
     */
    private static final String[] COMMON_API_PATHS = {
        "/api/v1/auth/**",      // AuthController
        "/api/v1/messages/**"   // MessageEventController
    };
    
    /**
     * API 경로 목록을 배열로 반환 (SwaggerConfig에서 사용)
     * <p>
     * Common 모듈의 공통 API 경로를 자동으로 포함하고,
     * 각 서버별로 설정된 API 경로를 추가합니다.
     * <p>
     * useDefaultPattern이 true이고 apiPaths가 비어있으면
     * 기본 패턴 "/api/v1/**"를 반환합니다.
     *
     * @return API 경로 배열 (Common API 경로 + 서버별 API 경로)
     */
    public String[] getApiPathArray() {
        List<String> allPaths = new ArrayList<>();
        
        // 1. Common 모듈의 공통 API 경로 추가 (모든 서버에 공통)
        allPaths.addAll(java.util.Arrays.asList(COMMON_API_PATHS));
        
        // 2. 서버별로 설정된 API 경로 추가
        if (apiPaths != null && !apiPaths.isEmpty()) {
            allPaths.addAll(apiPaths);
        } else if (useDefaultPattern) {
            // 기본 패턴 사용: 모든 /api/v1/** 경로 포함
            // 단, Common API 경로가 이미 포함되어 있으므로 중복 방지
            if (!allPaths.contains("/api/v1/**")) {
                allPaths.add("/api/v1/**");
            }
        }
        
        return allPaths.toArray(new String[0]);
    }
}

