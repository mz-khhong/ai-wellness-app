package com.aiwellness.common.code;

/**
 * com.aiwellness.common.code
 * <p>
 * MaskingType
 * <p>
 * ISMS-P 대응을 위한 마스킹 타입 정의
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
public enum MaskingType {
    /**
     * 이름 마스킹 (가운데 글자 마스킹)
     */
    NAME,
    
    /**
     * 이메일 마스킹 (앞 2자리 이후 '@' 전까지 마스킹)
     */
    EMAIL,
    
    /**
     * 전화번호 마스킹 (가운데 숫자 마스킹)
     */
    PHONE_NUMBER,
    
    /**
     * 카드번호 마스킹 (가운데 8자리 마스킹)
     */
    CREDIT_CARD,
    
    /**
     * 카드번호 전체 마스킹 (4자리씩 마스킹)
     */
    CREDIT_CARD_FULL,
    
    /**
     * 계좌번호 마스킹 (뒤 5자리 마스킹)
     */
    ACCOUNT,
    
    /**
     * 주소 마스킹 (숫자만 마스킹)
     */
    ADDRESS,
    
    /**
     * 사업자등록번호 마스킹 (마지막 4자리 마스킹)
     */
    BUSINESS_NO,
    
    /**
     * 아이디 마스킹 (2글자 뒤로 마스킹)
     */
    ID
}

