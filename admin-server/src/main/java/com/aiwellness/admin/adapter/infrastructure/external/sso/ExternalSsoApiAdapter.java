package com.aiwellness.admin.adapter.infrastructure.external.sso;

import com.aiwellness.admin.domain.port.external.ExternalSsoApiPort;
import com.aiwellness.admin.adapter.infrastructure.external.sso.dto.ExternalApiRequest;
import com.aiwellness.admin.adapter.infrastructure.external.sso.dto.ExternalApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Repository;

/**
 * com.aiwellness.admin.adapter.infrastructure.external.sso
 * <p>
 * ExternalSsoApiAdapter
 * <p>
 * 외부 SSO 연계 API 호출을 위한 Infrastructure Adapter
 * <p>
 * 헥사고날 아키텍처 원칙에 따라 ExternalSsoApiPort를 구현합니다.
 * <p>
 * SSO 토큰은 Feign Client 설정(ExternalSsoApiFeignConfig)에서 자동으로 주입되므로,
 * 이 Adapter에서는 직접 토큰을 관리할 필요가 없습니다.
 * <p>
 * external.sso.api.url이 설정되어 있고 비어있지 않을 때만 생성됩니다.
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
@Slf4j
@Repository
@RequiredArgsConstructor
@ConditionalOnExpression("!'${external.sso.api.url:}'.isEmpty()")
public class ExternalSsoApiAdapter implements ExternalSsoApiPort {
    
    private final ExternalSsoApiClient externalSsoApiClient;
    
    @Override
    public ExternalApiResponse callExternalApi(ExternalApiRequest request) {
        log.info("[Adapter/Infrastructure] ExternalSsoApiAdapter.callExternalApi() - 외부 API 호출 시작: type={}", 
                request.getType());
        
        try {
            // Feign Client를 통해 외부 API 호출
            // SSO 토큰은 ExternalSsoApiFeignConfig에서 자동으로 주입됨
            ExternalApiResponse response = externalSsoApiClient.processRequest(request);
            
            log.info("[Adapter/Infrastructure] ExternalSsoApiAdapter.callExternalApi() - 외부 API 호출 성공: code={}", 
                    response.getCode());
            
            return response;
            
        } catch (Exception e) {
            log.error("[Adapter/Infrastructure] ExternalSsoApiAdapter.callExternalApi() - 외부 API 호출 실패: type={}, error={}", 
                    request.getType(), e.getMessage(), e);
            throw new RuntimeException("외부 API 호출 실패: " + e.getMessage(), e);
        }
    }
}

