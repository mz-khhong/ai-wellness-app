package com.aiwellness.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * com.aiwellness.common.config
 * <p>
 * SwaggerConfig
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
 * </pre>
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    
    @Value("${spring.application.name:wellness-app}")
    private String applicationName;
    
    private final SwaggerPathConfig swaggerPathConfig;
    private final SwaggerInfoConfig swaggerInfoConfig;
    
    @Bean
    public OpenAPI openAPI() {
        String securitySchemeName = "bearerAuth";
        
        // 설정 파일에서 Swagger 정보 읽기
        Info info = new Info()
                .title(swaggerInfoConfig.getTitle())
                .description(swaggerInfoConfig.getDescription())
                .version(swaggerInfoConfig.getVersion());
        
        // Contact 정보 설정
        if (swaggerInfoConfig.getContact() != null) {
            SwaggerInfoConfig.Contact contact = swaggerInfoConfig.getContact();
            if (contact.getName() != null || contact.getEmail() != null) {
                info.contact(new Contact()
                        .name(contact.getName())
                        .email(contact.getEmail()));
            }
        }
        
        // License 정보 설정
        if (swaggerInfoConfig.getLicense() != null) {
            SwaggerInfoConfig.License license = swaggerInfoConfig.getLicense();
            if (license.getName() != null || license.getUrl() != null) {
                info.license(new License()
                        .name(license.getName())
                        .url(license.getUrl()));
            }
        }
        
        // Server 정보 설정
        List<Server> servers = swaggerInfoConfig.getServers().stream()
                .map(server -> new Server()
                        .url(server.getUrl())
                        .description(server.getDescription()))
                .collect(Collectors.toList());
        
        return new OpenAPI()
                .info(info)
                .servers(servers)
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT 토큰을 입력하세요. 형식: Bearer {token}")));
    }
    
    @Bean
    @ConditionalOnProperty(name = "spring.application.name", havingValue = "admin-server")
    public GroupedOpenApi adminApis() {
        String[] apiPaths = swaggerPathConfig.getApiPathArray();
        // 디버깅: 로드된 경로 확인
        log.info("[SwaggerConfig] Admin API paths configured: {}", java.util.Arrays.toString(apiPaths));
        return GroupedOpenApi.builder()
                .group("admin-server")
                .displayName("Admin APIs")
                .pathsToMatch(apiPaths)
                .packagesToScan(
                    "com.aiwellness.common.controller",  // common 모듈의 공통 API (Auth, AppCode 등)
                    "com.aiwellness.admin.adapter.web"  // admin-server의 web 패키지 스캔
                )
                .build();
    }
    
    @Bean
    @ConditionalOnProperty(name = "spring.application.name", havingValue = "manager-server")
    public GroupedOpenApi managerApis() {
        return GroupedOpenApi.builder()
                .group("manager-server")
                .displayName("Manager APIs")
                .pathsToMatch(swaggerPathConfig.getApiPathArray())
                .packagesToScan(
                    "com.aiwellness.common.controller",  // common 모듈의 공통 API (Auth, AppCode 등)
                    "com.aiwellness.manager.adapter.web"  // manager-server의 web 패키지 스캔
                )
                .build();
    }
    
    @Bean
    @ConditionalOnProperty(name = "spring.application.name", havingValue = "customer-server")
    public GroupedOpenApi customerApis() {
        return GroupedOpenApi.builder()
                .group("customer-server")
                .displayName("Customer APIs")
                .pathsToMatch(swaggerPathConfig.getApiPathArray())
                .packagesToScan(
                    "com.aiwellness.common.controller",  // common 모듈의 공통 API (Auth, AppCode 등)
                    "com.aiwellness.customer.adapter.web"  // customer-server의 web 패키지 스캔
                )
                .build();
    }
}

