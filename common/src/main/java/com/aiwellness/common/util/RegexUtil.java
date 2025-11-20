package com.aiwellness.common.util;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * com.aiwellness.common.util
 * <p>
 * RegexUtil
 * <p>
 * 정규식 패턴을 관리하는 유틸리티 클래스
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 17.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 17.    메가존 시스템            최초 생성
 * </pre>
 */
@Getter
@NoArgsConstructor
public class RegexUtil {
    
    // 이메일 형식
    public static final String EMAIL = "^[\\w.%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    
    // UUID 형식 (32자리 소문자/숫자)
    public static final String UUID = "^[0-9a-z]{32}$";
    
    // 전화번호 형식 (010-1234-5678 또는 01012345678)
    public static final String PHONE_NUMBER = "^01(\\d{1})(\\d{3,4})(\\d{4})$";
    
    // 생년월일 형식 (YYYYMMDD)
    public static final String BIRTHDAY = "^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$";
    
    // 날짜 형식 (YYYY-MM-DD)
    public static final String DATE_FORMAT = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
    
    // 날짜시간 형식 (YYYY-MM-DD HH:mm:ss)
    public static final String DATE_TIME_FORMAT = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) (\\d{2}):(\\d{2}):(\\d{2})$";
    
    // Y/N 값
    public static final String YN = "^[YN]$";
    
    // 한글 이름 (2-50자)
    public static final String KOREAN_NAME = "^[가-힣]{2,50}$";
    
    // 영문 이름 (2-50자, 공백 허용)
    public static final String ENGLISH_NAME = "^[a-zA-Z\\s]{2,50}$";
    
    // 비밀번호 (8자 이상, 영문/숫자/특수문자 조합)
    public static final String PASSWORD = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$";
    
    // ========== 마스킹 관련 정규식 패턴 ==========
    
    // 아이디 마스킹 (2글자 뒤로 마스킹)
    public static final String ID_MASKING = "(?<=.{2}).";
    
    // 한글 이름 패턴
    public static final String KOREAN_NAME_ONLY = "(^[가-힣]+)$";
    
    // 전화번호 패턴 (11자리)
    public static final String PHONE_11_DIGITS = "(\\d{2,3})-?(\\d{3,4})-?(\\d{4})$";
    
    // 전화번호 패턴 (10자리 - 서울)
    public static final String PHONE_10_DIGITS_SEOUL = "(\\d{2})-?(\\d{3,4})-?(\\d{4})$";
    
    // 전화번호 패턴 (10자리 - 기타)
    public static final String PHONE_10_DIGITS_OTHER = "(\\d{3})-?(\\d{3,4})-?(\\d{4})$";
    
    // 전화번호 패턴 (9자리)
    public static final String PHONE_9_DIGITS = "(\\d{2,3})-?(\\d{3,4})-?(\\d{4})$";
    
    // 전화번호 패턴 (8자리)
    public static final String PHONE_8_DIGITS = "(\\d{4})-?(\\d{4})$";
    
    // 이메일 패턴 (마스킹용)
    public static final String EMAIL_MASKING = "\\b(\\S+)+@(\\S+.\\S+)";
    
    // 이메일 마스킹 (앞 2자리 이후)
    public static final String EMAIL_MASKING_ADVANCED = "(?<=.{2})[^@\\n](?=[^@\\n]*?@)|(?:(?<=@.)|(?!^)\\G(?=[^@\\n]*$)).(?=.*\\.)";
    
    // 이메일 삭제용 마스킹
    public static final String EMAIL_MASKING_DESTROY = "(?<=.{2})[^@\\n](?=[^@\\n]*?@)";
    
    // 계좌번호 패턴 (숫자만)
    public static final String ACCOUNT_NUMBER = "(^[0-9]+)$";
    
    // 카드번호 패턴 (16자리 또는 15자리)
    public static final String CREDIT_CARD = "(\\d{4})-?(\\d{4})-?(\\d{4})-?(\\d{3,4})$";
    
    // 카드번호 전체 마스킹 패턴
    public static final String CREDIT_CARD_FULL = "(\\d{4})(\\d{4})\\d{4}(\\d{3,4})";
    
    // 주소 패턴 (구주소)
    public static final String ADDRESS_OLD = "(([가-힣]+(\\d{1,5}|\\d{1,5}(,|.)\\d{1,5}|)+(읍|면|동|가|리))(구|)((\\d{1,5}(~|-)\\d{1,5}|\\d{1,5})(가|리|)|))([ ](산(\\d{1,5}(~|-)\\d{1,5}|\\d{1,5}))|)|";
    
    // 주소 패턴 (도로명 주소)
    public static final String ADDRESS_NEW = "(([가-힣]|(\\d{1,5}(~|-)\\d{1,5})|\\d{1,5})+(로|길))";
    
    // 주소 숫자 마스킹
    public static final String ADDRESS_NUMBER_MASKING = "[0-9]";
    
    // 사업자등록번호 마스킹 (마지막 4자리)
    public static final String BUSINESS_NO_MASKING = "(?<=.{9}).";
    
    // 휴대폰 번호 패턴
    public static final String MOBILE_PHONE = "^(?<f>01[016789])(?<m>\\d*)(?<l>\\d{4})$";
    
    // JSON 필드 패턴 (마스킹용)
    public static final String JSON_FIELD_PATTERN = "\"%s\"\\s*:\\s*\"([^\"]+)\"";
}

