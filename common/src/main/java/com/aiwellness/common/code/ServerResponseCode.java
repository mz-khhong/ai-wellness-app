package com.aiwellness.common.code;

import lombok.Getter;

/**
 * com.aiwellness.common.code
 * <p>
 * ServerResponseCode
 * <p>
 * 각 서버별 공통 Response 코드를 제공하는 인터페이스
 * 각 서버에서 이 인터페이스를 구현하여 서버별 코드를 정의합니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 14.    메가존 시스템            최초 생성
 * </pre>
 */
public interface ServerResponseCode {
    
    /**
     * 응답 코드 반환
     */
    String getCode();
    
    /**
     * 메시지 키 반환 (다국어 지원)
     */
    String getMessageKey();

}

