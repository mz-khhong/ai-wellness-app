package com.aiwellness.common.util;

import com.aiwellness.common.code.MaskingType;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * com.aiwellness.common.util
 * <p>
 * MaskingUtil
 * <p>
 * ISMS-P 대응을 위한 개인정보 마스킹 유틸리티
 * <p>
 * 다양한 타입의 개인정보를 마스킹 처리합니다.
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
public class MaskingUtil {

    /**
     * Masking Type에 따라 마스킹 처리
     *
     * @param type 마스킹 타입
     * @param text 마스킹할 텍스트
     * @return 마스킹된 텍스트
     */
    public static String maskType(MaskingType type, String text) {
        if (type == null) {
            return text;
        }
        
        return switch (type) {
            case NAME -> nameMasking(text);
            case EMAIL -> emailMasking(text);
            case CREDIT_CARD -> cardMasking(text);
            case CREDIT_CARD_FULL -> cardMaskingFull(text);
            case PHONE_NUMBER -> phoneMasking(text);
            case ACCOUNT -> accountMasking(text);
            case ADDRESS -> addressMasking(text);
            case BUSINESS_NO -> businessNoMasking(text);
            case ID -> idMasking(text);
        };
    }

    /**
     * 아이디 마스킹 (2글자 뒤로 마스킹)
     *
     * @param id 아이디
     * @return 마스킹된 아이디
     */
    public static String idMasking(String id) {
        if (id == null) {
            return "";
        }
        return id.replaceAll(RegexUtil.ID_MASKING, "*");
    }

    /**
     * 이름 마스킹 (가운데 글자 마스킹)
     *
     * @param name 이름
     * @return 마스킹된 이름
     */
    public static String nameMasking(String name) {
        if (name == null) {
            return "";
        }
        
        Matcher matcher = Pattern.compile(RegexUtil.KOREAN_NAME_ONLY).matcher(name);
        
        if (matcher.find()) {
            int length = name.length();
            String middleMask = "";
            
            if (length > 2) {
                middleMask = name.substring(1, length - 1);
            } else {    // 이름이 외자
                middleMask = name.substring(1, length);
            }
            
            String dot = "";
            for (int i = 0; i < middleMask.length(); i++) {
                dot += "*";
            }
            
            if (length > 2) {
                return name.substring(0, 1)
                        + middleMask.replace(middleMask, dot)
                        + name.substring(length - 1, length);
            } else { // 이름이 외자 마스킹 리턴
                return name.substring(0, 1)
                        + middleMask.replace(middleMask, dot);
            }
        }
        
        return name;
    }

    /**
     * 전화번호 마스킹 (가운데 숫자 마스킹)
     *
     * @param phoneNumber 전화번호
     * @return 마스킹된 전화번호
     */
    public static String phoneMasking(String phoneNumber) {
        if (phoneNumber == null) {
            return "";
        }
        
        if (phoneNumber.length() == 11) {
            Matcher matcher = Pattern.compile(RegexUtil.PHONE_11_DIGITS).matcher(phoneNumber);
            
            if (matcher.find()) {
                String num1 = matcher.group(1);
                String num2 = "****";
                String num3 = matcher.group(3);
                return num1 + "-" + num2 + "-" + num3;
            }
        } else if (phoneNumber.length() == 10) {
            String localSeoulNo = phoneNumber.substring(0, 2);
            
            if (localSeoulNo.equals("02")) {
                Matcher matcher = Pattern.compile(RegexUtil.PHONE_10_DIGITS_SEOUL).matcher(phoneNumber);
                
                if (matcher.find()) {
                    String num1 = matcher.group(1);
                    String num2 = "****";
                    String num3 = matcher.group(3);
                    return num1 + "-" + num2 + "-" + num3;
                }
            } else {
                Matcher matcher = Pattern.compile(RegexUtil.PHONE_10_DIGITS_OTHER).matcher(phoneNumber);
                
                if (matcher.find()) {
                    String num1 = matcher.group(1);
                    String num2 = "***";
                    String num3 = matcher.group(3);
                    return num1 + "-" + num2 + "-" + num3;
                }
            }
        } else if (phoneNumber.length() == 9) {
            Matcher matcher = Pattern.compile(RegexUtil.PHONE_9_DIGITS).matcher(phoneNumber);
            
            if (matcher.find()) {
                String num1 = matcher.group(1);
                String num2 = "***";
                String num3 = matcher.group(3);
                return num1 + "-" + num2 + "-" + num3;
            }
        } else {
            Matcher matcher = Pattern.compile(RegexUtil.PHONE_8_DIGITS).matcher(phoneNumber);
            
            if (matcher.find()) {
                String num1 = matcher.group(1);
                String num2 = "****";
                return num1 + "-" + num2;
            }
        }
        
        return phoneNumber;
    }

    /**
     * 이메일 마스킹 (앞 2자리 이후 '@' 전까지 마스킹, 2자리일 경우 1자리만 노출)
     *
     * @param email 이메일
     * @return 마스킹된 이메일
     */
    public static String emailMasking(String email) {
        if (email == null) {
            return "";
        }
        
        Matcher matcher = Pattern.compile(RegexUtil.EMAIL_MASKING).matcher(email);
        
        if (matcher.find()) {
            String target = matcher.group(1);
            int length = target.length();
            
            if (length > 2) {
                char[] c = new char[length - 2];
                Arrays.fill(c, '*');
                return email.replace(target, target.substring(0, 2) + String.valueOf(c));
            } else {
                char[] c = new char[length - 1];
                Arrays.fill(c, '*');
                return email.replace(target, target.substring(0, 1) + String.valueOf(c));
            }
        }
        
        return email;
    }

    /**
     * 계좌번호 마스킹 (뒤 5자리 마스킹)
     *
     * @param accountNo 계좌번호
     * @return 마스킹된 계좌번호
     */
    public static String accountMasking(String accountNo) {
        if (accountNo == null) {
            return "";
        }
        
        // 계좌번호는 숫자만 파악하므로
        Matcher matcher = Pattern.compile(RegexUtil.ACCOUNT_NUMBER).matcher(accountNo);
        
        if (matcher.find()) {
            int length = accountNo.length();
            
            if (length > 5) {
                char[] c = new char[5];
                Arrays.fill(c, '*');
                return accountNo.replace(accountNo, accountNo.substring(0, length - 5) + String.valueOf(c));
            }
        }
        
        return accountNo;
    }

    /**
     * 카드번호 마스킹 (가운데 8자리 마스킹)
     *
     * @param cardNo 카드번호
     * @return 마스킹된 카드번호
     */
    public static String cardMasking(String cardNo) {
        if (cardNo == null) {
            return "";
        }
        
        // 카드번호 16자리 또는 15자리 '-'포함/미포함 상관없음
        Matcher matcher = Pattern.compile(RegexUtil.CREDIT_CARD).matcher(cardNo);
        
        if (matcher.find()) {
            String target = matcher.group(2) + matcher.group(3);
            int length = target.length();
            
            char[] c = new char[length];
            Arrays.fill(c, '*');
            
            return cardNo.replace(target, String.valueOf(c));
        }
        
        return cardNo;
    }

    /**
     * 카드번호 전체 마스킹 (4자리씩 마스킹)
     *
     * @param cardNumber 카드번호
     * @return 마스킹된 카드번호
     */
    public static String cardMaskingFull(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 15 || cardNumber.length() > 16) {
            throw new IllegalArgumentException("Invalid card number length");
        }
        
        return cardNumber.replaceAll(RegexUtil.CREDIT_CARD_FULL, "$1 $2 **** $3");
    }

    /**
     * 주소 마스킹 (신주소, 구주소, 도로명 주소 숫자만 전부 마스킹)
     *
     * @param address 주소
     * @return 마스킹된 주소
     */
    public static String addressMasking(String address) {
        if (address == null) {
            return "";
        }
        
        // 신(구)주소, 도로명 주소
        Matcher matcher = Pattern.compile(RegexUtil.ADDRESS_OLD).matcher(address);
        Matcher newMatcher = Pattern.compile(RegexUtil.ADDRESS_NEW).matcher(address);
        
        if (matcher.find()) {
            return address.replaceAll(RegexUtil.ADDRESS_NUMBER_MASKING, "*");
        } else if (newMatcher.find()) {
            return address.replaceAll(RegexUtil.ADDRESS_NUMBER_MASKING, "*");
        }
        
        return address;
    }

    /**
     * 사업자등록번호 마스킹 (마지막 4자리 마스킹)
     *
     * @param businessNo 사업자등록번호
     * @return 마스킹된 사업자등록번호
     */
    public static String businessNoMasking(String businessNo) {
        if (businessNo == null) {
            return "";
        }
        
        return businessNo.replaceAll(RegexUtil.BUSINESS_NO_MASKING, "*");
    }

    /**
     * 휴대폰 번호 포맷팅 (010-1234-5678 형식)
     *
     * @param hp 휴대폰 번호
     * @return 포맷팅된 휴대폰 번호
     */
    public static String formatHp(String hp) {
        Matcher m = Pattern.compile(RegexUtil.MOBILE_PHONE).matcher(hp);
        
        if (!m.find()) {
            return hp;
        }
        
        return new StringBuilder()
                .append(m.group("f")).append("-")
                .append(m.group("m")).append("-")
                .append(m.group("l")).toString();
    }
}

