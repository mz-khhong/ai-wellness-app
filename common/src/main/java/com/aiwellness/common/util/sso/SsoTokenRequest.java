package com.aiwellness.common.util.sso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.aiwellness.common.util.sso
 * <p>
 * SsoTokenRequest
 * <p>
 * SSO 토큰 발급 요청 DTO
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SsoTokenRequest {
    private String clientId;
    private String clientSecret;
    private String grantType;
    private String scope;
}

