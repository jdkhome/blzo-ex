package com.jdkhome.blzo.ex.utils.tools;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
public class DateUtil extends DateUtils {

    public static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String DATE_PATTERN = "yyyy-MM-dd";

    public static String TIME_PATTERN = "HH:mm:ss";

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
            "yyyy.MM.dd HH:mm", "yyyy.MM", "yyyy-MM-dd'T'HH:mm:ssXXX", "yyyy-MM-dd'T'HH:mm:ssz",
            "yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ssSSS", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "EEE MMM ddHH:mm:ss 'GMT' yyyy"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * Date得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @param : [localDateTime]
     * @return : java.util.Date
     * @methodName LocalDateTime 得到Date类型
     * @description LocalDateTime 得到Date类型
     * @author : mumulei
     * @date :2018/12/16 16:59
     **/
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if(localDateTime == null){
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        return Date.from(zdt.toInstant());
    }

    /**
     * @param : java.util.Date
     * @return : [localDateTime]
     * @methodName Date 得到LocalDateTime类型
     * @description Date 得到LocalDateTime类型
     * @author : mumulei
     * @date :2018/12/16 16:59
     **/
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * @param : java.util.String
     * @return : [localDateTime]
     * @methodName String 得到LocalDateTime类型
     * @description String 得到LocalDateTime类型
     * @author : mumulei
     * @date :2018/12/16 16:59
     **/
    public static LocalDateTime stringToLocalDateTime(String dateStr) {

        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        Date date = parseDate(dateStr);

        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        return instant.atZone(zoneId).toLocalDateTime();
    }


    /**
     * @methodName long类型转LocalDateTime
     * @description long类型转LocalDateTime
     * @author : mumulei
     * @date :2019/3/14 15:52
     * @param : [time]
     * @return : java.time.LocalDateTime
     **/
    public static LocalDateTime longToLocalDateTime(Long time) {

        if(time == null){
            return null;
        }

        Date date = new Date(time);

        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        return instant.atZone(zoneId).toLocalDateTime();
    }


    /**
     * LocalDateTime 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(LocalDateTime date) {
        if (date == null) {
            return null;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormatter.format(date);
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 1、小于一小时用“XX分钟前”表示；2、小于24小时用“XX小时前”；3、小于5天（含）用“X天前”；4、大于5天用具体日期显示
     *
     * @param date
     * @return
     */
    public static String getPastTime(String date) {
        if (date != null) {
            date = date.replace("T", " ");
            date = date.replace("+00:00", "");
        }

        Date dateObj = parseDate(date);
        if (dateObj == null) {
            return getDate();
        }
        long t = new Date().getTime() - dateObj.getTime();
        if (t < 0) {
            return getDate();
        }
        if (t / (60 * 1000) < 60) {
            return t / (60 * 1000) + "分钟前";
        }
        if (t / (60 * 60 * 1000) >= 1 && t / (60 * 60 * 1000) < 24) {
            return t / (60 * 60 * 1000) + "小时前";
        }
        if (t / (24 * 60 * 60 * 1000) >= 1 && t / (24 * 60 * 60 * 1000) <= 5) {
            return t / (24 * 60 * 60 * 1000) + "天前";
        }
        return DateUtil.formatDate(dateObj, "yyyy-MM-dd");
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000D * 60D * 60D * 24D);
    }

    /**
     * 将时间变成"yyyy-MM-dd'T'HH:mm:ss'Z'"这种格式
     *
     * @param date
     * @return
     */
    public static String asUTCFormat(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(date);
    }

    /**
     * 将时间变成"yyyy-MM-dd'T'HH:mm:ss'Z'"这种格式
     *
     * @param milliSeconds
     * @return
     */
    public static String asUTCFormat(long milliSeconds) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date(milliSeconds));
    }

    public static Date addYears(Date date, int year) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, year);
            return calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取某天的23:59:59秒
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        Date endDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

        // 将毫米变为零，避免MySQL数据库对于毫秒大于500的数据进行进位。避免 23:59:59 变成 次日的 00:00:00
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(endDate);
        cal1.set(Calendar.MILLISECOND, 0);
        return cal1.getTime();
    }

    /**
     * 获得某天最小时间 2017-10-15 00:00:00
     *
     * @return
     */
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前时间点离当天结束剩余秒数
     */
    public static long getRemainSecondsOfToday() {
        Date currentDate = new Date();
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return seconds;
    }

    /**
     * @param : [minute]
     * @return : java.lang.String
     * @methodName 分钟转时间
     * @description 分钟转时间（天,时:分）
     **/
    public static String formatMinute(int minute) {
        int hour = minute / 60;
        String hourStr = hour > 9 ? "" + hour : "0" + hour;
        int min = minute % 60;
        String minStr = min > 9 ? "" + min : "0" + min;
        return hourStr + ":" + minStr;
    }


    /**
     * @param : [localDateTime]
     * @return : boolean
     * @methodName 判断是否今天
     * @description 判断是否今天
     * @author : mumulei
     * @date :2018/12/28 18:54
     **/
    public static boolean isToday(LocalDateTime localDateTime) {

        int thenYear = localDateTime.getYear();
        int thenMonth = localDateTime.getMonthValue();
        int thenMonthDay = localDateTime.getDayOfMonth();

        LocalDateTime now = LocalDateTime.now();
        return (thenYear == now.getYear())
                && (thenMonth == now.getMonthValue())
                && (thenMonthDay == now.getDayOfMonth());
    }


    /**
     * @param : [nowTime,beginTime, endTime]
     * @return : boolean
     * @methodName 判断时间是否在时间段内
     * @description 判断时间是否在时间段内
     * @author : mumulei
     * @date :2018/12/28 18:31
     **/
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param : [nowTime, endTime]
     * @return : boolean
     * @methodName 判断时间是否在时间前
     * @description 判断时间是否在时间前
     * @author : mumulei
     * @date :2018/12/28 18:31
     **/
    public static boolean beforeCalendar(Date nowTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将秒转换成分钟，不足一分钟取一分钟
     *
     * @param second 秒
     * @return minute 分钟
     */
    public static Integer getMinuteBySecond(Integer second) {
        if (null == second) return null;

        return (int) Math.ceil(second / (double) 60);
    }

    public static void main(String[] args) {

        Date date1 = parseDate("2018-12-27");

        Date date2 = parseDate("2018-12-28");

        Date date3 = parseDate("2018-12-29");

        LocalDateTime localDateTime1 = dateToLocalDateTime(date1);

        LocalDateTime localDateTime2 = dateToLocalDateTime(date2);

        LocalDateTime localDateTime3 = dateToLocalDateTime(date3);


        System.out.println(isToday(localDateTime1));

        System.out.println(isToday(localDateTime2));

        System.out.println(isToday(localDateTime3));

        /*System.out.println(formatMinute(1200));

        Date date = MyDateUtils.addMinutes(new Date(), 1);

        System.out.println(addDays(getStartOfDay(date), 1));

        System.out.println(addDays(getEndOfDay(date), -1));

        System.out.println(addMinutes(getStartOfDay(date), 10));

        System.out.println(addMinutes(getEndOfDay(date), -10));

        System.out.println(new Date().compareTo(date));*/
    }


    public static LocalDateTime Long2LocalDateTime(Long timstamp){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timstamp), ZoneId.systemDefault());
    }

    public static LocalDateTime getTodayStartLocalDateTime(){
        return Long2LocalDateTime(getTodayStartTime().getTime());
    }
    public static LocalDateTime getSomedayStartLocalDateTime(Date date){
        return Long2LocalDateTime(getSomedayStartTime(date).getTime());
    }

    public static LocalDateTime getTodayEndLocalDateTime(){
        return Long2LocalDateTime(getTodayEndTime().getTime());
    }
    public static LocalDateTime getSomedayEndLocalDateTime(Date date){
        return Long2LocalDateTime(getSomedayEndTime(date).getTime());
    }

    public static LocalDateTime getLocalDateTimeBackward(LocalDateTime fromTime, Integer backwardDays){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = fromTime.atZone(zoneId);
        Date fromDate = Date.from(zdt.toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        calendar.add(Calendar.DATE, backwardDays);
        return Long2LocalDateTime(calendar.getTimeInMillis());
    }



    /**
     * 获取当天的开始时间 00:00:00:00
     *
     * @return
     */
    public static Date getTodayStartTime() {
        return getSomedayStartTime(new Date());
    }
    /**
     * 获取某天的开始时间 00:00:00:00
     *
     * @return
     */
    public static Date getSomedayStartTime(Date date) {
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTime(date);
        dateStart.set(Calendar.HOUR_OF_DAY, 0);
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);
        return dateStart.getTime();
    }

    /**
     * 获取当天的结束时间 23:59:59:999
     *
     * @return
     */
    public static Date getTodayEndTime() {
        return getSomedayEndTime(new Date());
    }
    /**
     * 获取某天的结束时间 23:59:59:999
     *
     * @return
     */
    public static Date getSomedayEndTime(Date date) {
        Calendar dateEnd = Calendar.getInstance();
        dateEnd.setTime(date);
        dateEnd.set(Calendar.HOUR_OF_DAY, 23);
        dateEnd.set(Calendar.MINUTE, 59);
        dateEnd.set(Calendar.SECOND, 59);
        return dateEnd.getTime();
    }



}
