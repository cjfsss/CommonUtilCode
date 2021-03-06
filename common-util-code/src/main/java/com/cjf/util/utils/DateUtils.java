package com.cjf.util.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * <p>Title: DateUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/3/26 17:29
 */
public class DateUtils {

    /**
     * 时间日期格式化到年月日时分秒.
     */
    public static final String yyyyMMddHHmmssSSS = "yyyyMMdd_HHmmssSSS";

    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    /**
     * 时间日期格式化到年月日时分秒.
     */
    public static final String yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间日期格式化到年月日.
     */
    public static final String yyyyMMdd = "yyyy-MM-dd";

    /**
     * 时间日期格式化到年月.
     */
    public static final String yyMM = "yyyy-MM";

    /**
     * 时间日期格式化到年月日时分.
     */
    public static final String yyyyMMdd_HHmm = "yyyy-MM-dd HH:mm";

    /**
     * 时间日期格式化到月日.
     */
    public static final String MMdd = "MM/dd";

    /**
     * 时分秒.
     */
    public static final String HHmmss = "HH:mm:ss";

    /**
     * 时分.
     */
    public static final String HHmm = "HH:mm";

    /**
     * 上午.
     */
    public static final String AM = "AM";

    /**
     * 下午.
     */
    public static final String PM = "PM";

    /***
     * 得到MM-dd时间格式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    @NonNull
    public static SimpleDateFormat getMMDD() {
        return new SimpleDateFormat("MM-dd");
    }

    /***
     * 得到yyyy-MM-dd时间格式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    @NonNull
    public static SimpleDateFormat getYYYYMMDD() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    /***
     * 得到yyyy-MM-dd hh:mm:ss时间格式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    @NonNull
    public static SimpleDateFormat getYYYYMMDDHHMMSS() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    }

    /***
     * 得到yyyy-MM-dd hh:mm时间格式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    @NonNull
    public static SimpleDateFormat getYYYYMMDDHHMM() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm");
    }

    /***
     * 得到MM-dd hh:mm时间格式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    @NonNull
    public static SimpleDateFormat getMMDDHHMM() {
        return new SimpleDateFormat("MM-dd hh:mm");
    }

    /***
     * 得到MM-dd hh:mm时间格式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    @NonNull
    public static SimpleDateFormat getHHMM() {
        return new SimpleDateFormat("HH:mm");
    }

    /**
     * 根据两个日期A、B计算AB之间的天数
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 大于0，time1 > time2
     */
    public static long getQuot(@NonNull String time1, @NonNull String time2) {
        long quot = 0;
        SimpleDateFormat ft = getYYYYMMDD();
        try {
            Date date1 = ft.parse(time1);
            Date date2 = ft.parse(time2);
            if (date1 == null || date2 == null) {
                return quot;
            }
            return getQuot(date1.getTime(), date2.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quot;
    }

    /**
     * 根据两个日期A、B计算AB之间的天数
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 大于0，time1 > time2
     */
    public static long getQuot(long time1, long time2) {
        long quot = 0;
        try {
            quot = time1 - time2;
            quot = quot / 1000 / 60 / 60 / 24;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quot;
    }

    /**
     * 描述：String类型的日期时间转化为Date类型.
     *
     * @param strDate String形式的日期时间
     * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return Date Date类型日期时间
     */
    public static Date getDateByFormat(String strDate, String format) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = mSimpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 描述：获取偏移之后的Date.
     *
     * @param date          日期时间
     * @param calendarField Calendar属性，对应offset的值，
     *                      如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return Date 偏移之后的日期时间
     */
    public static Date getDateByOffset(Date date, int calendarField, int offset) {
        Calendar c = new GregorianCalendar();
        try {
            c.setTime(date);
            c.add(calendarField, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 描述：获取指定日期时间的字符串(可偏移).
     *
     * @param strDate       String形式的日期时间
     * @param format        格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar属性，对应offset的值，
     *                      如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return String String类型的日期时间
     */
    public static String getStringByOffset(String strDate, String format, int calendarField, int offset) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(mSimpleDateFormat.parse(strDate));
            c.add(calendarField, offset);
            mDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * 描述：Date类型转化为String类型(可偏移).
     *
     * @param date          the date
     * @param format        the format
     * @param calendarField the calendar field
     * @param offset        the offset
     * @return String String类型日期时间
     */
    public static String getStringByOffset(Date date, String format, int calendarField, int offset) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(date);
            c.add(calendarField, offset);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取指定日期时间的字符串,用于导出想要的格式.
     *
     * @param strDate String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
     * @param format  输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     */
    public static String getStringByFormat(String strDate, String format) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(yyyyMMdd_HHmmss);
            c.setTime(mSimpleDateFormat.parse(strDate));
            SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
            mDateTime = mSimpleDateFormat2.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * 描述：Date类型转化为String类型.
     *
     * @param date   the date
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getStringByFormat(Date date, String format) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        String strDate = null;
        try {
            strDate = mSimpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getCurrentYear() {
        return new GregorianCalendar().get(Calendar.YEAR) - 1900;
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        return new GregorianCalendar().get(Calendar.MONTH);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static int getCurrentDate() {
        return new GregorianCalendar().get(Calendar.DATE);
    }

    /**
     * 描述：获取表示当前日期时间的字符串(可偏移).
     *
     * @param format        格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar属性，对应offset的值，
     *                      如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return String String类型的日期时间
     */
    public static String getCurrentDateByOffset(String format, int calendarField, int offset) {
        String mDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            c.add(calendarField, offset);
            mDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;

    }

    /**
     * 描述：计算两个日期所差的天数.
     *
     * @param milliseconds1 the milliseconds1
     * @param milliseconds2 the milliseconds2
     * @return int 所差的天数
     */
    public static int getOffectDay(long milliseconds1, long milliseconds2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(milliseconds1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(milliseconds2);
        // 先判断是否同年
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);
        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays = 0;
        int day = 0;
        if (y1 - y2 > 0) {
            maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 + maxDays;
        } else if (y1 - y2 < 0) {
            maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 - maxDays;
        } else {
            day = d1 - d2;
        }
        return day;
    }

    /**
     * 描述：计算两个日期所差的小时数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的小时数
     */
    public static int getOffectHour(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int h = 0;
        int day = getOffectDay(date1, date2);
        h = h1 - h2 + day * 24;
        return h;
    }

    /**
     * 描述：计算两个日期所差的分钟数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的分钟数
     */
    public static int getOffectMinutes(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int m1 = calendar1.get(Calendar.MINUTE);
        int m2 = calendar2.get(Calendar.MINUTE);
        int h = getOffectHour(date1, date2);
        int m = 0;
        m = m1 - m2 + h * 60;
        return m;
    }

    /**
     * 描述：获取本周一.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.MONDAY);
    }

    /**
     * 描述：获取本周日.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getLastDayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.SUNDAY);
    }

    /**
     * 描述：获取本周的某一天.
     *
     * @param format        the format
     * @param calendarField the calendar field
     * @return String String类型日期时间
     */
    private static String getDayOfWeek(String format, int calendarField) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            int week = c.get(Calendar.DAY_OF_WEEK);
            if (week == calendarField) {
                strDate = mSimpleDateFormat.format(c.getTime());
            } else {
                int offectDay = calendarField - week;
                if (calendarField == Calendar.SUNDAY) {
                    offectDay = 7 - Math.abs(offectDay);
                }
                c.add(Calendar.DATE, offectDay);
                strDate = mSimpleDateFormat.format(c.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取本月第一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            // 当前月的第一天
            c.set(GregorianCalendar.DAY_OF_MONTH, 1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;

    }

    /**
     * 描述：获取本月最后一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getLastDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            // 当前月的最后一天
            c.set(Calendar.DATE, 1);
            c.roll(Calendar.DATE, -1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：判断是否是闰年()
     * <p>
     * (year能被4整除 并且 不能被100整除) 或者 year能被400整除,则该年为闰年.
     *
     * @param year 年代（如2012）
     * @return boolean 是否为闰年
     */
    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 400 != 0) || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 描述：根据时间返回格式化后的时间的描述. 小于1小时显示多少分钟前 大于1小时显示今天＋实际日期，大于今天全部显示实际时间
     *
     * @param strDate   the str date
     * @param outFormat the out format
     * @return the string
     */
    public static String formatDateStr2Desc(String strDate, String outFormat) {

        DateFormat df = getYYYYMMDDHHMMSS();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c2.setTime(df.parse(strDate));
            c1.setTime(new Date());
            int d = getOffectDay(c1.getTimeInMillis(), c2.getTimeInMillis());
            if (d == 0) {
                int h = getOffectHour(c1.getTimeInMillis(), c2.getTimeInMillis());
                if (h > 0) {
                    return "今天" + getStringByFormat(strDate, HHmm);
                    // return h + "小时前";
                } else if (h < 0) {
                    // return Math.abs(h) + "小时后";
                } else if (h == 0) {
                    int m = getOffectMinutes(c1.getTimeInMillis(), c2.getTimeInMillis());
                    if (m > 0) {
                        return m + "分钟前";
                    } else if (m < 0) {
                        // return Math.abs(m) + "分钟后";
                    } else {
                        return "刚刚";
                    }
                }

            } else if (d > 0) {
                if (d == 1) {
                    // return "昨天"+getStringByFormat(strDate,outFormat);
                } else if (d == 2) {
                    // return "前天"+getStringByFormat(strDate,outFormat);
                }
            } else if (d < 0) {
                if (d == -1) {
                    // return "明天"+getStringByFormat(strDate,outFormat);
                } else if (d == -2) {
                    // return "后天"+getStringByFormat(strDate,outFormat);
                } else {
                    // return Math.abs(d) +
                    // "天后"+getStringByFormat(strDate,outFormat);
                }
            }

            String out = getStringByFormat(strDate, outFormat);
            if (!TextUtils.isEmpty(out)) {
                return out;
            }
        } catch (Exception e) {
        }

        return strDate;
    }

    /**
     * 取指定日期为星期几.
     *
     * @param strDate  指定日期
     * @param inFormat 指定日期格式
     * @return String 星期几
     */
    public static String getWeekNumber(String strDate, String inFormat) {
        String week = "星期日";
        Calendar calendar = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat(inFormat);
        try {
            calendar.setTime(df.parse(strDate));
        } catch (Exception e) {
            return "错误";
        }
        int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (intTemp) {
            case 0:
                week = "星期日";
                break;
            case 1:
                week = "星期一";
                break;
            case 2:
                week = "星期二";
                break;
            case 3:
                week = "星期三";
                break;
            case 4:
                week = "星期四";
                break;
            case 5:
                week = "星期五";
                break;
            case 6:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * 根据给定的日期判断是否为上下午.
     *
     * @param strDate the str date
     * @param format  the format
     * @return the time quantum
     */
    public static String getTimeQuantum(String strDate, String format) {
        Date mDate = getDateByFormat(strDate, format);
        int hour = mDate.getHours();
        if (hour >= 12)
            return "PM";
        else
            return "AM";
    }

    /**
     * 根据给定的毫秒数算得时间的描述.
     *
     * @param milliseconds the milliseconds
     * @return the time description
     */
    public static String getTimeDescription(long milliseconds) {
        if (milliseconds > 1000) {
            // 大于一分
            if (milliseconds / 1000 / 60 > 1) {
                long minute = milliseconds / 1000 / 60;
                long second = milliseconds / 1000 % 60;
                return minute + "分" + second + "秒";
            } else {
                // 显示秒
                return milliseconds / 1000 + "秒";
            }
        } else {
            return milliseconds + "毫秒";
        }
    }

    /**
     * 根据当前日期获得上周的日期区间（本周周一和周日日期
     *
     * @param format
     * @return
     */
    @SuppressWarnings("AccessStaticViaInstance")
    public static String getThisWeekTimeInterval(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendarBegin = Calendar.getInstance(Locale.CHINA);
        calendarBegin.setFirstDayOfWeek(Calendar.MONDAY);
        calendarBegin.setTimeInMillis(System.currentTimeMillis());
        //周一
        calendarBegin.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Calendar calendarEnd = Calendar.getInstance(Locale.CHINA);
        calendarEnd.setFirstDayOfWeek(Calendar.MONDAY);
        calendarEnd.setTimeInMillis(System.currentTimeMillis());
        //周日
        calendarEnd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return simpleDateFormat.format(calendarBegin.getTime()) + "," + simpleDateFormat.format(calendarEnd.getTime());
    }

    /**
     * 根据当前日期获得上周的日期区间（上周周一和周日日期
     *
     * @param format
     * @return
     */
    @SuppressWarnings("AccessStaticViaInstance")
    public static String getLastWeekTimeInterval(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        Calendar calendarBegin = Calendar.getInstance();
        calendarBegin.setTime(date);
        //判断当前日期是否为周末，因为周末是本周第一天，如果不向后推迟一天的到的将是下周一的零点，而不是本周周一零点
        if (1 == calendarBegin.get(Calendar.DAY_OF_WEEK)) {
            calendarBegin.add(Calendar.DATE, -1);
        }
        //时间减去7天
        calendarBegin.add(Calendar.DAY_OF_MONTH, -7);
        calendarBegin.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(date);
        if (1 == calendarEnd.get(Calendar.DAY_OF_WEEK)) {
            calendarEnd.add(Calendar.DATE, -1);
        }
        calendarEnd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return simpleDateFormat.format(calendarBegin.getTime()) + "," + simpleDateFormat.format(calendarEnd.getTime());
    }

    public static String toDayHour(String time) {
        String day = "";
        String hour = "";
        if (time.contains(" ")) {
            String[] times = time.split(" ");
            if (times[0].contains("-")) {
                day = times[0].split("-")[2];
            } else if (times[0].contains("/")) {
                day = times[0].split("/")[2];
            }
            hour = times[1].split(":")[0];
            return day + "日" + hour + "时";
        }
        if (time.contains("-")) {
            day = time.split("-")[2];
        } else if (time.contains("/")) {
            day = time.split("/")[2];
        }
        return day + "日";
    }
}
