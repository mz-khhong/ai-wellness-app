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
 * SwaggerInfoConfig
 * <p>
 * Swagger 정보(제목, 설명, 연락처 등)를 설정 파일로 관리하는 Configuration
 * <p>
 * application-common.yml의 swagger.info에서 정보를 관리합니다.
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
@ConfigurationProperties(prefix = "swagger.info")
public class SwaggerInfoConfig {
    
    /**
     * API 문서 제목
     */
    private String title;
    
    /**
     * API 문서 설명
     */
    private String description;
    
    /**
     * API 문서 버전
     */
    private String version = "1.0.0";
    
    /**
     * 연락처 정보
     */
    private Contact contact = new Contact();
    
    /**
     * 라이선스 정보
     */
    private License license = new License();
    
    /**
     * 서버 정보 목록
     */
    private List<Server> servers = new ArrayList<>();
    
    @Getter
    @Setter
    public static class Contact {
        private String name;
        private String email;
    }
    
    @Getter
    @Setter
    public static class License {
        private String name;
        private String url;
    }
    
    @Getter
    @Setter
    public static class Server {
        private String url;
        private String description;
    }
}

