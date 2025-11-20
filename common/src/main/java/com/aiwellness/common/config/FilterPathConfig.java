package com.aiwellness.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * com.aiwellness.common.config
 * <p>
 * FilterPathConfig
 * <p>
 * 필터에서 제외할 경로를 설정 파일로 관리하는 Configuration
 * <p>
 * application-common.yml의 filter.excluded-paths에서 경로 목록을 관리합니다.
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
@Configuration
@ConfigurationProperties(prefix = "filter")
public class FilterPathConfig {
    
    /**
     * 필터에서 제외할 경로 목록
     * <p>
     * 이 경로들로 시작하는 요청은 TraceIdFilter, HttpLoggingFilter에서 제외됩니다.
     */
    private List<String> excludedPaths = new ArrayList<>();
    
    /**
     * 경로가 제외 대상인지 확인
     *
     * @param requestPath 요청 경로
     * @return 제외 대상이면 true
     */
    public boolean isExcluded(String requestPath) {
        if (requestPath == null || excludedPaths == null || excludedPaths.isEmpty()) {
            return false;
        }
        
        return excludedPaths.stream()
                .anyMatch(requestPath::startsWith);
    }
}

