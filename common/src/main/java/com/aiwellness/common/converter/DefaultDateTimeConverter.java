package com.aiwellness.common.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;

/**
 * com.aiwellness.common.converter
 * <p>
 * DefaultDateTimeConverter
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
@Slf4j
public class DefaultDateTimeConverter {
  public static final LocalDate DATE_2999_12_31 = convertDate("2999-12-31");
  public static final LocalDateTime DATE_TIME_2999_12_31 = convertDateTime("2999-12-31 00:00:00");


  private DefaultDateTimeConverter() {

  }

  public static long convertMillisecond(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return 0;
    }
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  public static LocalDate convertDate(String date) {
    if (date == null || date.isEmpty()) {
      return null;
    }
    return LocalDate.parse(date, DefaultDateTimeFormat.DATE_FORMAT);
  }

  public static String convertDate(LocalDate localDate, DateTimeFormatter format) {
    if (localDate == null) {
      return null;
    }
    return localDate.format(format);
  }

  public static String convertDate(LocalDate localDate) {
    return convertDate(localDate, DefaultDateTimeFormat.DATE_FORMAT);
  }

  public static LocalTime convertTime(String time) {
    if (time == null) {
      return null;
    }
    return LocalTime.parse(time, DefaultDateTimeFormat.TIME_FORMAT);
  }

  public static String convertTime(LocalTime localTime) {
    if (localTime == null) {
      return null;
    }
    return localTime.format(DefaultDateTimeFormat.TIME_FORMAT);
  }

  public static LocalDateTime convertDateTime(String dateTime) {
    if (dateTime == null) {
      return null;
    }
    return LocalDateTime.parse(dateTime, DefaultDateTimeFormat.DATE_TIME_FORMAT);
  }

  public static LocalDateTime convertTimeStamp(String dateTime) {
    if (dateTime == null) {
      return null;
    }
    return LocalDateTime.parse(dateTime, DefaultDateTimeFormat.DATE_TIMESTAMP_FORMAT);
  }

  public static String convertDateTime(LocalDateTime dateTime, DateTimeFormatter formatter) {
    if (dateTime == null) {
      return null;
    }

    return dateTime.format(formatter);
  }

  public static String convertDateTime(LocalDateTime dateTime) {
    return convertDateTime(dateTime, DefaultDateTimeFormat.DATE_TIME_FORMAT);
  }

  public static String convertDateWithDay(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return localDateTime.toLocalDate().format(
        DateTimeFormatter.ofPattern("yyyy-MM-dd E", Locale.KOREAN));
  }

  public static String convertDay(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return localDateTime.format(DefaultDateTimeFormat.DATE_FORMAT);
  }

  public static String convertUploadDateTime(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return localDateTime.format(DefaultDateTimeFormat.DATE_TIME_FORMAT_UPLOAD);
  }

  public static String convertYearMonthDay(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return localDateTime.format(DefaultDateTimeFormat.YEAR_MONTH_DAY);
  }

  public static String convertHourMinutes(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return localDateTime.format(DefaultDateTimeFormat.HOUR_MINUTES);
  }
}
