package com.aiwellness.admin.domain.port.external;

import com.aiwellness.admin.adapter.infrastructure.external.sso.dto.ExternalApiRequest;
import com.aiwellness.admin.adapter.infrastructure.external.sso.dto.ExternalApiResponse;

/**
 * com.aiwellness.admin.domain.port.external
 * <p>
 * ExternalSsoApiPort
 * <p>
 * 외부 SSO 연계 API 호출을 위한 아웃바운드 포트 인터페이스
 * <p>
 * 헥사고날 아키텍처 원칙에 따라 외부 시스템과의 통신은 Port를 통해 이루어집니다.
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
public interface ExternalSsoApiPort {
    
    /**
     * 외부 SSO 연계 API 호출
     * 
     * @param request 외부 API 요청 DTO
     * @return 외부 API 응답 DTO
     */
    ExternalApiResponse callExternalApi(ExternalApiRequest request);
}

