package com.aiwellness.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

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
@Configuration
public class SwaggerConfig {
    
    @Value("${spring.application.name:wellness-app}")
    private String applicationName;
    
    @Value("${server.port:8080}")
    private int serverPort;
    
    @Bean
    public OpenAPI openAPI() {
        String securitySchemeName = "bearerAuth";
        
        String title = getServerTitle();
        String description = getServerDescription();
        List<Server> servers = getServerList();
        
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Wellness App Team")
                                .email("dev@wellness.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
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
        return GroupedOpenApi.builder()
                .group("default")
                .displayName("Admin APIs")
                .pathsToMatch("/api/v1/auth/**", "/api/v1/admins/**", "/api/v1/response-codes/**")
                .build();
    }
    
    @Bean
    @ConditionalOnProperty(name = "spring.application.name", havingValue = "manager-server")
    public GroupedOpenApi managerApis() {
        return GroupedOpenApi.builder()
                .group("default")
                .displayName("Manager APIs")
                .pathsToMatch("/api/v1/auth/**", "/api/v1/managers/**", "/api/v1/response-codes/**")
                .build();
    }
    
    @Bean
    @ConditionalOnProperty(name = "spring.application.name", havingValue = "customer-server")
    public GroupedOpenApi customerApis() {
        return GroupedOpenApi.builder()
                .group("default")
                .displayName("Customer APIs")
                .pathsToMatch("/api/v1/auth/**", "/api/v1/customers/**", "/api/v1/response-codes/**")
                .build();
    }
    
    private String getServerTitle() {
        return switch (applicationName) {
            case "admin-server" -> "Admin Server API Documentation";
            case "manager-server" -> "Manager Server API Documentation";
            case "customer-server" -> "Customer Server API Documentation";
            default -> "Wellness App API Documentation";
        };
    }
    
    private String getServerDescription() {
        return switch (applicationName) {
            case "admin-server" -> "Admin Server의 API 문서입니다.";
            case "manager-server" -> "Manager Server의 API 문서입니다.";
            case "customer-server" -> "Customer Server의 API 문서입니다.";
            default -> "Wellness App의 통합 API 문서입니다.";
        };
    }
    
    private List<Server> getServerList() {
        List<Server> servers = new ArrayList<>();
        
        switch (applicationName) {
            case "admin-server":
                servers.add(new Server().url("http://localhost:8080").description("Admin Server"));
                break;
            case "manager-server":
                servers.add(new Server().url("http://localhost:8081").description("Manager Server"));
                break;
            case "customer-server":
                servers.add(new Server().url("http://localhost:8082").description("Customer Server"));
                break;
            default:
                servers.add(new Server().url("http://localhost:8080").description("Admin Server"));
                servers.add(new Server().url("http://localhost:8081").description("Manager Server"));
                servers.add(new Server().url("http://localhost:8082").description("Customer Server"));
        }
        return servers;
    }
}

