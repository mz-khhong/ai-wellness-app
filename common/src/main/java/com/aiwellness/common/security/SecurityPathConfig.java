package com.aiwellness.common.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * com.aiwellness.common.security
 * <p>
 * SecurityPathConfig
 * <p>
 * 인증 없이 접근 가능한 경로를 설정 파일로 관리
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "security")
public class SecurityPathConfig {

    /**
     * 인증 없이 접근 가능한 경로 목록
     * Ant 패턴 또는 정규식 패턴 지원
     * 
     * 예시:
     * - "/api/v1/auth/**" (Ant 패턴)
     * - "/swagger-ui/**" (Ant 패턴)
     * - "^/api/v1/public/.*" (정규식 패턴)
     */
    private List<String> permitAllPaths = new ArrayList<>();

    /**
     * 정규식 패턴으로 인식할 경로 목록
     * 이 목록에 포함된 경로는 정규식으로 처리됩니다.
     */
    private List<String> regexPaths = new ArrayList<>();

    /**
     * 경로가 정규식 패턴인지 확인
     *
     * @param path 경로
     * @return 정규식 패턴 여부
     */
    public boolean isRegexPath(String path) {
        return regexPaths.contains(path) || path.startsWith("^") || path.contains(".*");
    }
}

