package com.merlin.asset.core.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

/**
 * @author Minh-Luan H. Phan
 * Created on: 2021.05.08 20:20
 */
public class DateTimeUtils {

    public static final DateTimeFormatter ISO8601_DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .optionalStart()
            .appendOffsetId()
            .optionalStart()
            .toFormatter();
    public static final DateTimeFormatter MA_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .append(ISO_LOCAL_TIME)
            .optionalStart()
            .appendOffsetId()
            .optionalStart()
            .toFormatter();

    public static final DateTimeFormatter MA_DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(ISO_LOCAL_TIME)
            .toFormatter();

    public static final DateTimeFormatter ISO8601_DATE_TIME_FORMATTER_NO_PRECISION = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral('T')
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .appendLiteral('Z')
            .toFormatter();

    public static final DateTimeFormatter MA_YYYY_MM_DD_HH_MM = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .toFormatter();

    public static final DateTimeFormatter MA_YYYY_MM_DD_HH_MM_SS = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2).appendLiteral(":")
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .appendPattern(".")
            .appendFraction(ChronoField.MICRO_OF_SECOND, 3, 3, false)
            .toFormatter();

    public static final DateTimeFormatter MA_LOG = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral('-')
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .toFormatter();
    public static final String CDP_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";


    public static ZonedDateTime getCurrentZonedDateTime(String timeZone) {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId serverTimeZone = ZoneId.systemDefault();
        ZonedDateTime currentDateTime = localDateTime.atZone(serverTimeZone);
        return currentDateTime.withZoneSameInstant(ZoneId.of(timeZone));
    }

    public static ZonedDateTime addDate(ZonedDateTime date, int amount, String timeUnit) {
        if (amount == 0) return null;
        if (ParserUtils.isNullOrEmpty(timeUnit)) return null;
        if (date == null) return null;
        switch (timeUnit) {
            case "day":
                return date.plusDays(amount);
            case "hour":
                return date.plusHours(amount);
            default:
                return null;
        }
    }

    public static ZonedDateTime getStartTime(ZonedDateTime date, String timeUnit) {
        if (date == null) return null;
        if (ParserUtils.isNullOrEmpty(timeUnit)) return null;

        LocalDateTime result;
        if (timeUnit.equals("day")) {
            result = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0, 0);
        } else {
            result = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), date.getHour(), 0, 0, 0);
        }
        return result.atZone(date.getZone());
    }

    public static ZonedDateTime getEndTime(ZonedDateTime date, String timeUnit) {
        if (date == null) return null;
        if (ParserUtils.isNullOrEmpty(timeUnit)) return null;

        LocalDateTime result;
        if (timeUnit.equals("day")) {
            result = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59, 999999999);
        } else {
            result = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), date.getHour(), 59, 59, 999999999);
        }
        return result.atZone(date.getZone());
    }


    public static String convertZonedDateTimeToFormat_ISO8601(ZonedDateTime date, String timeZone) {
        if (date == null) return null;
        if (timeZone == null) return null;
        DateTimeFormatter dateTimeFormatter = ISO8601_DATE_TIME_FORMATTER.withZone(ZoneId.of(timeZone));
        return dateTimeFormatter.format(date);
    }

    public static String convertZonedDateTimeToFormat_ISO8601_V2(ZonedDateTime date, String timeZone) {
        if (date == null) return null;
        if (timeZone == null) return null;
        DateTimeFormatter dateTimeFormatter = ISO8601_DATE_TIME_FORMATTER_NO_PRECISION.withZone(ZoneId.of(timeZone));
        return dateTimeFormatter.format(date);
    }

    public static String convertZonedDateTimeToFormat(ZonedDateTime date, String timeZone, DateTimeFormatter dtf) {
        if (date == null) return null;
        if (timeZone == null) return null;
        return dtf.withZone(ZoneId.of(timeZone)).format(date);
    }

    public static ZonedDateTime convertStringToZonedDateTime(String dateString, String pattern, String sourceTimeZone, String targetTimeZone) {
        if (ParserUtils.isNullOrEmpty(dateString)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, dateTimeFormatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(sourceTimeZone));
        return zonedDateTime.withZoneSameInstant(ZoneId.of(targetTimeZone));
    }


    public static ZonedDateTime convertStringToZonedDateTime(String dateString, DateTimeFormatter dateTimeFormatter, String sourceTimeZone, String targetTimeZone) {
        if (ParserUtils.isNullOrEmpty(dateString)) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, dateTimeFormatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(sourceTimeZone));
        return zonedDateTime.withZoneSameInstant(ZoneId.of(targetTimeZone));
    }

    public static String reformatDate(String dateString, String sourceFormat, DateTimeFormatter targetFormat, String sourceTimeZone, String targetTimeZone) {
        ZonedDateTime zonedDateTime = convertStringToZonedDateTime(dateString, sourceFormat, sourceTimeZone, targetTimeZone);
        return convertZonedDateTimeToFormat(zonedDateTime, targetTimeZone, targetFormat);
    }

    public static long toEpochSecond(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) return -1;
        return zonedDateTime.toInstant().getEpochSecond();
    }

    public static long toEpochMilli(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) return -1;
        return zonedDateTime.toInstant().getEpochSecond() * 1000;
    }

    public static ZonedDateTime fromEpochSecond(long epochSecond, String timeZone) {
        return ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(epochSecond),
                ZoneId.of(timeZone)
        );
    }

    public static ZonedDateTime fromEpochMilli(long epochMilli, String timeZone) {
        return ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(epochMilli),
                ZoneId.of(timeZone)
        );
    }

    public static String reformatDate(String dateString, String sourceFormat, DateTimeFormatter targetFormat) {
        return reformatDate(dateString, sourceFormat, targetFormat, "UTC", "UTC");
    }
}
