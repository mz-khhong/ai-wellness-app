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
}

