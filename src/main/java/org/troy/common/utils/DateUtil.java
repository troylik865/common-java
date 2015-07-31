package org.troy.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.util.Assert;

public class DateUtil {

    public static final String PATTERN_STANDARD = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_DATE     = "yyyy-MM-dd";

    public static String timestamp2String(Timestamp timestamp, String pattern) {
        if (timestamp == null) {
            throw new java.lang.IllegalArgumentException("timestamp null illegal");
        }
        if (pattern == null || pattern.equals("")) {
            pattern = PATTERN_STANDARD;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(timestamp.getTime()));
    }

    public static String date2String(java.util.Date date, String pattern) {
        if (date == null) {
            throw new java.lang.IllegalArgumentException("timestamp null illegal");
        }
        if (pattern == null || pattern.equals("")) {
            pattern = PATTERN_STANDARD;
            ;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Timestamp currentTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    public static String currentTimestamp2String(String pattern) {
        return timestamp2String(currentTimestamp(), pattern);
    }

    public static Timestamp string2Timestamp(String strDateTime, String pattern) {
        if (strDateTime == null || strDateTime.equals("")) {
            throw new java.lang.IllegalArgumentException("Date Time Null Illegal");
        }
        if (pattern == null || pattern.equals("")) {
            pattern = PATTERN_STANDARD;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(strDateTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new Timestamp(date.getTime());
    }

    public static Date string2Date(String strDate, String pattern) {
        if (strDate == null || strDate.equals("")) {
            throw new RuntimeException("str date null");
        }
        if (pattern == null || pattern.equals("")) {
            pattern = DateUtil.PATTERN_DATE;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;

        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public static String stringToYear(String strDest) {
        if (strDest == null || strDest.equals("")) {
            throw new java.lang.IllegalArgumentException("str dest null");
        }

        Date date = string2Date(strDest, DateUtil.PATTERN_DATE);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return String.valueOf(c.get(Calendar.YEAR));
    }

    public static String stringToMonth(String strDest) {
        if (strDest == null || strDest.equals("")) {
            throw new java.lang.IllegalArgumentException("str dest null");
        }

        Date date = string2Date(strDest, DateUtil.PATTERN_DATE);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // return String.valueOf(c.get(Calendar.MONTH));
        int month = c.get(Calendar.MONTH);
        month = month + 1;
        if (month < 10) {
            return "0" + month;
        }
        return String.valueOf(month);
    }

    public static String stringToDay(String strDest) {
        if (strDest == null || strDest.equals("")) {
            throw new java.lang.IllegalArgumentException("str dest null");
        }

        Date date = string2Date(strDest, DateUtil.PATTERN_DATE);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // return String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            return "0" + day;
        }
        return "" + day;
    }

    public static Date getFirstDayOfMonth(Calendar c) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = 1;
        c.set(year, month, day, 0, 0, 0);
        return c.getTime();
    }

    public static Date getLastDayOfMonth(Calendar c) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = 1;
        if (month > 11) {
            month = 0;
            year = year + 1;
        }
        c.set(year, month, day - 1, 0, 0, 0);
        return c.getTime();
    }

    public static String date2GregorianCalendarString(Date date) {
        if (date == null) {
            throw new java.lang.IllegalArgumentException("Date is null");
        }
        long tmp = date.getTime();
        GregorianCalendar ca = new GregorianCalendar();
        ca.setTimeInMillis(tmp);
        try {
            XMLGregorianCalendar t_XMLGregorianCalendar = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(ca);
            return t_XMLGregorianCalendar.normalize().toString();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            throw new java.lang.IllegalArgumentException("Date is null");
        }

    }

    public static boolean compareDate(Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null) {
            throw new java.lang.RuntimeException();
        }

        String strFirstDate = date2String(firstDate, "yyyy-MM-dd");
        String strSecondDate = date2String(secondDate, "yyyy-MM-dd");
        if (strFirstDate.equals(strSecondDate)) {
            return true;
        }
        return false;
    }

    public static Date getStartTimeOfDate(Date currentDate) {
        Assert.notNull(currentDate);
        String strDateTime = date2String(currentDate, "yyyy-MM-dd") + " 00:00:00";
        return string2Date(strDateTime, "yyyy-MM-dd hh:mm:ss");
    }

    public static Date getEndTimeOfDate(Date currentDate) {
        Assert.notNull(currentDate);
        String strDateTime = date2String(currentDate, "yyyy-MM-dd") + " 59:59:59";
        return string2Date(strDateTime, "yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 获取指定年份和月份对应的天数
     * 
     * @param year
     *            指定的年份
     * @param month
     *            指定的月份
     * @return int 返回天数
     */
    public static int getDaysInMonth(int year, int month) {
        if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8)
            || (month == 10) || (month == 12)) {
            return 31;
        } else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
            return 30;
        } else {
            if (((year % 4) == 0) && ((year % 100) != 0) || ((year % 400) == 0)) {
                return 29;
            } else {
                return 28;
            }
        }
    }

    /**
     * 根据所给的起止时间来计算间隔的天数
     * 
     * @param startDate
     *            起始时间
     * @param endDate
     *            结束时间
     * @return int 返回间隔天数
     */
    public static int getIntervalDays(java.sql.Date startDate, java.sql.Date endDate) {
        long startdate = startDate.getTime();
        long enddate = endDate.getTime();
        long interval = enddate - startdate;
        int intervalday = (int) (interval / (1000 * 60 * 60 * 24));
        return intervalday;
    }

    /**
     * 根据所给的起止时间来计算间隔的月数
     * 
     * @param startDate
     *            起始时间
     * @param endDate
     *            结束时间
     * @return int 返回间隔月数
     */
    @SuppressWarnings("static-access")
    public static int getIntervalMonths(java.sql.Date startDate, java.sql.Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int startDateM = startCal.MONTH;
        int startDateY = startCal.YEAR;
        int enddatem = endCal.MONTH;
        int enddatey = endCal.YEAR;
        int interval = (enddatey * 12 + enddatem) - (startDateY * 12 + startDateM);
        return interval;
    }

    /**
     * 返回当前日期时间字符串<br>
     * 默认格式:yyyy-mm-dd hh:mm:ss
     * 
     * @return String 返回当前字符串型日期时间
     */
    public static String getCurrentTime() {
        String returnStr = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        returnStr = f.format(date);
        return returnStr;
    }

    /**
     * 返回当前日期时间字符串<br>
     * 默认格式:yyyy-mm-dd hh:mm:ss
     * 
     * @return String 返回当前字符串型日期时间
     */
    public static String getCurrentTime(String pattern) {
        String returnStr = null;
        if (pattern == null || pattern.equals("")) {
            pattern = DateUtil.PATTERN_STANDARD;
        }
        SimpleDateFormat f = new SimpleDateFormat(pattern);
        Date date = new Date();
        returnStr = f.format(date);
        return returnStr;
    }

    /**
     * 返回当前日期字符串<br>
     * 默认格式:yyyy-mm-dd
     * 
     * @return String 返回当前字符串型日期
     */
    public static String getCurrentDate() {
        String returnStr = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        returnStr = f.format(date);
        return returnStr;
    }

    /**
     * 返回当前日期字符串<br>
     * 默认格式:yyyy-mm-dd
     * 
     * @return String 返回当前字符串型日期
     */
    public static String getCurrentDate(String pattern) {
        String returnStr = null;
        SimpleDateFormat f = new SimpleDateFormat(pattern);
        Date date = new Date();
        returnStr = f.format(date);
        return returnStr;
    }

    /**
     * 返回当前时间
     * 
     * @return
     */
    public static Date getCurrDate() {
        return new Date();
    }

    /**
     * 日期相加减
     * @param inputDate
     * @param i
     * @return
     */
    public static String processDateAddDay(String inputDate, int i) {
        if (inputDate == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(string2Date(inputDate, ""));
            calendar.add(5, i);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 年份相加减
     * @param inputDate
     * @param i
     * @return
     */
    public static String processDateAddMonth(String inputDate, int i) {
        if (inputDate == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(string2Date(inputDate, ""));
            calendar.add(2, i);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 获取给定当前日期下一个月日期
     * @param startDateStr
     * @return
     */
    public static Date getAfterMonthDate(String startDateStr) {
        if (StringUtil.isEmpty(startDateStr))
            return null;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(string2Date(startDateStr, "yyyy-MM-dd"));
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    /***
     * 获取时间段之间天数
     * @param begintime
     * @param endtime
     * @return
     */
    public static long getDaycount(String begintime, String endtime) {
        long dayCount = 0L;
        if (StringUtil.isEmpty(begintime) || StringUtil.isEmpty(endtime))
            return dayCount;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        ParsePosition pos1 = new ParsePosition(0);
        Date dt1 = formatter.parse(begintime, pos);
        Date dt2 = formatter.parse(endtime, pos1);
        dayCount = dt2.getTime() - dt1.getTime();
        dayCount = dayCount / (1000 * 60 * 60 * 24) + 1;
        return dayCount;
    }

    /**
     * 获取指定日期的后一天
     * 
     * @param date
     * @return
     */
    public static Date getNextDate(Date date) {
        if (null == date) {
            return date;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date getBeforeDate(Date date) {
        if (null == date) {
            return date;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 根据输入的日期字符串 和 提前天数 ，
     * 获得当前日期提前几天的日期对象
     * @param dateString 日期对象 ，格式如 1-31-1900
     * @param lazyDays 倒推的天数
     * @return 指定日期倒推指定天数后的日期对象
     * @throws ParseException 
     */
    public static Date getBeforeDate(int beforeDays) {
        Date currDate = getNowDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear - beforeDays);
        return cal.getTime();
    }

    public static void main(String[] args) {
        String str1 = "2011-01-01";
        String str2 = "1988-09-09";
        Date date1 = DateUtil.string2Date(str1, "yyyy-MM-dd");
        Date date2 = DateUtil.string2Date(str2, "yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        c2.add(Calendar.YEAR, 4);
        if (c2.before(c1)) {
            System.out.println("illegal");
        } else {
            System.out.println("ok");
        }

    }

}