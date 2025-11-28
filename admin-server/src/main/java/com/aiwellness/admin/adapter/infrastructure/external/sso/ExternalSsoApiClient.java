package com.aiwellness.admin.adapter.infrastructure.external.sso;

import com.aiwellness.admin.adapter.infrastructure.external.sso.dto.ExternalApiRequest;
import com.aiwellness.admin.adapter.infrastructure.external.sso.dto.ExternalApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * com.aiwellness.admin.adapter.infrastructure.external.sso
 * <p>
 * ExternalSsoApiClient
 * <p>
 * 외부 SSO 연계 API 호출을 위한 Feign Client
 * <p>
 * SSO 토큰은 ExternalSsoApiFeignConfig에서 자동으로 주입됩니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 */
@FeignClient(
    name = "external-sso-api",
    url = "${external.sso.api.url:}",
    configuration = ExternalSsoApiFeignConfig.class
)
public interface ExternalSsoApiClient {
    
    /**
     * 외부 SSO 연계 API 호출
     * 
     * @param request 외부 API 요청
     * @return 외부 API 응답
     */
    @PostMapping("/api/v1/external/process")
    ExternalApiResponse processRequest(@RequestBody ExternalApiRequest request);
}

