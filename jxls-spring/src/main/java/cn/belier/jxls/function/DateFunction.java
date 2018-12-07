package cn.belier.jxls.function;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日期格式化
 *
 * @author belier
 * @date 2018/12/6
 */
@Data
@Accessors(chain = true)
public class DateFunction {

    public static final String NAME = "date";

    public static final String DATE = "yyyy-MM-dd";

    public static final String TIME = "HH:mm:ss";

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    private String date = DATE;

    private String datetime = DATE_TIME;

    private String time = TIME;

    @Setter(AccessLevel.NONE)
    private Map<String, DateTimeFormatter> dateTimeFormatterCache = new ConcurrentHashMap<>();

    public static DateFunction of() {
        return new DateFunction();
    }

    public String format(LocalDateTime localDateTime, String format) {
        if (localDateTime == null || StringUtils.isBlank(format)) {
            return "";
        }
        return localDateTime.format(getDateTimeFormatter(format));
    }

    public String format(LocalDate localDate, String format) {
        if (localDate == null || StringUtils.isBlank(format)) {
            return "";
        }
        return localDate.format(getDateTimeFormatter(format));
    }

    public String format(LocalTime localTime, String format) {
        if (localTime == null || StringUtils.isBlank(format)) {
            return "";
        }
        return localTime.format(getDateTimeFormatter(format));
    }

    public String format(Date date, String format) {
        if (date == null || StringUtils.isBlank(format)) {
            return "";
        }
        return new SimpleDateFormat(format).format(date);
    }

    public String datetime(LocalDateTime localDateTime) {
        return format(localDateTime, this.datetime);
    }

    public String datetime(Date date) {
        return format(date, this.datetime);
    }

    public String date(LocalDateTime localDateTime) {
        return format(localDateTime, this.date);
    }

    public String date(LocalDate localDate) {
        return format(localDate, this.date);
    }

    public String date(Date date) {
        return format(date, this.date);
    }

    public String time(LocalTime localTime) {
        return format(localTime, this.time);
    }

    public String time(LocalDate localDate) {
        return format(localDate, this.time);
    }

    public String time(LocalDateTime localDateTime) {
        return format(localDateTime, this.time);
    }

    public String time(Date date) {
        return format(date, this.time);
    }

    private DateTimeFormatter getDateTimeFormatter(String format) {
        return dateTimeFormatterCache.computeIfAbsent(format, key -> DateTimeFormatter.ofPattern(format));
    }

}
