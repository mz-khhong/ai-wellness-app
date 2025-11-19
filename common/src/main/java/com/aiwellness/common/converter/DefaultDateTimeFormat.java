package com.aiwellness.common.converter;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.format.DateTimeFormatter;

/**
 * com.aiwellness.common.converter
 * <p>
 * DefaultDateTimeFormat
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
public class DefaultDateTimeFormat {

  private DefaultDateTimeFormat() {
  }

  public static final DateTimeFormatter DATE_FORMAT = ofPattern("yyyy-MM-dd");
  public static final DateTimeFormatter TIME_FORMAT = ofPattern("HH:mm:ss");
  public static final DateTimeFormatter DATE_TIME_FORMAT = ofPattern("yyyy-MM-dd HH:mm:ss");
  public static final DateTimeFormatter DATE_TIME_MINUTE_FORMAT = ofPattern("yyyy-MM-dd HH:mm");
  public static final DateTimeFormatter DATE_TIMESTAMP_FORMAT = ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
  public static final DateTimeFormatter DATE_TIME_FORMAT_UPLOAD = ofPattern("yyyyMMddHHmmss");
  public static final DateTimeFormatter YEAR_MONTH_DAY = ofPattern("yyyyMMdd");
  public static final DateTimeFormatter STRING_TIME_FORMAT = ofPattern("HHmmss");
  public static final DateTimeFormatter HOUR_MINUTES = ofPattern("HHmm");

}

