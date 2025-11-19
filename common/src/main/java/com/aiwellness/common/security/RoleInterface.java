package com.aiwellness.common.security;

/**
 * com.aiwellness.common.security
 * <p>
 * RoleInterface
 * <p>
 * Role의 공통 인터페이스. 각 서버는 이 인터페이스를 구현하는 자체 Role enum을 가질 수 있습니다.
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
public interface RoleInterface {
    /**
     * Role의 문자열 값을 반환합니다.
     * 예: "ROLE_ADMIN", "ROLE_MANAGER" 등
     */
    String getValue();
    
    /**
     * Role의 이름을 반환합니다.
     */
    String name();
}

