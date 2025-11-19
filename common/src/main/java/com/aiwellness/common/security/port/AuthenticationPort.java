package com.aiwellness.common.security.port;

import com.aiwellness.common.security.AuthenticationResult;

/**
 * com.aiwellness.common.security.port
 * <p>
 * AuthenticationPort
 * <p>
 * 인증을 위한 Port 인터페이스
 * 각 서버(admin, manager, customer)에서 이 인터페이스를 구현하여 인증 로직을 제공합니다.
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
public interface AuthenticationPort {
    
    /**
     * 이메일과 비밀번호로 인증을 수행합니다.
     *
     * @param email 이메일
     * @param password 비밀번호 (평문)
     * @return 인증 결과 (사용자 ID, 이메일, 역할 목록)
     */
    AuthenticationResult authenticate(String email, String password);
}

