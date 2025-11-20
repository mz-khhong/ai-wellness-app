package com.aiwellness.admin.adapter.infrastructure;

import com.aiwellness.admin.exception.AdminErrorCode;
import com.aiwellness.common.domain.port.ErrorCodeProviderPort;
import com.aiwellness.common.controller.appCode.dto.response.AppCodeInfo;
import com.aiwellness.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * com.aiwellness.admin.adapter.infrastructure
 * <p>
 * ErrorCodeProviderAdapter
 * <p>
 * Admin 서버의 에러 코드를 제공하는 어댑터
 * <p>
 * Hexagonal Architecture 원칙에 따라 포트 인터페이스를 구현합니다.
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
@Component
@RequiredArgsConstructor
public class ErrorCodeProviderAdapter implements ErrorCodeProviderPort {
    
    private static final String SERVER_NAME = "admin";
    private static final String DOMAIN_NAME = "admin"; // Admin 도메인
    
    private final MessageUtil messageUtil;
    
    @Override
    public String getServerName() {
        return SERVER_NAME;
    }
    
    @Override
    public Map<String, List<AppCodeInfo>> getErrorCodesByDomain(Locale locale) {
        // Admin 서버의 에러 코드를 "admin" 비즈니스 영역으로 그룹화
        List<AppCodeInfo> adminCodes = Arrays.stream(AdminErrorCode.values())
                .map(errorCode -> {
                    // 코드 형식: HttpStatus.value() + 순서 (예: 40401, 40901, 40902)
                    String code = generateErrorCode(errorCode);
                    String messageKey = errorCode.getMessageKey();
                    String message = getTranslatedMessage(messageKey, locale);
                    log.debug("AdminErrorCode: code={}, messageKey={}, message={}, locale={}", code, messageKey, message, locale);
                    return new AppCodeInfo(code, message, "ERROR");
                })
                .collect(Collectors.toList());
        
        Map<String, List<AppCodeInfo>> result = new HashMap<>();
        result.put(DOMAIN_NAME, adminCodes);
        return result;
    }
    
    /**
     * 에러 코드 생성
     * 형식: HttpStatus.value() + 순서 (2자리)
     * 예: 40401 (NOT_FOUND 첫 번째), 40901 (CONFLICT 첫 번째), 40902 (CONFLICT 두 번째)
     */
    private String generateErrorCode(AdminErrorCode errorCode) {
        int httpStatus = errorCode.getStatus().value();
        int order = getOrderInSameStatus(errorCode);
        return String.format("%d%02d", httpStatus, order);
    }
    
    /**
     * 동일한 HttpStatus를 가진 에러 코드 중에서의 순서 계산
     */
    private int getOrderInSameStatus(AdminErrorCode errorCode) {
        int httpStatus = errorCode.getStatus().value();
        long order = Arrays.stream(AdminErrorCode.values())
                .filter(code -> code.getStatus().value() == httpStatus)
                .takeWhile(code -> code != errorCode)
                .count();
        return (int) order + 1;
    }
    
    /**
     * 메시지 키를 Locale에 맞게 번역
     */
    private String getTranslatedMessage(String messageKey, Locale locale) {
        try {
            String message = messageUtil.getMessage(messageKey, locale);
            // 번역이 안 되었는지 확인 (메시지 키와 동일하면 번역 실패)
            if (message.equals(messageKey)) {
                log.warn("Admin 메시지 번역 실패: messageKey={}, locale={}, 번역된 메시지가 키와 동일함", messageKey, locale);
            }
            return message;
        } catch (Exception e) {
            log.error("Admin 메시지 번역 실패: messageKey={}, locale={}, error={}", messageKey, locale, e.getMessage(), e);
            return messageKey;
        }
    }
}

