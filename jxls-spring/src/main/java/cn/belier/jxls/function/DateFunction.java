package cn.belier.jxls.function;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

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
@EqualsAndHashCode(callSuper = true)
public class DateFunction extends AbstractJxlsFunction {

    public static final String DEFAULT_NAME = "date";

    public static final String DEFAULT_DATE = "yyyy-MM-dd";

    public static final String DEFAULT_TIME = "HH:mm:ss";

    public static final String DEFAULT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    private String date = DEFAULT_DATE;

    private String datetime = DEFAULT_DATE_TIME;

    private String time = DEFAULT_TIME;

    @Setter(AccessLevel.NONE)
    private Map<String, DateTimeFormatter> dateTimeFormatterCache = new ConcurrentHashMap<>();

    private DateFunction() {
        // 设置默认的名字
        this.name = DEFAULT_NAME;
    }

    /**
     * 获取{@link DateFunction} 实例
     *
     * @return {@link DateFunction}
     */
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
        return FastDateFormat.getInstance(format).format(date);
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
