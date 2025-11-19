package com.aiwellness.common.domain.port;

import com.aiwellness.common.dto.ResponseCodeInfo;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * com.aiwellness.common.domain.port
 * <p>
 * ErrorCodeProviderPort
 * <p>
 * 서버별 에러 코드를 제공하는 포트 인터페이스
 * <p>
 * 각 서버에서 이 포트를 구현하여 자신의 ErrorCode를 비즈니스 영역별로 제공합니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 19.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 19.    메가존 시스템            최초 생성
 * </pre>
 */
public interface ErrorCodeProviderPort {
    
    /**
     * 서버명 반환
     * 예: "admin", "manager", "customer"
     * 
     * @return 서버명
     */
    String getServerName();
    
    /**
     * 서버별 에러 코드를 비즈니스 영역별로 조회
     * <p>
     * 반환 형식: Map<비즈니스영역, List<ResponseCodeInfo>>
     * 예: {"admin": [코드1, 코드2, ...], "profile": [코드1, ...]}
     * 
     * @param locale Locale (다국어 메시지용)
     * @return 비즈니스 영역별 에러 코드 맵
     */
    Map<String, List<ResponseCodeInfo>> getErrorCodesByDomain(Locale locale);
}

