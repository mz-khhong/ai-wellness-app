package com.aiwellness.admin.adapter.infrastructure.external.sso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * com.aiwellness.admin.adapter.infrastructure.external.sso.dto
 * <p>
 * ExternalApiRequest
 * <p>
 * 외부 SSO 연계 API 요청 DTO
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalApiRequest {
    
    /**
     * 요청 데이터
     */
    private String data;
    
    /**
     * 요청 타입
     */
    private String type;
}

