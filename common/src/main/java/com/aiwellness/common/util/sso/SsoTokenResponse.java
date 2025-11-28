package com.aiwellness.common.util.sso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.aiwellness.common.util.sso
 * <p>
 * SsoTokenResponse
 * <p>
 * SSO 토큰 발급 응답 DTO
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SsoTokenResponse {
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private String scope;
    private String refreshToken; // 선택사항
}