package com.aiwellness.admin.application.service.external;

import com.aiwellness.admin.domain.port.external.ExternalSsoApiPort;
import com.aiwellness.admin.adapter.infrastructure.external.sso.dto.ExternalApiRequest;
import com.aiwellness.admin.adapter.infrastructure.external.sso.dto.ExternalApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * com.aiwellness.admin.application.service.external
 * <p>
 * ExternalApiService
 * <p>
 * 외부 SSO 연계 API 호출을 위한 Application Service
 * <p>
 * 헥사고날 아키텍처 원칙에 따라 Domain Port를 통해 외부 시스템과 통신합니다.
 * <p>
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
@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED)
@ConditionalOnExpression("!'${external.sso.api.url:}'.isEmpty()")
public class ExternalApiService {
    
    private final ExternalSsoApiPort externalSsoApiPort;
    
    /**
     * 외부 SSO 연계 API 호출
     * 
     * @param data 요청 데이터
     * @param type 요청 타입
     * @return 외부 API 응답
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ExternalApiResponse callExternalApi(String data, String type) {
        log.info("[Application/Service] ExternalApiService.callExternalApi() - Use Case 시작: type={}", type);
        
        // 요청 DTO 생성
        ExternalApiRequest request = ExternalApiRequest.builder()
                .data(data)
                .type(type)
                .build();
        
        // Port를 통해 외부 API 호출
        ExternalApiResponse response = externalSsoApiPort.callExternalApi(request);
        
        log.info("[Application/Service] ExternalApiService.callExternalApi() - Use Case 완료: code={}", 
                response.getCode());
        
        return response;
    }
}

