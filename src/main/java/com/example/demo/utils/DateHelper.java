package com.example.demo.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateHelper {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_FORMAT2 = "yyyy/MM/dd";
    public static final String DEFAULT_DATE_FORMATTWO = "yyyy.MM.dd";
    public static final String DEFAULT_EN_DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_DDMMYYYY = "dd-MM-yyyy";
    public static final String DATE_FORMAT_DDMMMYYYY = "dd-MMM-yyyy";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YYMMDDHHMM = "YY-MM-dd HH:mm";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS2 = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_FORMAT_HHMM = "HH:mm";
    public static final String DATE_FORMAT_YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_DDMMYYYYHHMMSS = "dd/MM/yy HH:mm:ss";
    public static final String DATE_FORMAT_YYYYMMDDHHmmss = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_YYYYMMDDHHmm = "yyyyMMddHHmm";
    public static final String DATE_FORMAT_EN_YYYYMMDDHHMM = "dd/MM/yyyy HH:mm";
    public static final String DATE_FORMAT_EN_YYYYMMDDTHHMMSSZ = "yyyy-MM-dd'T'HH:mm:ss";

    private static final String[] DAY_WEEK_NAME = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    public static final String DATE_FORMAT_EN_MMDDHHMM = "MM/dd HH:mm";


    public static final String DATE_FORMAT_LOGIC_TIMESTAMP = "yyyyMMddHHmmssSSS";
    public static final String DATE_FORMAT_LOGIC_TIMESTAMP_WITHOUT_MSEC = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_YYMMDDHHMMSS_MSEC = "yyMMddHHmmss";
    // RFC 822 Date Format
    private static final String RFC822_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";

    public static final String DATE_FORMAT_STRING_9 = "MM月dd日 HH:mm";
    public static final String DATE_FORMAT_STRING_11 = "MM月dd日";
    public static final String DATE_FORMAT_STRING_10 = "hh:mm";


    public static boolean isValidDate(String dateStr, String dateFmt) {
        try {
            Date date = convertDate(Locale.CHINA, dateStr, dateFmt);

            if (date == null) {
                return false;
            }
            return true;
        } catch (Exception exception) {
            return false;
        }
    }


    public static Date convertDate(Locale locale, String dateStr, String dateFmt) {
        try {
            if (locale == null) {
                locale = Locale.getDefault();
            }
            SimpleDateFormat dateFmter = new SimpleDateFormat(dateFmt, locale);
            return dateFmter.parse(dateStr);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static DateTime convertDateTime(Locale locale, String dateStr, String dateFmt) {
        try {
            if (locale == null) {
                locale = Locale.getDefault();
            }
            DateTimeFormatter dateTimeFmter = DateTimeFormat.forPattern(dateFmt).withLocale(locale);
            return DateTime.parse(dateStr, dateTimeFmter);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static String convertDateTimeToString(DateTime inDate, String dateFmt) {
        if (inDate == null) return "";

        DateTimeFormatter dateTimeFmter = DateTimeFormat.forPattern(dateFmt);


        return inDate.toString(dateTimeFmter);
    }


    public static String convertDateToString(Date inDate) {
        if (inDate == null) return "";

        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

        return df.format(inDate);
    }


    public static String convertDateToString(Date inDate, String dateFormat) {
        String resultValue = "";

        try {
            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            resultValue = df.format(inDate);
        } catch (Exception e) {
            return null;
        }

        return resultValue;
    }


    public static String formatDate(Locale locale, Date date, String dateFmt) {
        try {
            SimpleDateFormat dateFmter = new SimpleDateFormat(dateFmt, locale);
            return dateFmter.format(date);
        } catch (Exception exception) {
            return null;
        }
    }


    public static String formatDate(Date date, String dateFormat) {
        return formatDate(Locale.CHINA, date, dateFormat);
    }


    public static String formatDate(long millisecond, String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);
        return formatDate(null, calendar.getTime(), dateFormat);
    }


    public static int compareDate(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.compareTo(calendar2);
    }


    /**
     * validate date overlap.
     *
     * @return if overlap will return 1,else return 0
     * @throws
     * @author WangLiming
     */
    public static int dateOverlap(Date startDate1, Date endDate1,
                                  Date startDate2, Date endDate2) {
        if (startDate1.compareTo(endDate2) > 0
                || endDate1.compareTo(startDate2) < 0) {
            return 0;
        }
        return 1;
    }


    public static long getCurrentTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getTimeInMillis();
    }


    public static Date getLastTimeOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    public static Date getFirstTimeOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 000);
        return calendar.getTime();
    }


    public static Date getFirstDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 01);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 000);
        return calendar.getTime();
    }


    public static Date addYearToDate(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }


    public static Date addMonthToDate(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }


    public static Date addDayToDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }


    public static Date addHourToDate(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }


    public static Date addMinuteToDate(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }


    public static Date addSecondToDate(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static Date addTimeToDate(Date date, String time) {
        String dateTime = DateHelper.convertDateToString(date, DEFAULT_DATE_FORMAT) + " " + time;
        return DateHelper.convertDate(dateTime, DATE_FORMAT_YYYYMMDDHHMMSS);
    }

    /**
     * TODO To describe the functionality of this method.
     *
     * @return
     * @throws
     * @author liyue
     */
    public static int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }


    public static int getMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }


    public static int getDayFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }


    public static int getHourFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR);
    }


    public static int getWeekDayFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.DAY_OF_WEEK) - 1) < 0 ? 0 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static String getWeekDayNameFromDate(Date date) {
        int index = getWeekDayFromDate(date);
        if (index < 0) {
            index = 0;
        }
        return DAY_WEEK_NAME[index];
    }

    public static int getDaysBetween(Date d1, Date d2) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(d1);
        Calendar instance1 = Calendar.getInstance();
        instance1.setTime(d2);
        return getDaysBetween(instance, instance1);
    }

    public static int getWeekDayNow() {
        Calendar now = Calendar.getInstance();
        //一周第一天是否为星期天
        boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
        //获取周几
        int weekDay = now.get(Calendar.DAY_OF_WEEK);
        //若一周第一天星期天，则-1
        if (isFirstSunday) {
            weekDay = weekDay - 1;
            if (weekDay == 0) {
                weekDay = 7;
            }
        }
        return weekDay;
    }

    /**
     * get days between startTime and secondTime.
     *
     * @return int
     * @throws
     * @author hxc
     */
    public static int getDaysBetween(Calendar d1,
                                     Calendar d2) {
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR)
                - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                d1.add(Calendar.YEAR, 1);
            }
            while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }


    /**
     * get the date list between two days, includes these two days.
     *
     * @return List
     * @throws
     * @author hxc
     */
    public static List<Date> getDaysListBetween(Calendar d1,
                                                Calendar d2) {
        List<Date> days = new ArrayList<Date>();
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        d1 = (Calendar) d1.clone();
        do {
            days.add(d1.getTime());
            d1.add(Calendar.DAY_OF_YEAR, 1);
        }
        while (d1.get(Calendar.YEAR) != d1
                .get(Calendar.YEAR)
                || d1.get(Calendar.DAY_OF_YEAR) != (d2
                .get(Calendar.DAY_OF_YEAR) + 1));
        return days;
    }


    public static int getMinuteFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }


    public static int getSecondFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }


    public static Date convertDate(String dateStr, String dateFmt) {
        return convertDate(Locale.CHINA, dateStr, dateFmt);
    }


    public static int getDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }


    // format:yyyy-mm-dd 00:00:00
    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date paramCurrDate = calendar.getTime();
        return paramCurrDate;
    }

    // 上个月
    public static Date getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date paramCurrDate = cal.getTime();
        return paramCurrDate;
    }

    // format:yyyy-mm-dd hh:mm:ss
    public static Date getCurrentDateTime() {
//        Calendar calendar = Calendar.getInstance();
        //        calendar.setTime(new Date());
        //        //calendar.set(Calendar.HOUR_OF_DAY, 0);
        //        //calendar.set(Calendar.MINUTE, 0);
        //        //calendar.set(Calendar.SECOND, 0);
        //        //calendar.set(Calendar.MILLISECOND, 0);
        //        Date paramCurrDate = calendar.getTime();
        return new Date(System.currentTimeMillis());
    }


    public static Long convertSecondsToMillisecond(Long seconds) {
        return seconds * 1000;
    }


    public static Long convertMillisecondToSeconds(Long millisecond) {
        return millisecond / 1000;
    }

    //将秒数转换成Date类型
    public static Date convertSecondsToDate(Long seconds) {
        return new Date(convertSecondsToMillisecond(seconds));
    }

    /**
     * 精确计算年龄
     *
     * @param birthday 初始日期
     * @return 年龄
     * @author liub
     */
    public static Integer getAge(Date birthday) {
        Calendar cal = Calendar.getInstance();
        if (CodeHelper.isNull(birthday) || cal.before(birthday)) return 0;

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                //monthNow<monthBirth
                age--;
            }
        }

        return age;
    }

    /**
     * 根据设置的年月日来获取日期.
     *
     * @param year  年
     * @param month 月
     * @param date  日期
     * @return
     * @author liub
     */
    public static Date getDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, date);
        return calendar.getTime();
    }

    public static void main(String[] args) throws InterruptedException {
        Date date1 = convertDate("2017/4/6 15:09:58", DATE_FORMAT_YYYYMMDDHHMMSS2);

        // Date date1 = new Date();
        // Thread.sleep(5500);
        // Date date2 = new Date();

        // System.out.println(new BigDecimal(date2.getTime()).subtract(
        // new BigDecimal(date1.getTime())).divide(
        // new BigDecimal(1000)).intValue());

        // System.out.println(formatDate(date1,"yyyy/MM/dd HH:mm:ss"));
        //
        // System.out.println(compareDate(date1, date2));
        // System.out.println(compareDate(date2, date2));
        // System.out.println(compareDate(date2, date1));
        // System.out.println(getFirstDateOfMonth(new Date()));
        //
        // System.out.println(formatDate(addMonthToDate(date1,1),"yyyy/MM/dd HH:mm:ss"));
        // System.out.println(formatDate(addDayToDate(date1,1),"yyyy/MM/dd HH:mm:ss"));
        // System.out.println(formatDate(addHourToDate(date1,1),"yyyy/MM/dd HH:mm:ss"));
        // System.out.println(formatDate(addMinuteToDate(date1,1),"yyyy/MM/dd HH:mm:ss"));
        // System.out.println(formatDate(addSecondToDate(date1,1),"yyyy/MM/dd HH:mm:ss"));
        //
        // System.out.println(getYearFromDate(date1));
        // System.out.println(getMonthFromDate(date1));
        // System.out.println(getDayFromDate(date1));
        // System.out.println(getHourFromDate(date1));
        // System.out.println(getMinuteFromDate(date1));
        // System.out.println(getSecondFromDate(date1));
        //
        // Calendar cal = Calendar.getInstance();
        // cal.setTime(date1);
        // Calendar cal3 = (Calendar) cal.clone();
        // Calendar cal4 = (Calendar) cal.clone();
        // cal.add(Calendar.DAY_OF_YEAR, 10);
        // Calendar cal2 = Calendar.getInstance();
        // cal2.setTime(date2);
        // cal3.add(Calendar.DAY_OF_YEAR, 2);
        // cal4.add(Calendar.DAY_OF_YEAR, 4);
        // System.out.println(date1);
        // System.out.println(cal.getTime());
        // System.out.println(getDaysListBetween(cal, cal3).contains(
        // cal3.getTime()));
        // System.out.println(getDaysListBetween(cal, cal3)
        // .contains(cal.getTime()));
        // System.out.println(getDaysListBetween(cal, cal3).contains(
        // cal4.getTime()));
        // System.out.println(compareDate(date1, date2));
        // System.out.println(compareDate(date2, date2));
        // System.out.println(compareDate(date2, date1));
        // System.out.println(getFirstDateOfMonth(new Date()));
        //
        // Date date3 = new Date();
        // Date date4 = DateHelper.addDayToDate(date3,2);
        //
        // System.out.println(getRegionDates(date3,date4));
        // System.out.println(convertDateToString(getDate(2099, 11, 31)));
        // System.out.println(getAge(getDate(1983, 4, 18)));
        // System.out.println(getLastMonthFirstDate(getDate(2015, 1, 1)));
//        System.out.println(getCurrentMonthFirstDate(null));
        System.out.println(DateHelper.formatDate(date1, DATE_FORMAT_YYYYMMDDHHMMSS));

        // 获取Calendar
        Calendar calendar = Calendar.getInstance();
        // 设置时间,当前时间不用设置
        // calendar.setTime(new Date());
        // 设置日期为本月最大日期
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        // 打印
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = getFirstDateOfMonth(Calendar.getInstance().getTime());
        System.out.println(format.format(date));
        Date date2 = addMonthToDate(date, 1);
        System.out.println(format.format(date2));
        System.out.println(format.format(calendar.getTime()));
    }

    public static Date getFirstDateOfWeek() {
        int mondayPlus;
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            mondayPlus = 0;
        } else {
            mondayPlus = 1 - dayOfWeek;
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        currentDate.set(Calendar.HOUR_OF_DAY, 00);
        currentDate.set(Calendar.MINUTE, 00);
        currentDate.set(Calendar.SECOND, 00);
        currentDate.set(Calendar.MILLISECOND, 000);
        Date monday = currentDate.getTime();
        return monday;
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     * 获取日期对应的上个月第一天
     *
     * @param date 日期
     * @return
     */
    public static Date getLastMonthFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        if (date == null)
            date = getCurrentDate();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month--;
        if (month < 0) {
            year--;
            month = 11;
        }
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取日期对应的本月第一天
     *
     * @param date 日期
     * @return
     */
    public static Date getCurrentMonthFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        if (date == null)
            date = getCurrentDate();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 根据时间对象按规则转化成相应的字符返回前端展示
     *
     * @param date
     * @return
     * @autor zhangyif
     */
    public static String switchTimeToDate(Date date) {
        /* 规则：今天、明天、后天 */
        DateTime showStartTime = new DateTime(date);
        String day = "";
        if (showStartTime.withTimeAtStartOfDay().getMillis() == new DateTime().withTimeAtStartOfDay().getMillis()) {
            day = "今天";
        } else if (showStartTime.withTimeAtStartOfDay().getMillis() == new DateTime().plusDays(1).withTimeAtStartOfDay().getMillis()) {
            day = "明天";
        } else if (showStartTime.withTimeAtStartOfDay().getMillis() == new DateTime().plusDays(2).withTimeAtStartOfDay().getMillis()) {
            day = "后天";
        } else {
//            day = new SimpleDateFormat("MM月dd日").format(date);
            day = getWeekOfDate(date);

        }
        return day;
    }


    /**
     * 将时间段数字转化为上午、下午、晚上
     *
     * @param reserveInterval
     * @return
     * @autor zhangyif
     */
    public static String switchDateToIntervalTime(String reserveInterval) {
        String str = "";
        switch (reserveInterval) {
            case "1":
                str = "上午";
                break;
            case "2":
                str = "下午";
                break;
            case "3":
                str = "晚上";
                break;
        }
        return str;
    }


    /**
     * Formats Date to GMT string.
     */
    public static String formatRfc822Date(Date date) {
        return getRfc822DateFormat().format(date);
    }

    private static DateFormat getRfc822DateFormat() {
        SimpleDateFormat rfc822DateFormat =
                new SimpleDateFormat(RFC822_DATE_FORMAT, Locale.US);
        rfc822DateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));

        return rfc822DateFormat;
    }

    /**
     * 比较两个日期相差的天数，忽略时分秒
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 返回相差天数
     */
    public static int compareDateOffsetDay(Date start, Date end) {
        Calendar c1 = new GregorianCalendar();
        c1.setTime(start);
        Calendar c2 = new GregorianCalendar();
        c2.setTime(end);
        Calendar newStart = new GregorianCalendar();
        newStart.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH));
        Calendar newEnd = new GregorianCalendar();
        newEnd.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH));
        long offset = (newEnd.getTimeInMillis() - newStart.getTimeInMillis()) / (1000 * 60 * 60 * 24);
        //避免跨年时间计算错误
        //  c1.get(Calendar.DAY_OF_YEAR) -  c2.get(Calendar.DAY_OF_YEAR);
        return new BigDecimal(offset).intValue();
    }

    /**
     * 解析日期 yyyy-MM-dd
     *
     * @param object 日期字符串
     * @return
     */
    public static Timestamp parse(Object object) {
        if (object instanceof Date) {
            return new Timestamp(((Date) object).getTime());
        }
        if (object == null)
            return null;
        String date = object.toString();
        try {
            if (date.length() == 10) {
                DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
                Date d = yyyyMMdd.parse(date);
                return new Timestamp(d.getTime());
            } else if (date.length() == 8) {
                DateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
                Date d = yyyyMMdd.parse(date);
                return new Timestamp(d.getTime());
            } else if (date.length() == 9) {
                if (date.matches("\\d{4}-\\d-\\d{2}")) {//yyyy-M-dd
                    DateFormat yyyyMdd = new SimpleDateFormat("yyyy-M-dd");
                    Date d = yyyyMdd.parse(date);
                    return new Timestamp(d.getTime());
                } else if (date.matches("\\d{4}-\\d{2}-\\d")) {//yyyy-MM-d
                    DateFormat yyyyMdd = new SimpleDateFormat("yyyy-MM-d");
                    Date d = yyyyMdd.parse(date);
                    return new Timestamp(d.getTime());
                }
            } else if (date.length() == 16) {//yyyy-MM-dd HH:mm
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date d = format.parse(date);
                return new Timestamp(d.getTime());
            } else if (date.length() == 19) {//yyyy-MM-dd HH:mm:ss
                DateFormat yyyyMdd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = yyyyMdd.parse(date);
                return new Timestamp(d.getTime());
            } else if (date.length() >= 20 && date.length() <= 23) {//yyyy-MM-dd HH:mm:ss.SSS
                if (date.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\.\\d{1,3}")) {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    Date d = format.parse(date);
                    return new Timestamp(d.getTime());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    public static String getWeekOfDate2(Date date) {
        String[] weekOfDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /*
* 毫秒转化时分秒毫秒
*/
    public static Map<String, Object> formatTime(Long ms) {
        if (CodeHelper.isNull(ms)) {
            ms = 0L;
        }
        Map<String, Object> returnMap = new HashMap<>();
        String averagetag = "";
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
//        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (ms > 0 && hour < 1) {
            averagetag = "回复快";
        }
        if (hour < 1 && minute > 0 && second >= 0) {
            sb.append(minute + "分").append(second + "秒");

        } else if (hour >= 1 && minute >= 0) {
            sb.append(hour + "小时").append(minute + "分");
        } else if (minute <= 0 && second > 0) {
            sb.append(second + "秒");
        }

        if (sb.toString().length() > 0) {
            sb.append("回复");
        }


//        if(milliSecond > 0) {
//            sb.append(milliSecond+"毫秒");
//        }
        returnMap.put("averagetag", averagetag);
        returnMap.put("averagetime", sb.toString());
        return returnMap;
    }

    /**
     * 把秒数转换成时分秒的形式显示
     *
     * @param secend
     * @return
     */
    public static String convertTimeString(int secend) {
        int hours = secend / (60 * 60);
        int c = secend % (60 * 60);
        int minutes = c / 60;
        int sed = c % 60;
        StringBuilder sb = new StringBuilder();
        sb.append(hours);
        sb.append(":");
        sb.append(minutes);
        sb.append(":");
        sb.append(sed);
        return sb.toString();
    }


}
