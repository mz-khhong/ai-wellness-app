package com.aiwellness.common.service;

import com.aiwellness.common.code.ApiResponseWellnessCode;
import com.aiwellness.common.domain.port.ErrorCodeProviderPort;
import com.aiwellness.common.dto.ResponseCodeData;
import com.aiwellness.common.dto.ResponseCodeInfo;
import com.aiwellness.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * com.aiwellness.common.service
 * <p>
 * ResponseCodeService
 * <p>
 * 응답 코드 정보를 조회하는 서비스
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
@Slf4j
@Service
@RequiredArgsConstructor
public class ResponseCodeService {
    
    private final MessageUtil messageUtil;
    private final List<ErrorCodeProviderPort> errorCodeProviders; // 각 서버에서 구현한 포트들 (Spring이 자동 주입)
    
    /**
     * 모든 응답 코드 목록 조회 (공통 + 서버별)
     * <p>
     * 공통 코드와 서버별 코드를 비즈니스 영역별로 분리하여 제공합니다.
     * 
     * @param locale Locale (Accept-Language 헤더 기반)
     * @return 구조화된 응답 코드 데이터
     */
    public ResponseCodeData getAllResponseCodes(Locale locale) {
        if (locale == null) {
            locale = Locale.KOREAN; // 기본값: 한국어
        }
        
        // 공통 응답 코드 조회
        List<ResponseCodeInfo> commonCodes = getCommonResponseCodes(locale);
        
        // 서버별 에러 코드 조회 (비즈니스 영역별로 분리)
        Map<String, Map<String, List<ResponseCodeInfo>>> serverCodes = getServerSpecificErrorCodes(locale);
        
        return ResponseCodeData.builder()
                .common(commonCodes)
                .servers(serverCodes)
                .build();
    }
    
    /**
     * 공통 응답 코드 목록 조회
     */
    private List<ResponseCodeInfo> getCommonResponseCodes(Locale locale) {
        return Arrays.stream(ApiResponseWellnessCode.values())
                .map(code -> {
                    String messageKey = code.getMessageKey();
                    String translatedMessage = getTranslatedMessage(messageKey, locale);
                    return new ResponseCodeInfo(
                            code.getCode(),
                            translatedMessage,
                            getCodeType(code)
                    );
                })
                .collect(Collectors.toList());
    }
    
    /**
     * 서버별 에러 코드 목록 조회 (비즈니스 영역별로 분리)
     * 포트 인터페이스를 통해 각 서버에서 구현한 에러 코드를 조회합니다.
     * <p>
     * Hexagonal Architecture 원칙:
     * - Common 모듈은 포트 인터페이스만 알고 있음
     * - 각 서버는 자신의 Adapter에서 포트를 구현
     * - 의존성 방향: Common ← 각 서버 (포트 인터페이스)
     * 
     * @param locale Locale
     * @return Map<서버명, Map<비즈니스영역, List<ResponseCodeInfo>>>
     */
    private Map<String, Map<String, List<ResponseCodeInfo>>> getServerSpecificErrorCodes(Locale locale) {
        if (errorCodeProviders == null || errorCodeProviders.isEmpty()) {
            return Collections.emptyMap();
        }
        
        Map<String, Map<String, List<ResponseCodeInfo>>> result = new HashMap<>();
        
        for (ErrorCodeProviderPort provider : errorCodeProviders) {
            try {
                String serverName = provider.getServerName();
                Map<String, List<ResponseCodeInfo>> domainCodes = provider.getErrorCodesByDomain(locale);
                result.put(serverName, domainCodes);
            } catch (Exception e) {
                log.warn("서버별 에러 코드 조회 실패: {}", e.getMessage());
            }
        }
        
        return result;
    }
    
    /**
     * 메시지 키를 Locale에 맞게 번역
     */
    private String getTranslatedMessage(String messageKey, Locale locale) {
        try {
            return messageUtil.getMessage(messageKey, locale);
        } catch (Exception e) {
            log.warn("메시지 번역 실패: messageKey={}, locale={}, error={}", messageKey, locale, e.getMessage());
            return messageKey; // 번역 실패 시 메시지 키 반환
        }
    }
    
    /**
     * 응답 코드 타입 판별 (SUCCESS, ERROR)
     */
    private String getCodeType(ApiResponseWellnessCode code) {
        return code == ApiResponseWellnessCode.SUCCESS ? "SUCCESS" : "ERROR";
    }
}

