package com.cjf.util.utils;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: CalendarConvert </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/8/27 15:45
 */
public class CalendarConvert {
    private static final ThreadLocal<Map<String, SimpleDateFormat>> SDF_THREAD_LOCAL
            = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<>();
        }
    };

    private static SimpleDateFormat getDefaultFormat() {
        return getSafeDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat getSafeDateFormat(String pattern) {
        Map<String, SimpleDateFormat> sdfMap = SDF_THREAD_LOCAL.get();
        //noinspection ConstantConditions
        SimpleDateFormat simpleDateFormat = sdfMap.get(pattern);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(pattern);
            sdfMap.put(pattern, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    /**
     * Milliseconds to the formatted time string.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param millis The milliseconds.
     * @return the formatted time string
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, getDefaultFormat());
    }

    /**
     * Milliseconds to the formatted time string.
     *
     * @param millis  The milliseconds.
     * @param pattern The pattern of date format, such as yyyy/MM/dd HH:mm
     * @return the formatted time string
     */
    public static String millis2String(long millis, @NonNull final String pattern) {
        return millis2String(millis, getSafeDateFormat(pattern));
    }

    /**
     * Milliseconds to the formatted time string.
     *
     * @param millis The milliseconds.
     * @param format The format.
     * @return the formatted time string
     */
    public static String millis2String(final long millis, @NonNull final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * Milliseconds to the formatted time string.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param calendar The milliseconds.
     * @return the formatted time string
     */
    public static String calendar2String(final @NonNull Calendar calendar) {
        return calendar2String(calendar, getDefaultFormat());
    }

    /**
     * Milliseconds to the formatted time string.
     *
     * @param calendar The milliseconds.
     * @param pattern  The pattern of date format, such as yyyy/MM/dd HH:mm
     * @return the formatted time string
     */
    public static String calendar2String(final @NonNull Calendar calendar, @NonNull final String pattern) {
        return calendar2String(calendar, getSafeDateFormat(pattern));
    }

    /**
     * Milliseconds to the formatted time string.
     *
     * @param calendar The milliseconds.
     * @param format   The format.
     * @return the formatted time string
     */
    public static String calendar2String(final @NonNull Calendar calendar, @NonNull final DateFormat format) {
        return millis2String(calendar.getTimeInMillis(), format);
    }

    /**
     * Formatted time string to the milliseconds.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the milliseconds
     */
    public static long string2Millis(final String time) {
        return string2Millis(time, getDefaultFormat());
    }

    /**
     * Formatted time string to the milliseconds.
     *
     * @param time    The formatted time string.
     * @param pattern The pattern of date format, such as yyyy/MM/dd HH:mm
     * @return the milliseconds
     */
    public static long string2Millis(final String time, @NonNull final String pattern) {
        return string2Millis(time, getSafeDateFormat(pattern));
    }

    /**
     * Formatted time string to the milliseconds.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the milliseconds
     */
    public static long string2Millis(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Formatted time string to the milliseconds.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the milliseconds
     */
    @NonNull
    public static Calendar string2Calendar(final String time) {
        return string2Calendar(time, getDefaultFormat());
    }

    /**
     * Formatted time string to the milliseconds.
     *
     * @param time    The formatted time string.
     * @param pattern The pattern of date format, such as yyyy/MM/dd HH:mm
     * @return the milliseconds
     */
    @NonNull
    public static Calendar string2Calendar(final String time, @NonNull final String pattern) {
        return string2Calendar(time, getSafeDateFormat(pattern));
    }

    /**
     * Formatted time string to the milliseconds.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the milliseconds
     */
    @NonNull
    public static Calendar string2Calendar(final String time, @NonNull final DateFormat format) {
        long string2Millis = string2Millis(time, format);
        if (string2Millis == -1) {
            return CalendarCompat.getCalendar();
        }
        return CalendarCompat.getCalendar(string2Millis);
    }

    /**
     * 获取当前周的星期日，在当前月中
     *
     * @param week 第几周
     * @param time 时间
     * @return
     */
    public static String getWeekSundayForMonth(int week, String time) {
        return getWeekSundayForMonth(week, time, CalendarFormat.full);
    }

    /**
     * 获取当前周的星期日，在当前月中
     *
     * @param week   第几周
     * @param time   时间
     * @param format 时间格式
     * @return
     */
    public static String getWeekSundayForMonth(int week, String time, String format) {
        return getWeekSundayForMonth(week, time, format, format);
    }

    /**
     * 获取当前周的星期日，在当前月中
     *
     * @param week      第几周
     * @param time      时间
     * @param inFormat  输入时间格式
     * @param outFormat 输出时间格式
     * @return
     */
    public static String getWeekSundayForMonth(int week, String time, String inFormat, String outFormat) {
        return calendar2String(
                CalendarCompat.getWeekSundayForMonth(
                        week,
                        string2Millis(time, inFormat)
                ), outFormat
        );
    }

    /**
     * 获取当前周的星期日，在当前月中
     *
     * @param week   第几周
     * @param time   时间
     * @param format 时间格式
     * @return
     */
    public static String getWeekMondayForMonth(int week, String time, String format) {
        return getWeekMondayForMonth(week, time, format, format);
    }

    /**
     * 获取当前周的星期日，在当前月中
     *
     * @param week      第几周
     * @param time      时间
     * @param inFormat  输入时间格式
     * @param outFormat 输出时间格式
     * @return
     */
    public static String getWeekMondayForMonth(int week, String time, String inFormat, String outFormat) {
        return calendar2String(
                CalendarCompat.getWeekMondayForMonth(
                        week,
                        string2Millis(time, inFormat)
                ), outFormat
        );
    }
}
