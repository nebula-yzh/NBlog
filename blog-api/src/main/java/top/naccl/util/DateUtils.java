package top.naccl.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class DateUtils {
    public static final String DATE_FORMAT = "yyyy-M-d";
    public static final String YEAR_FORMAT_STD = "yyyy";
    public static final String DATE_FORMAT_WITH = "yyyyMMdd";
    public static final String DATE_FORMAT_STD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_STR = "yyyy年MM月dd日";
    public static final String MONTH_DATE_FORMAT_STR = "MM月dd日";
    public static final String DATE_FORMAT_MON = "yyyy-MM";
    public static final String YEAR_AND_MONTH = "yyyyMM";
    public static final String MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String SECOND_FORMAT_WITH = "yyyy-MM-dd HH:mm:ss";
    public static final String SECOND_FORMAT_RAW = "yyyyMMddHHmmss";
    public static final String MILSECOND_FORMAT_WITH = "yyyy-MM-dd HH:mm:ss SSS";
    public static final String MILSECOND_FORMAT_WITH_ONE = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String SPECIAL_FORMAT_WITH = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final DateTimeFormatter STANDARD_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(SECOND_FORMAT_WITH);
    public static final DateTimeFormatter STANDARD_DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_STD);
    public static final DateTimeFormatter STANDARD_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter STANDARD_MONTH_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_MON);

    public static final String DAY_START_TIME = "00:00:00";
    public static final String DAY_END_TIME = "23:59:59";
    public static final int MINUTE_IN_SECOND = 60;
    public static final int TEN_MINUTE_IN_SECOND = 10 * MINUTE_IN_SECOND;
    public static final int ONE_HOUR_IN_SECOND = MINUTE_IN_SECOND * 60;
    public static final int ONE_DAY_IN_SECOND = ONE_HOUR_IN_SECOND * 24;
    public static final int ONE_WEEK_IN_SECOND = ONE_DAY_IN_SECOND * 7;

    public static final long ONE_SECOND_IN_MILLIS = 1000;
    public static final long ONE_MINUTE_IN_MILLIS = ONE_SECOND_IN_MILLIS * 60;
    public static final long ONE_HOUR_IN_MILLIS = ONE_MINUTE_IN_MILLIS * 60;
    public static final long EIGHT_HOUR_IN_MILLS = ONE_HOUR_IN_MILLIS * 8;
    public static final long ONE_DAY_IN_MILLIS = ONE_HOUR_IN_MILLIS * 24;
    public static final long ONE_MONTH_IN_MILLIS = ONE_DAY_IN_MILLIS * 30;

    public static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}+[-]\\d{1,2}+[-]\\d{1,2}+");

    private DateUtils() {
    }

    /**
     * 判断time是否在from，to之内
     *
     * @param time 指定日期
     * @param from 开始日期
     * @param to   结束日期
     * @return
     */
    public static boolean belongCalendar(Date time, Date from, Date to) {
        Calendar date = Calendar.getInstance();
        date.setTime(time);

        Calendar after = Calendar.getInstance();
        after.setTime(from);

        Calendar before = Calendar.getInstance();
        before.setTime(to);

        if (date.after(after) && date.before(before)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取今天凌晨
     *
     * @return
     */
    public static Date initDateByDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 判断time是否在now的n天之内
     *
     * @param time
     * @param now
     * @param n    正数表示在条件时间n天之后，负数表示在条件时间n天之前 返回true代表超过，返回false代表没超过
     * @return
     */
    public static boolean belongDate(Date time, Date now, int n) {
        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(now);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, n);
        Date before7days = calendar.getTime();   //得到n前的时间
        if (before7days.getTime() <= time.getTime()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 将日期字符串转化为日期对象
     *
     * @param date
     * @param pattern
     * @param defaultVal
     * @return
     */
    public static Date parse(String date, String pattern, Date defaultVal) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date x = defaultVal;
        try {
            x = format.parse(date);
        } catch (Exception e) {
            return x;
        }
        return x;
    }

    /**
     * 将日期字符串转化为日期对象
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date parse(String date, String pattern) {
        return parse(date, pattern, null);
    }

    public static Date parseStander(String date) {
        return parse(date, DATE_FORMAT_STD, null);
    }

    public static Date parseStanderAndMonth(String date) {
        return parse(date, SECOND_FORMAT_WITH, null);
    }

    public static Date parseEndStander(String date) {
        return parse(date + " 23:59:59", SECOND_FORMAT_WITH, null);
    }

    /**
     * 判断日期是否在现在之前
     *
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static boolean isDateBeforeNow(String date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date destDate = format.parse(date);
        return destDate.before(new Date());
    }


    /**
     * @param dateString1 字符串1
     * @param dateString2 字符串2
     * @param pattern     格式
     * @return 字符串1 在 字符串2 之前 -> 负数; 相同 -> 0; 字符串1 在 字符串2 之后 -> 正数
     * @throws ParseException 解析异常
     */
    public static int compareDateString(String dateString1, String dateString2, String pattern) throws ParseException {
        return compareDateString(dateString1, pattern, dateString2, pattern);
    }

    /**
     * 比较两个字符串日期
     *
     * @param dateString1 字符串1
     * @param pattern1    字符串1的格式
     * @param dateString2 字符串2
     * @param pattern2    字符串2的格式
     * @return 字符串1 在 字符串2 之前 -> 负数; 相同 -> 0; 字符串1 在 字符串2 之后 -> 正数
     * @throws ParseException 解析异常
     */
    public static int compareDateString(String dateString1, String pattern1, String dateString2, String pattern2) throws ParseException {
        final Date date1 = new SimpleDateFormat(pattern1).parse(dateString1);
        final Date date2 = new SimpleDateFormat(pattern2).parse(dateString2);
        return date1.compareTo(date2);
    }

    /**
     * 判断日期是否在现在之后
     *
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static boolean isDateAfterNow(String date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date destDate = format.parse(date);
        return destDate.after(new Date());
    }

    /**
     * 将日期类型转换为字符串类型
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatByPattern(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String formatByPattern(String date, String prePattern, String newPattern) {
        Date dateParsed = parse(date, prePattern);
        return formatByPattern(dateParsed, newPattern);
    }

    /**
     * 获取系统当前时间戳 格式：yyyy-MM-DD HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTimestamp() {
        return format(new Date(), DateUtils.SECOND_FORMAT_WITH);
    }

    /**
     * 获取系统当前时间戳
     *
     * @return
     */
    public static String getCurrentTimestamp(String format) {
        return format(new Date(), format);
    }

    /**
     * 获取系统当前时间戳 格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getStdCurrentTimestamp() {
        return format(new Date(), DateUtils.SECOND_FORMAT_WITH);
    }

    public static String getStandardDate(Date date) {
        if (date == null) {
            return null;
        }
        return format(date, DateUtils.DATE_FORMAT_STD);
    }

    public static String getStandardTimestamp(Date date) {
        if (date == null) {
            return "";
        }
        return format(date, DateUtils.SECOND_FORMAT_WITH);
    }

    public static String getYear(Date date) {
        return format(date, DateUtils.YEAR_FORMAT_STD);
    }


    /**
     * 获取今天的日期，例如：2011-07-20
     *
     * @return
     */
    public static Date getToday() {
        Calendar ca = Calendar.getInstance();

        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);

        return ca.getTime();
    }

    /**
     * 判断两个时间相差多少小时，后一个时间要大于前一个时间
     *
     * @return
     */
    public static long getTimeDiff(String preDate, String afterDate) {
        if (!StringUtils.isFine(preDate) || !StringUtils.isFine(afterDate)) {
            log.error("getTimeDiff 时间格式错误");
            return -1;
        }
        Date pre = parse(preDate, SECOND_FORMAT_WITH);
        Date after = parse(afterDate, SECOND_FORMAT_WITH);
        long preTime = pre.getTime();
        long afterTime = after.getTime();
        long diff = afterTime - preTime;
        return diff / (1000 * 60 * 60);
    }

    /**
     * 获取前x分钟的时间
     *
     * @return
     */
    public static Date getPastXMinuteTime(int x) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) - x);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    /**
     * 获取前x小时的时间（小时）。
     *
     * @return
     */
    public static Date getPastXHourTime(int x) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY) - x);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }

    /**
     * 获取前x小时的时间（小时）。
     *
     * @return
     */
    public static Date getPastXHour(int x) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY) - x);
        return ca.getTime();
    }

    /**
     * 获取前x天的时间。
     *
     * @param x
     * @return
     */
    public static String getPastXDate(int x) {
        return format(getPastXDateTime(x), SECOND_FORMAT_WITH);
    }

    /**
     * 获取前x天的时间。
     *
     * @param x
     * @return
     */
    public static String getPastXDateWithoutTime(int x) {
        return format(getPastXDateTime(x), DATE_FORMAT_STD);
    }

    /**
     * 获取前x天的时间。
     *
     * @param x
     * @return
     */
    public static Date getPastXDateTime(int x) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_YEAR, ca.get(Calendar.DAY_OF_YEAR) - x);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        // 增加设置毫秒也为0，fix: 表里有数据，但查询结果为null
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    public static Date getPastXDateDay(int x) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_YEAR, ca.get(Calendar.DAY_OF_YEAR) - x);
        return ca.getTime();
    }

    public static Date getPastXMonthTime(int x) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - x);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }

    public static Date getPastXMonth(int x) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - x);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }

    public static Date getPastXMonthTime(Date from, int x) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(from);
        ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - x);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }

    public static Date getPastXEndTime(int x) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_YEAR, ca.get(Calendar.DAY_OF_YEAR) - x);
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        return ca.getTime();
    }

    /**
     * 根据时间from，获取from的前x天的时间。
     *
     * @param from
     * @param x
     * @return
     */
    public static Date getPastXDateTime(Date from, int x) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(from);
        ca.set(Calendar.DAY_OF_YEAR, ca.get(Calendar.DAY_OF_YEAR) - x);
        return ca.getTime();
    }

    /**
     * 根据时间from，获取from的前x天的时间。
     *
     * @param from
     * @param x
     * @return
     */
    public static Date getBehindXDateTime(Date from, int x) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(from);
        ca.set(Calendar.DAY_OF_YEAR, ca.get(Calendar.DAY_OF_YEAR) + x);
        return ca.getTime();
    }

    /**
     * 根据时间from，获取from的前x天的时间。
     *
     * @param from
     * @param x
     * @return
     */
    public static String getPastXDate(Date from, int x) {
        return format(getPastXDateTime(from, x), DATE_FORMAT_STD);
    }

    /**
     * 获取该时间中的日期
     *
     * @param d
     * @return
     */
    public static Date getDate(Date d) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    /**
     * 获取该时间中的小时
     *
     * @param d
     * @return
     */
    public static int getHourOfDay(Date d) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        return ca.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取该时间中的日期
     *
     * @param d
     * @return
     */
    public static String getDateString(Date d, String pattern) {
        return format(getDate(d), pattern);
    }

    /**
     * 获取该时间中的日期
     *
     * @param d
     * @return
     */
    public static String getDateString(Date d) {
        return format(getDate(d), SECOND_FORMAT_WITH);
    }

    public static String getDateStringNormal(Date d) {
        return format(d, SECOND_FORMAT_WITH);
    }

    /**
     * 获取该时间中的小时（24小时）
     *
     * @param d
     * @return
     */
    public static int get24Hour(Date d) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        return ca.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取该时间中的分钟（24小时）
     *
     * @param d
     * @return
     */
    public static int getMinute(Date d) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        return ca.get(Calendar.MINUTE);
    }

    public static int getDayOfMonth(Date d) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        return ca.get(Calendar.DAY_OF_MONTH);
    }

    public static Date getFirstDayOfMonthFromDate(Date currentDate) {
        return parse(format(currentDate, "yyyy-MM") + "-01", DATE_FORMAT_STD);
    }

    /**
     * currentDate所在的星期的 周一的日期
     *
     * @return
     */
    public static Date getMondayOfCurrentWeekFromDate(Date currentDate) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(currentDate);
        ca.set(Calendar.DAY_OF_WEEK, 2);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }

    /**
     * 获取精确到小时的时间
     *
     * @param currentDate
     * @return
     */
    public static Date getTimeByPrecisionOfHour(Date currentDate) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(currentDate);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }

    /**
     * currentDate所在的星期的 周一的日期
     *
     * @return
     */
    public static Date getMondayOfCurrentWeek(String currentDate) {
        return getMondayOfCurrentWeekFromDate(parse(currentDate, DATE_FORMAT_STD));
    }

    /**
     * currentDate所在的星期的 周一的日期
     *
     * @return
     */
    public static String getStringMondayOfCurrentWeek(String currentDate) {
        return format(getMondayOfCurrentWeek(currentDate), DATE_FORMAT_STD);
    }

    /**
     * currentDate所在的月的1号
     *
     * @param currentDate
     * @return
     */
    public static Date getFirstDateOfCurrentMonth(String currentDate) {
        return getFirstDateOfMonth(currentDate, 0);
    }

    /**
     * currentDate所在的月的1号
     *
     * @param currentDate
     * @return
     */
    public static String getStringFirstDateOfCurrentMonth(String currentDate) {
        return getStringFirstDateOfMonth(currentDate, 0);
    }

    /**
     * currentDate所在的月的 下个月的1号
     *
     * @param currentDate
     * @param x
     * @return
     */
    public static String getStringFirstDateOfMonth(String currentDate, int x) {
        return format(getFirstDateOfMonth(currentDate, x), DATE_FORMAT_STD);
    }

    public static Date getLastDateOfCurrentMonth(String currentDate) {
        Date nextMonthFirstDay = getFirstDateOfMonth(currentDate, 1);
        return getPastXDateTime(nextMonthFirstDay, 1);
    }


    /**
     * currentDate所在的月的 后面x个月的1号
     *
     * @param currentDate
     * @param x           0表示当前月，负数表示之前的月
     * @return
     */
    public static Date getFirstDateOfMonth(String currentDate, int x) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(parse(currentDate, DATE_FORMAT_STD));
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) + x);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }

    /**
     * 分割时间
     *
     * @param start
     * @param end
     * @param type     时间类型，天，小时等
     * @param interval
     * @return
     */
    private static List<Date> splitDate(Date start, Date end, int type, int interval) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(start);
        List<Date> list = new ArrayList<Date>();
        list.add(start);
        do {
            ca.set(type, ca.get(type) + interval);
            list.add(ca.getTime());
        } while (ca.getTime().before(end));

        if (list.size() > 1) {
            list.remove(list.size() - 1);
        }
        list.add(end);
        return list;
    }

    /**
     * 分割时间
     *
     * @param start
     * @param end
     * @param interval 单位：天
     * @return
     */
    public static List<Date> splitDate(Date start, Date end, int interval) {
        return splitDate(start, end, Calendar.DAY_OF_YEAR, interval);
    }

    /**
     * 分割时间
     *
     * @param start
     * @param end
     * @param interval 单位：小时
     * @return
     */
    public static List<Date> splitHour(Date start, Date end, int interval) {
        return splitDate(start, end, Calendar.HOUR_OF_DAY, interval);
    }


    public static String toFullDateStr(final Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(date);
    }

    public static String toFullDayStr(final Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(date);
    }

    public static String toDateStr(final Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat f = new SimpleDateFormat(DATE_FORMAT_STD);
        return f.format(date);
    }

    public static String toShortTimeStr(final Date date) {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        return f.format(date);
    }

    /**
     * 返回日期所在月份.
     */
    public static int getMonth(Date statDate) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(statDate);
        return ca.get(Calendar.MONTH) + 1;
    }

    public static Date toDate(String dateStr) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = f.parse(dateStr);
        } catch (ParseException e) {
            log.error("toDate error", e);
        }
        return date;
    }

    public static Date toShortDate(String dateStr) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = f.parse(dateStr);
        } catch (ParseException e) {
            log.error("toShortDate error", e);
        }
        return date;
    }

    public static long getShortTime(String dateStr) {
        return toShortDate(dateStr).getTime();
    }

    /***
     * r日期格式 yyyy-MM-dd HH:mm:ss Returns the number of milliseconds since January 1, 1970, 00:00:00
     * GMT represented by this Date object.
     *
     * @param dateStr
     * @return
     */
    public static long getTime(String dateStr) {
        Date date = toDate(dateStr);
        return date == null ? 0 : date.getTime();
    }

    /***
     * r日期格式 yyyy-MM-dd HH:mm:ss Returns the number of milliseconds since January 1, 1970, 00:00:00
     * GMT represented by this Date object.
     *
     * @param dateStr
     * @return
     */
    public static double getTimeDouble(String dateStr) {
        return (double) (getTime(dateStr));
    }

    public static Date toDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.getTime();
    }

    public static Date getAMonthAfter(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        return cal.getTime();
    }

    public static Date getAMonthBefore(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        return cal.getTime();
    }

    public static int getDaysOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date getDayAfter(Date today, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, num);
        return cal.getTime();
    }

    public static boolean isToday(Date time) {
        boolean isToday = false;
        if (toFullDayStr(time).equals(toFullDayStr(new Date()))) {
            isToday = true;
        }
        return isToday;
    }

    public static String getTime() {
        return getTime(0);
    }

    public static boolean validate(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return false;
        }
        // 使用正则表达式 测试 字符 符合 dddd-dd-dd 的格式(d表示数字)
        Matcher m = DATE_PATTERN.matcher(dateString);
        if (!m.matches()) {
            return false;
        }
        // 得到年月日
        String[] array = dateString.split("-");
        int year = Integer.valueOf(array[0]);
        int month = Integer.valueOf(array[1]);
        int day = Integer.valueOf(array[2]);
        if (month < 1 || month > 12) {
            return false;
        }
        int[] monthLengths = new int[]{0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isLeapYear(year)) {
            monthLengths[2] = 29;
        } else {
            monthLengths[2] = 28;
        }
        int monthLength = monthLengths[month];
        return !(day < 1 || day > monthLength);
    }

    public static boolean validate(String dateString, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            format.setLenient(false);
            format.parse(dateString);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private static boolean isLeapYear(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    public static String getTime(final long millis) {
        Date date = new Date();
        if (millis > 0) {
            date.setTime(millis);
        }
        SimpleDateFormat GET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return GET_TIME_FORMAT.format(date);
    }

    public static Date getDate(final long millis) {
        Date date = new Date();
        if (millis > 0) {
            date.setTime(millis);
        }
        return date;
    }

    public static String format(Date optime, String secondFormatWith) {
        SimpleDateFormat GET_TIME_FORMAT = new SimpleDateFormat(secondFormatWith);
        return GET_TIME_FORMAT.format(optime);
    }

    /***
     * 判断两个日期是否是同一年
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isSameYear(Date start, Date end) {
        return !(start == null || end == null) && getYear(start).equals(getYear(end));
    }

    public static boolean isSameMonth(Date start, Date end) {
        return isSameYear(start, end) && getMonth(start) == getMonth(end);
    }

    public static boolean isSameDay(Date start, Date end) {
        return !(start == null || end == null) && getStandardDate(start).equals(getStandardDate(end));
    }

    public static int getDayDiff(Date a, Date b) {
        Calendar calendarA = Calendar.getInstance();
        Calendar calendarB = Calendar.getInstance();
        calendarA.setTime(a);
        calendarB.setTime(b);
        int aDay = calendarA.get(Calendar.DAY_OF_YEAR);
        int bDay = calendarB.get(Calendar.DAY_OF_YEAR);
        return aDay - bDay;
    }

    public static Date getYesterday(String start) {
        return getDate(start, 1);
    }

    public static Date getYesterday() {
        return getPastXDateTime(1);
    }

    public static Date getDate(String start, int pastXDays) {
        Date date;
        if (start == null) {
            date = getPastXDateTime(pastXDays);
        } else {
            date = parseStander(start);
        }
        return date;
    }

    public static Date getEndOfLastMonth(String start) {
        Date date;
        if (start == null) {
            date = getPastXDateTime(getFirstDayOfMonthFromDate(new Date()), 1);
        } else {
            date = parseStander(start);
        }
        return date;
    }

    public static Date getStartOfLastMonth(String start) {
        Date date;
        if (start == null) {
            date = getPastXMonthTime(1);
        } else {
            date = parseStander(start);
        }
        return date;
    }

    public static Date getDateFromMonth(String month) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = f.parse(month);
        } catch (ParseException e) {
            log.error("getDateFromMonth error", e);
        }
        return date;
    }

    public static String toStartTimeOfDay(String dateString) {
        return StringUtils.isEmpty(dateString) ? null : dateString + " " + DAY_START_TIME;
    }

    public static String toEndTimeOfDay(String dateString) {
        return dateString + " " + DAY_END_TIME;
    }

    public static String toStartTimeOfDay(Date dateString) {
        String pastXDateString = DateUtils.toDateStr(dateString);
        return toStartTimeOfDay(pastXDateString);
    }

    public static String toEndTimeOfDay(Date dateString) {
        String pastXDateString = DateUtils.toDateStr(dateString);
        return toEndTimeOfDay(pastXDateString);
    }

    public static Date getEndDateOfYear(Date date) {
        return parse(getYear(date) + "-12-31", DATE_FORMAT_STD);
    }

    public static Date getStartDateOfYear(Date Date) {
        return parse(getYear(Date) + "-01-01", DATE_FORMAT_STD);
    }

    public static Date getTime(String start, int offset) {
        Date startTime;
        if (start == null) {
            startTime = DateUtils.getPastXDateTime(offset);
        } else {
            startTime = DateUtils.parse(start, DateUtils.DATE_FORMAT_STD);
        }
        return startTime;
    }

    public static Date toDateByMonthStr(String cancelMonth) {
        SimpleDateFormat f = new SimpleDateFormat(DATE_FORMAT_MON);
        Date date = null;
        try {
            date = f.parse(cancelMonth);
        } catch (ParseException e) {
            log.error("toDate error", e);
        }
        return date;
    }

    public static Date getLatestWeekTime(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);//把指定时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -7);  //设置为7天前
        return calendar.getTime();   //得到7天前的时间
    }

    public static Date getPastXHourToToday(int x) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_YEAR, ca.get(Calendar.DAY_OF_YEAR) - 1);
        ca.set(Calendar.HOUR_OF_DAY, 24 - x);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }


    /**
     * 根据间隔日期获取当月月份，大于等于intervalDay的取下一月，小于的取本月
     *
     * @param statDate
     * @param intervalDay
     * @return
     */
    public static Date getMonthByInterval(Date statDate, int intervalDay) {
        Integer statDay = DateUtils.getDayOfMonth(statDate);
        Date pnoDate = DateUtils.getFirstDayOfMonthFromDate(statDate);
        if (statDay >= intervalDay) {
            pnoDate = DateUtils.getAMonthAfter(pnoDate);
        }
        return pnoDate;
    }


    /**
     * 获取date所在月的第xDay天的日期
     *
     * @param date
     * @param xDay
     * @return
     */
    public static Date getXDateInMonth(Date date, int xDay) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, xDay);
        return ca.getTime();
    }

    public static int getDayOfWeek(Date d) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        return ca.get(Calendar.DAY_OF_WEEK);
    }

    public static int getSecondOfDate(Date d) {
        if (d == null) {
            return 0;
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        return ca.get(Calendar.SECOND);
    }

    public static long getPublishHours(Date publishTime) {
        if (publishTime == null) {
            return 0;
        }
        return (System.currentTimeMillis() - publishTime.getTime()) / DateUtils.ONE_HOUR_IN_MILLIS;
    }

    public static String getTodayDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STR);
        return sdf.format(new Date());
    }

    public static Date convertLocalDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }

    public static LocalDate convertToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            return day2 - day1;
        }
    }


    public static String getStdCurrentQuarter() {
        int month = getMonth(new Date());
        String currentQuarter = getYear(new Date()) + "-Q";
        return getQuarter(currentQuarter, month);
    }

    public static String getQuarter(String currentQuarter, int month) {
        if (month <= 3) {
            return currentQuarter + "1";
        } else if (month <= 6) {
            return currentQuarter + "2";
        } else if (month <= 9) {
            return currentQuarter + "3";
        }
        return currentQuarter + "4";
    }

    public static String getQuarterByDate(Date date) {
        int month = getMonth(date);
        String quarterYear = getYear(date) + "-Q";
        return getQuarter(quarterYear, month);
    }

    public static boolean isWeekend(Date date) {
        try {
            if (getDayOfWeek(date) == Calendar.SATURDAY || getDayOfWeek(date) == Calendar.SUNDAY) {
                return true;
            }
        } catch (Exception e) {
            log.error("isWeekend error. date: " + date);
        }
        return false;
    }

    //判断两个时间区间是否重叠
    public static boolean isOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        return (startDate1.after(startDate2) && startDate1.before(endDate2))
                || (endDate1.after(startDate2) && endDate1.before(endDate2))
                || (!startDate1.after(startDate2) && !endDate1.before(startDate2));
    }

    /**
     * 判断end与start是否间隔大于一年
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 大于一年或异常 -> true; 小于一年 -> false
     */
    public static boolean dateIntervalGreaterThanOneYear(String start, String end) {
        try {
            return StringUtils.isEmpty(start)
                    || StringUtils.isEmpty(end)
                    || 365 < differentDays(parse(start, DateUtils.DATE_FORMAT_STD), parse(end, DateUtils.DATE_FORMAT_STD));
        } catch (Exception e) {
            log.error(String.format("failed to compare date, start = %s, end = %s", start, end));
            return true;
        }
    }

    /**
     * 开始结束时间校验
     *
     * @param start 开始时间 yyyy-MM-dd
     * @param end   结束时间 yyyy-MM-dd
     * @author maning
     * @date 2022-02-10 14:36
     **/
    public static boolean endAfterStart(String start, String end) {
        if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            return true;
        }
        Date startDate = parse(start, DATE_FORMAT_STD);
        Date endDate = parse(end, DATE_FORMAT_STD);
        if (startDate.after(endDate)) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("…".length());
        System.out.println(endAfterStart("2021-01-01", "2021-02-01"));
    }

}
