package com.aiwellness.admin.adapter.infrastructure.external.sso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * com.aiwellness.admin.adapter.infrastructure.external.sso.dto
 * <p>
 * ExternalApiResponse
 * <p>
 * 외부 SSO 연계 API 응답 DTO
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalApiResponse {
    
    /**
     * 응답 코드
     */
    private String code;
    
    /**
     * 응답 메시지
     */
    private String message;
    
    /**
     * 응답 데이터
     */
    private String data;
}

