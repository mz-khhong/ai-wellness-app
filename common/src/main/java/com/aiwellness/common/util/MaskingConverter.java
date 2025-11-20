package com.aiwellness.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * com.aiwellness.common.util
 * <p>
 * MaskingConverter
 * <p>
 * ISMS-P 대응을 위한 마스킹 변환 유틸리티
 * <p>
 * 이메일 및 전체 문자열 마스킹, 날짜/시간 마스킹을 제공합니다.
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
public class MaskingConverter {

    /**
     * 이메일 마스킹
     *
     * @param email 이메일
     * @return 마스킹된 이메일
     */
    public static String maskEmail(String email) {
        if (email == null) {
            return "";
        }
        return email.replaceAll(RegexUtil.EMAIL_MASKING_ADVANCED, "*");
    }

    /**
     * 이메일 삭제용 마스킹 (앞 2자리만 노출)
     *
     * @param email 이메일
     * @return 마스킹된 이메일
     */
    public static String maskEmailForDestroy(String email) {
        if (email == null) {
            return "";
        }
        return email.replaceAll(RegexUtil.EMAIL_MASKING_DESTROY, "*");
    }

    /**
     * 입력값 전체 마스킹
     * <p>
     * 10자리 이상이면 "*********"로 반환, 그 외는 전체를 '*'로 마스킹
     *
     * @param targetMasking 마스킹할 대상
     * @return 마스킹된 문자열
     */
    public static String maskString(String targetMasking) {
        if (targetMasking == null) {
            return "";
        }
        
        if (targetMasking.length() > 10) {
            return "*********";
        } else {
            char[] maskChars = new char[targetMasking.length()];
            Arrays.fill(maskChars, '*');
            return new String(maskChars);
        }
    }

    /**
     * LocalDate를 마스킹된 문자열로 변환
     *
     * @param localDate 날짜
     * @return 마스킹된 날짜
     */
    public static LocalDate maskToLocalDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = formatter.format(localDate);
        String maskedLocalDate = MaskingConverter.maskString(format);
        
        return LocalDate.parse(maskedLocalDate, formatter);
    }

    /**
     * LocalDateTime을 마스킹된 문자열로 변환
     *
     * @param localDateTime 날짜/시간
     * @return 마스킹된 날짜/시간
     */
    public static LocalDateTime maskToLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        
        DateTimeFormatter maskFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String format = maskFormatter.format(localDateTime);
        String maskedString = MaskingConverter.maskString(format);
        
        return LocalDateTime.parse(maskedString, maskFormatter);
    }
}

