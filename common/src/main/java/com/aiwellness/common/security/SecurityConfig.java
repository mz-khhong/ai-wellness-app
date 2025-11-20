package com.aiwellness.common.security;

import com.aiwellness.common.config.SecurityPathConfig;
import com.aiwellness.common.config.SwaggerSecurityConfig;
import com.aiwellness.common.logging.HttpLoggingFilter;
import com.aiwellness.common.logging.TraceIdFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * com.aiwellness.common.security
 * <p>
 * SecurityConfig
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
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPointHandler authenticationEntryPointHandler;
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final HttpLoggingFilter httpLoggingFilter;
    private final TraceIdFilter traceIdFilter;
    private final SecurityPathConfig securityPathConfig;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // OPTIONS 요청 허용 (CORS preflight)
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        // Public endpoints (설정 파일에서 관리)
                        .requestMatchers(securityPathConfig.getPublicPathArray()).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPointHandler)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                // 필터 실행 순서 (위에서 아래로):
                // 1. TraceIdFilter: MDC에 traceId/spanId 설정 (가장 먼저 실행되어야 함)
                // 2. HttpLoggingFilter: 요청/응답 Body 래핑
                // 3. JwtAuthenticationFilter: JWT 토큰 인증
                // 4. UsernamePasswordAuthenticationFilter: Spring Security 기본 필터
                .addFilterBefore(traceIdFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(httpLoggingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    /**
     * CORS 설정
     * <p>
     * 모든 Origin, Method, Header를 허용합니다.
     * 프로덕션 환경에서는 필요한 Origin만 허용하도록 수정해야 합니다.
     *
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedHeaders(
                List.of("access-control-allow-origin", "content-type", "authorization", "X-Trace-Id", "X-Request-Id", "X-Span-Id"));
        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    /**
     * HTTP Firewall 설정
     * <p>
     * URL 인코딩된 슬래시(/)를 허용합니다.
     *
     * @return StrictHttpFirewall
     */
    @Bean
    public StrictHttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return SwaggerSecurityConfig.webSecurityCustomizer();
    }
    
    /**
     * 기본 UserDetailsService를 비활성화하여 Spring Boot의 자동 생성 비밀번호 메시지를 제거
     * <p>
     * JWT 인증을 사용하므로 기본 UserDetailsService는 사용되지 않습니다.
     * 이 빈이 존재하면 Spring Boot가 자동으로 생성하는 기본 사용자와 비밀번호를 생성하지 않습니다.
     *
     * @return UserDetailsService (항상 UsernameNotFoundException을 발생시킴)
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new UsernameNotFoundException("UserDetailsService는 JWT 인증을 사용하므로 사용되지 않습니다.");
        };
    }
}

