package com.cjf.util.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * <p>Title: CalendarCompat </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/8/9 21:14
 */
public class CalendarCompat {

    /// 获取日期
    public static Calendar getCalendar() {
        return getCalendar(Calendar.getInstance());
    }

    /// 获取日期
    public static Calendar getCalendar(long millis) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(millis);
        return gregorianCalendar;
    }


    /// 获取日期
    public static Calendar getCalendar(@Nullable Calendar calendar) {
        if (calendar == null) {
            return getCalendar(Calendar.getInstance());
        }
        return getCalendar(calendar.getTimeInMillis());
    }

    /// 获取本月的每周多少天
    @NonNull
    public static String getWeekSizeForMonth(long millis) {
        return getWeekSizeForMonth(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的每周多少天
    @NonNull
    public static String getWeekSizeForMonth() {
        return getWeekSizeForMonth(null);
    }

    /// 获取本月的每周多少天
    @NonNull
    public static String getWeekSizeForMonth(@Nullable Calendar cal) {
        if (cal == null) {
            return getWeekSizeForMonth(getCalendar());
        }
        int weekSize = getWeekSize(getCalendar(cal));
        // 获取本月的第一个周日
        Calendar firstWeekSundayTime = getFirstWeekSunday(getCalendar(cal));
        int fastWeekSize = firstWeekSundayTime.get(Calendar.DATE);
        // 获取本月的最后一天
        Calendar lastTime = getLastWeek(getCalendar(cal));
        int lastDay = lastTime.get(Calendar.DATE);
        // 获取本月最后一周的星期一
        Calendar lastMondayTime = getLastWeekMonday(getCalendar(cal));
        int lastMondayDay = lastMondayTime.get(Calendar.DATE);
        StringBuilder timeBuilder = new StringBuilder();
        timeBuilder.append(String.valueOf(fastWeekSize)).append(",");
        for (int i = 0; i < weekSize - 2; i++) {
            timeBuilder.append("7").append(",");
        }
        timeBuilder.append(String.valueOf(lastDay - lastMondayDay + 1));
        return timeBuilder.toString();
    }

    /// 获取本月的每周多少天
    @NonNull
    public static String convertWeekForMonthWeekSize(long millis, @NonNull String weekMonth) {
        return convertWeekForMonthWeekSize(fromMillisecondsSinceEpoch(millis), weekMonth);
    }

    /// 获取本月的每周多少天
    @NonNull
    public static String convertWeekForMonthWeekSize(@Nullable Calendar cal, @NonNull String weekMonth) {
        if (cal == null) {
            return convertWeekForMonthWeekSize(getCalendar(), weekMonth);
        }
        String[] weekMonthArray = weekMonth.split(",");
        StringBuilder builder = new StringBuilder();
        int month = getMonth(getCalendar(cal));
        // 第一周(6.1-6.6),第二周(6.7-6.13),第三周(6.14-6.20),第四周(6.21-6.27),第五周(6.28-6.30)
        for (int i = 0; i < weekMonthArray.length; i++) {
            String indexZN;
            if (i == 0) {
                indexZN = "一";
            } else if (i == 1) {
                indexZN = "二";
            } else if (i == 2) {
                indexZN = "三";
            } else if (i == 3) {
                indexZN = "四";
            } else if (i == 4) {
                indexZN = "五";
            } else if (i == 5) {
                indexZN = "六";
            } else {
                indexZN = "七";
            }
            if (builder.length() != 0) {
                builder.append(",");
            }
            int weekSize = Integer.parseInt(weekMonthArray[i]);
            Calendar firstWeek = getFirstWeek(getCalendar(cal));
            int day = firstWeek.get(Calendar.DAY_OF_MONTH);
            // 第日周(8.1-8.1),第一周(8.1-8.7),第二周(8.2-8.8),第三周(8.9-8.15),第四周(8.16-8.22),第五周(8.23-8
            if (i == 0) {
                // 第一周
                int last = day + weekSize - 1;
                builder.append("第").append(indexZN).append("周")
                        .append("(").append(month).append(".").append(day).append("-")
                        .append(month).append(".").append(last).append(")");
            } else {
                int lastWeekSize = 0;
                for (int j = 0; j < i; j++) {
                    lastWeekSize += Integer.parseInt(weekMonthArray[j]);
                }
                int first = day + lastWeekSize;
                int last = first + weekSize - 1;
                builder.append("第").append(indexZN).append("周")
                        .append("(").append(month).append(".").append(first).append("-")
                        .append(month).append(".").append(last).append(")");
            }
        }
        return builder.toString();
    }

    /// 获取本月的第一周，也就是本月的第一天
    @NonNull
    public static Calendar getFirstWeek(long millis) {
        return getFirstWeek(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的第一周，也就是本月的第一天
    @NonNull
    public static Calendar getFirstWeek() {
        return getFirstWeek(null);
    }

    /// 获取本月的第一周，也就是本月的第一天
    @NonNull
    public static Calendar getFirstWeek(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getFirstWeek(getCalendar());
        }
        return getMonthFirstDay(dateTime);
    }

    /// 获取本月的第一周的周日，也就是本月的第一天的周日
    @NonNull
    public static Calendar getFirstWeekSunday(long millis) {
        return getFirstWeekSunday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的第一周的周日，也就是本月的第一天的周日
    @NonNull
    public static Calendar getFirstWeekSunday() {
        return getFirstWeekSunday(null);
    }

    /// 获取本月的第一周的周日，也就是本月的第一天的周日
    @NonNull
    public static Calendar getFirstWeekSunday(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getFirstWeekSunday(getCalendar());
        }
        return getDateTimeSunday(getFirstWeek(dateTime));
    }

    /// 获取本月的最后一周，也就是本月的最后一天
    @NonNull
    public static Calendar getLastWeek(long millis) {
        return getLastWeek(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的最后一周，也就是本月的最后一天
    @NonNull
    public static Calendar getLastWeek() {
        return getLastWeek(null);
    }

    /// 获取本月的最后一周，也就是本月的最后一天
    @NonNull
    public static Calendar getLastWeek(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getLastWeek(getCalendar());
        }
        return getMonthLastDay(dateTime);
    }

    /// 获取本月的最后一周的周一，也就是本月的最后一天的周一
    @NonNull
    public static Calendar getLastWeekMonday(long millis) {
        return getLastWeekMonday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的最后一周的周一，也就是本月的最后一天的周一
    @NonNull
    public static Calendar getLastWeekMonday() {
        return getLastWeekMonday(null);
    }

    /// 获取本月的最后一周的周一，也就是本月的最后一天的周一
    @NonNull
    public static Calendar getLastWeekMonday(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getLastWeekMonday(getCalendar());
        }
        return getDateTimeMonday(getLastWeek(dateTime));
    }

    /// 获取本月的第二周的周日
    @NonNull
    public static Calendar getSecondWeekSunday(long millis) {
        return getSecondWeekSunday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的第二周的周日
    @NonNull
    public static Calendar getSecondWeekSunday() {
        return getSecondWeekSunday(null);
    }

    /// 获取本月的第二周的周日
    @NonNull
    public static Calendar getSecondWeekSunday(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getSecondWeekSunday(getCalendar());
        }
        /// 获取本月第一周的周日
        Calendar firstWeekDateTime = getFirstWeekSunday(getCalendar(dateTime));
        firstWeekDateTime.add(Calendar.DATE, 7);
        return firstWeekDateTime;
    }

    /// 获取本月的第三周的星期一
    @NonNull
    public static Calendar getThirdWeekMonday(long millis) {
        return getThirdWeekMonday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的第三周的星期一
    @NonNull
    public static Calendar getThirdWeekMonday() {
        return getThirdWeekMonday(null);
    }

    /// 获取本月的第三周的星期一
    @NonNull
    public static Calendar getThirdWeekMonday(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getSecondWeekSunday(getCalendar());
        }

        /// 获取本月第一周的周日
        Calendar firstWeekDateTime = getFirstWeekSunday(dateTime);
        firstWeekDateTime.add(Calendar.DATE, 8);
        return firstWeekDateTime;
    }

    /// 获取本月的第三周的周日
    @NonNull
    public static Calendar getThirdWeekSunday(long millis) {
        return getThirdWeekSunday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的第三周的周日
    @NonNull
    public static Calendar getThirdWeekSunday() {
        return getThirdWeekSunday(null);
    }

    /// 获取本月的第三周的周日
    @NonNull
    public static Calendar getThirdWeekSunday(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getThirdWeekSunday(getCalendar());
        }

        /// 获取本月第一周的周日
        Calendar firstWeekDateTime = getFirstWeekSunday(dateTime);
        firstWeekDateTime.add(Calendar.DATE, 14);
        return firstWeekDateTime;
    }

    /// 获取本月的第四周的星期一
    @NonNull
    public static Calendar getFourthWeekMonday(long millis) {
        return getFourthWeekMonday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的第四周的星期一
    @NonNull
    public static Calendar getFourthWeekMonday() {
        return getFourthWeekMonday(null);
    }

    /// 获取本月的第四周的星期一
    @NonNull
    public static Calendar getFourthWeekMonday(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getFourthWeekMonday(getCalendar());
        }

        /// 获取本月第一周的周日
        Calendar firstWeekDateTime = getFirstWeekSunday(dateTime);
        firstWeekDateTime.add(Calendar.DATE, 15);
        return firstWeekDateTime;
    }

    /// 获取本月的第四周的周日
    @NonNull
    public static Calendar getFourthWeekSunday(long millis) {
        return getFourthWeekSunday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的第四周的周日
    @NonNull
    public static Calendar getFourthWeekSunday() {
        return getFourthWeekSunday(null);
    }

    /// 获取本月的第四周的周日
    @NonNull
    public static Calendar getFourthWeekSunday(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getFourthWeekSunday(getCalendar());
        }
        // 获取最后一周的周一
        Calendar lastWeekMondayDateTime = getLastWeekMonday(getCalendar(dateTime));
        int lastWeekMondayDateDay = lastWeekMondayDateTime.get(Calendar.DAY_OF_MONTH);
        // 获取第四周的周一
        Calendar fourthWeekMondayDateTime = getFourthWeekMonday(getCalendar(dateTime));
        int fourthWeekMondayDateDay = fourthWeekMondayDateTime.get(Calendar.DAY_OF_MONTH);
        if (lastWeekMondayDateDay == fourthWeekMondayDateDay) {
            // 第四周是最后一周,也就是本月的最后一天
            return getLastWeek(dateTime);
        }
        // 不是最后一周
        /// 获取本月第一周的周日
        Calendar firstWeekDateTime = getFirstWeekSunday(getCalendar(dateTime));
        firstWeekDateTime.add(Calendar.DATE, 21);
        return firstWeekDateTime;
    }

    /// 获取本月的第五周的星期一
    @NonNull
    public static Calendar getFifthWeekMonday(long millis) {
        return getFifthWeekMonday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的第五周的星期一
    @NonNull
    public static Calendar getFifthWeekMonday() {
        return getFifthWeekMonday(null);
    }

    /// 获取本月的第五周的星期一
    @NonNull
    public static Calendar getFifthWeekMonday(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getFifthWeekMonday(getCalendar());
        }

        /// 获取本月第一周的周日
        Calendar firstWeekDateTime = getFirstWeekSunday(dateTime);
        firstWeekDateTime.add(Calendar.DATE, 22);
        return firstWeekDateTime;
    }

    /// 获取本月的第五周的周日
    @NonNull
    public static Calendar getFifthWeekSunday(long millis) {
        return getFifthWeekSunday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的第五周的周日
    @NonNull
    public static Calendar getFifthWeekSunday() {
        return getFifthWeekSunday(null);
    }

    /// 获取本月的第五周的周日
    @NonNull
    public static Calendar getFifthWeekSunday(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getFifthWeekSunday(getCalendar());
        }
        // 获取最后一周的周一
        Calendar lastWeekMondayDateTime = getLastWeekMonday(getCalendar(dateTime));
        int lastWeekMondayDateDay = lastWeekMondayDateTime.get(Calendar.DAY_OF_MONTH);
        // 获取第四周的周一
        Calendar fourthWeekMondayDateTime = getFourthWeekMonday(getCalendar(dateTime));
        int fourthWeekMondayDateDay = fourthWeekMondayDateTime.get(Calendar.DAY_OF_MONTH);
        if (fourthWeekMondayDateDay == lastWeekMondayDateDay) {
            // 第四周是最后一周,也就是本月的最后一天
            return getLastWeek(dateTime);
        }
        // 获取第五周的周一
        Calendar fifthWeekMondayDateTime = getFifthWeekMonday(getCalendar(dateTime));
        int fifthWeekMondayDateDay = fifthWeekMondayDateTime.get(Calendar.DAY_OF_MONTH);
        if (lastWeekMondayDateDay == fifthWeekMondayDateDay) {
            // 第五周是最后一周,也就是本月的最后一天
            return getLastWeek(dateTime);
        }
        // 不是最后一周
        /// 获取本月第一周的周日
        Calendar firstWeekDateTime = getFirstWeekSunday(getCalendar(dateTime));
        firstWeekDateTime.add(Calendar.DATE, 28);
        return firstWeekDateTime;
    }

    /// 获取本月的第六周的星期一
    @NonNull
    public static Calendar getSixthWeekMonday(long millis) {
        return getSixthWeekMonday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的第六周的星期一
    @NonNull
    public static Calendar getSixthWeekMonday() {
        return getSixthWeekMonday(null);
    }

    /// 获取本月的第六周的星期一
    @NonNull
    public static Calendar getSixthWeekMonday(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getSixthWeekMonday(getCalendar());
        }

        /// 获取本月第一周的周日
        Calendar firstWeekDateTime = getFirstWeekSunday(dateTime);
        firstWeekDateTime.add(Calendar.DATE, 29);
        return firstWeekDateTime;
    }

    /// 获取本月的第六周的周日
    @NonNull
    public static Calendar getSixWeekSunday(long millis) {
        return getSixWeekSunday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取本月的第六周的周日
    @NonNull
    public static Calendar getSixWeekSunday() {
        return getSixWeekSunday(null);
    }

    /// 获取本月的第六周的周日
    @NonNull
    public static Calendar getSixWeekSunday(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getSixWeekSunday(getCalendar());
        }
        // 第六周是最后一周,也就是本月的最后一天
        return getLastWeek(dateTime);
    }

    /// 获取当前时间第几周的周一
    @NonNull
    public static Calendar getWeekMonday(int week, long millis) {
        return getWeekMonday(week, fromMillisecondsSinceEpoch(millis));
    }

    /// 获取当前时间第几周的周一
    @NonNull
    public static Calendar getWeekMonday(int week) {
        return getWeekMonday(week, null);
    }

    /// 获取当前时间第几周的周一
    @NonNull
    public static Calendar getWeekMonday(int week, @Nullable Calendar dateTime) {
        Calendar monthFirstDay = getMonthFirstDay(dateTime);
        if (week == 1) {
            return getDateTimeMonday(monthFirstDay);
        }
        Calendar firstWeekSunday = getFirstWeekSunday(monthFirstDay);
        firstWeekSunday.add(Calendar.DATE, (week - 2) * 7 + 1);
        return firstWeekSunday;
    }

    /// 获取当前时间第几周的周日，限制本月
    public static long getWeekMondayForMonthMills(int week, long millis) {
        return getWeekMondayForMonth(week, millis).getTimeInMillis();
    }

    /// 获取当前时间第几周的周一,限制本月
    @NonNull
    public static Calendar getWeekMondayForMonth(int week, long millis) {
        return getWeekMondayForMonth(week, fromMillisecondsSinceEpoch(millis));
    }

    /// 获取当前时间第几周的周日，限制本月
    public static long getWeekMondayForMonthMills(int week) {
        return getWeekMondayForMonth(week, null).getTimeInMillis();
    }

    /// 获取当前时间第几周的周一,限制本月
    @NonNull
    public static Calendar getWeekMondayForMonth(int week) {
        return getWeekMondayForMonth(week, null);
    }

    /// 获取当前时间第几周的周日，限制本月
    public static long getWeekMondayForMonthMills(int week, @Nullable Calendar dateTime) {
        return getWeekMondayForMonth(week, dateTime).getTimeInMillis();
    }

    /// 获取当前时间第几周的周一,限制本月
    @NonNull
    public static Calendar getWeekMondayForMonth(int week, @Nullable Calendar dateTime) {
        Calendar monthFirstDay = getMonthFirstDay(getCalendar(dateTime));
        if (week == 1) {
            return monthFirstDay;
        }
        int weekSize = getWeekSize(getCalendar(dateTime));
        if (week == weekSize) {
            // 最后一周
            return getLastWeekMonday(dateTime);
        }
        Calendar firstWeekSunday = getFirstWeekSunday(monthFirstDay);
        firstWeekSunday.add(Calendar.DATE, (week - 2) * 7 + 1);
        return firstWeekSunday;
    }

    /// 获取当前时间第几周的周日
    @NonNull
    public static Calendar getWeekSunday(int week, long millis) {
        return getWeekSunday(week, fromMillisecondsSinceEpoch(millis));
    }

    /// 获取当前时间第几周的周日
    @NonNull
    public static Calendar getWeekSunday(int week) {
        return getWeekSunday(week, null);
    }

    /// 获取当前时间第几周的周日
    @NonNull
    public static Calendar getWeekSunday(int week, @Nullable Calendar dateTime) {
        Calendar monthFirstDay = getMonthFirstDay(dateTime);
        if (week == 1) {
            return getDateTimeSunday(monthFirstDay);
        }
        Calendar firstWeekSunday = getDateTimeSunday(monthFirstDay);
        firstWeekSunday.add(Calendar.DATE, (week - 1) * 7);
        return firstWeekSunday;
    }

    /// 获取当前时间第几周的周日，限制本月
    public static long getWeekSundayForMonthMills(int week, long millis) {
        return getWeekSundayForMonth(week, millis).getTimeInMillis();
    }

    /// 获取当前时间第几周的周日，限制本月
    @NonNull
    public static Calendar getWeekSundayForMonth(int week, long millis) {
        return getWeekSundayForMonth(week, fromMillisecondsSinceEpoch(millis));
    }

    /// 获取当前时间第几周的周日，限制本月
    public static long getWeekSundayForMonthMills(int week) {
        return getWeekSundayForMonth(week, null).getTimeInMillis();
    }

    /// 获取当前时间第几周的周日，限制本月
    @NonNull
    public static Calendar getWeekSundayForMonth(int week) {
        return getWeekSundayForMonth(week, null);
    }

    /// 获取当前时间第几周的周日，限制本月
    public static long getWeekSundayForMonthMills(int week, @Nullable Calendar dateTime) {
        return getWeekSundayForMonth(week, dateTime).getTimeInMillis();
    }

    /// 获取当前时间第几周的周日，限制本月
    @NonNull
    public static Calendar getWeekSundayForMonth(int week, @Nullable Calendar dateTime) {
        Calendar monthFirstDay = getMonthFirstDay(getCalendar(dateTime));
        if (week == 1) {
            return getDateTimeSunday(monthFirstDay);
        }
        int weekSize = getWeekSize(getCalendar(dateTime));
        Calendar firstWeekSunday = getDateTimeSunday(monthFirstDay);
        if (week == weekSize) {
            // 最后一周
            return getMonthLastDay(dateTime);
        }
        firstWeekSunday.add(Calendar.DATE, (week - 1) * 7);
        return firstWeekSunday;
    }

    /// 获取当前时间所在的周一
    @NonNull
    public static Calendar getDateTimeMonday(long millis) {
        return getDateTimeMonday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取当前时间所在的周一
    @NonNull
    public static Calendar getDateTimeMonday() {
        return getDateTimeMonday(null);
    }

    /// 获取当前时间所在的周一
    @NonNull
    public static Calendar getDateTimeMonday(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getDateTimeSunday(getCalendar());
        }
        dateTime.setFirstDayOfWeek(Calendar.MONDAY);
        int weekday = dateTime.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekday == 1) {
            // 周一
            return dateTime;
        }
        if (weekday == 2) {
            dateTime.add(Calendar.DATE, -1);
            // 周二
            return dateTime;
        }
        if (weekday == 3) {
            // 周三
            dateTime.add(Calendar.DATE, -2);
            return dateTime;
        }
        if (weekday == 4) {
            // 周四
            dateTime.add(Calendar.DATE, -3);
            return dateTime;
        }
        if (weekday == 5) {
            // 周五
            dateTime.add(Calendar.DATE, -4);
            return dateTime;
        }
        if (weekday == 6) {
            // 周六
            dateTime.add(Calendar.DATE, -5);
            return dateTime;
        }
        dateTime.add(Calendar.DATE, -6);
        return dateTime;
    }

    /**
     * 获取当前时间所在的周日
     *
     * @return
     */
    @NonNull
    public static Calendar getDateTimeSunday(long millis) {
        return getDateTimeSunday(fromMillisecondsSinceEpoch(millis));
    }

    /**
     * 获取当前时间所在的周日
     *
     * @return
     */
    @NonNull
    public static Calendar getDateTimeSunday() {
        return getDateTimeSunday(null);
    }

    /**
     * 获取当前时间所在的周日
     *
     * @param cal
     * @return
     */
    @NonNull
    public static Calendar getDateTimeSunday(@Nullable Calendar cal) {
        if (cal == null) {
            return getDateTimeSunday(getCalendar());
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int weekday = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekday == 0) {
            weekday = 7;
        }
        if (weekday == 7) {
            // 周日
            return cal;
        }
        if (weekday == 6) {
            // 周六
            cal.add(Calendar.DATE, 1);
            return cal;
        }
        if (weekday == 5) {
            // 周五
            cal.add(Calendar.DATE, 2);
            return cal;
        }
        if (weekday == 4) {
            // 周四
            cal.add(Calendar.DATE, 3);
            return cal;
        }
        if (weekday == 3) {
            // 周三
            cal.add(Calendar.DATE, 4);
            return cal;
        }
        if (weekday == 2) {
            // 周二
            cal.add(Calendar.DATE, 5);
            return cal;
        }
        cal.add(Calendar.DATE, 6);
        return cal;
    }

    /// 获取当前月的某一天
    @NonNull
    public static Calendar getMonthDay(long millis, int day) {
        return getMonthDay(fromMillisecondsSinceEpoch(millis), day);
    }

    /// 获取当前月的某一天
    @NonNull
    public static Calendar getMonthDay(int day) {
        return getMonthDay(null, day);
    }

    /// 获取当前月的某一天
    @NonNull
    public static Calendar getMonthDay(@Nullable Calendar cal, int day) {
        if (cal == null) {
            return getMonthDay(cal, day);
        }
        if (day < 1) {
            day = 1;
        }
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);// 时
        cal.set(Calendar.MINUTE, 0);// 分
        cal.set(Calendar.SECOND, 0);// 秒
        return cal;
    }

    /// 获取当前月的周数
    public static int getWeekSize(long millis) {
        return getWeekSize(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取当前月的周数
    public static int getWeekSize() {
        return getWeekSize(null);
    }

    /// 获取当前月的周数
    public static int getWeekSize(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getWeekSize(getCalendar());
        }
        // 获取本月的最后一个周一
        Calendar lastWeekMondayTime = getLastWeekMonday(getCalendar(dateTime));
        int lastWeekMondayDay = lastWeekMondayTime.get(Calendar.DAY_OF_MONTH);
        // 获取本月的第一个周日
        Calendar lastFirstWeekSundayTime = getFirstWeekSunday(dateTime);
        // 获取本月的第四周的周一 3*7-6
        lastFirstWeekSundayTime.add(Calendar.DATE, 15);
        int fourthMondayDay = lastFirstWeekSundayTime.get(Calendar.DAY_OF_MONTH);
        if (lastWeekMondayDay == fourthMondayDay) {
            /// 当前月有四周
            return 4;
        }
        // 获取本月的第五周的周一 7
        lastFirstWeekSundayTime.add(Calendar.DATE, 7);
        int fifthMondayDay = lastFirstWeekSundayTime.get(Calendar.DAY_OF_MONTH);
        if (lastWeekMondayDay == fifthMondayDay) {
            /// 当前月有五周
            return 5;
        }
        // 当前月有六周，一个月最多有六周
        return 6;
    }

    /// 获取当前第几周
    public static int getCurrentWeek(long millis) {
        return getCurrentWeek(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取当前第几周
    public static int getCurrentWeek() {
        return getCurrentWeek(null);
    }

    /// 获取当前第几周
    public static int getCurrentWeek(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getCurrentWeek(getCalendar());
        }
        // 设置周一是第一天
        dateTime.setFirstDayOfWeek(Calendar.MONDAY);
        return dateTime.get(Calendar.WEEK_OF_MONTH);
    }

    /// 获取当前月的第一天
    @NonNull
    public static Calendar getMonthFirstDay() {
        return getMonthFirstDay(null);
    }

    /// 获取当前月的第一天
    @NonNull
    public static Calendar getMonthFirstDay(@Nullable Calendar cal) {
        return getMonthDay(cal, 1);
    }

    /// 获取当前月的第一天
    @NonNull
    public static Calendar getMonthFirstDay(long millis) {
        return getMonthFirstDay(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取当前月的最后一天
    @NonNull
    public static Calendar getMonthLastDay() {
        return getMonthLastDay(null);
    }

    /// 获取当前月的最后一天
    @NonNull
    public static Calendar getMonthLastDay(@Nullable Calendar cal) {
        Calendar lastCal = getMonthDay(cal, getMonthDaySize(cal));
        lastCal.set(Calendar.HOUR_OF_DAY, 23);// 时
        lastCal.set(Calendar.MINUTE, 59);// 分
        lastCal.set(Calendar.SECOND, 59);// 秒
        return cal;
    }

    /// 获取当前月的最后一天
    @NonNull
    public static Calendar getMonthLastDay(long millis) {
        return getMonthLastDay(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取月份
    public static int getMonth() {
        return getMonth(null);
    }

    /// 获取月份
    public static int getMonth(@Nullable Calendar cal) {
        if (cal != null) {
            return cal.get(Calendar.MONTH) + 1;
        }
        return getMonth(getCalendar());
    }

    /// 获取月份
    public static int getMonth(long millis) {
        return getMonth(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取月份
    public static int getMonthByMill() {
        return getMonthByMill(getCalendar().getTimeInMillis());
    }

    /// 获取月份
    public static int getMonthByMill(long millis) {
        Calendar cal = getCalendar();
        cal.setTimeInMillis(millis);
        return getMonth(cal);
    }

    /// 获取当前月的天数
    public static int getMonthDaySize() {
        return getMonthDaySize(null);
    }

    /// 获取当前月的天数
    public static int getMonthDaySize(@Nullable Calendar dateTime) {
        if (dateTime == null) {
            return getMonthDaySize(dateTime);
        }
        return dateTime.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /// 获取当前月的天数
    public static int getMonthDaySize(long millis) {
        Calendar cal = getCalendar();
        cal.setTimeInMillis(millis);
        return getMonthDaySize(cal);
    }

    /// year is equal.
    /// 是否同年.
    public static boolean yearIsEqual(@Nullable Calendar dateTime, Calendar locDateTime) {
        return dateTime.get(Calendar.YEAR) == locDateTime.get(Calendar.YEAR);
    }

    /// year is equal.
    /// 是否同年.
    public static boolean yearIsEqualByMs(int ms, int locMs) {
        Calendar dateTime = new GregorianCalendar();
        dateTime.setTimeInMillis(ms);
        Calendar locDateTime = new GregorianCalendar();
        locDateTime.setTimeInMillis(locMs);
        return yearIsEqual(locDateTime, locDateTime);
    }

    /// Return whether it is leap year.
    /// 是否是闰年
    public static boolean isLeapYear(@Nullable Calendar cal) {
        return isLeapYearByYear(cal.get(Calendar.YEAR));
    }

    /// Return whether it is leap year.
    /// 是否是闰年
    public static boolean isLeapYearByYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    @NonNull
    public static Calendar fromMillisecondsSinceEpoch(long millis) {
        Calendar dateTime = getCalendar();
        dateTime.setTimeInMillis(millis);
        return dateTime;
    }

    /// 获取昨天
    @NonNull
    public static Calendar getYesterday(long millis) {
        return getYesterday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取昨天
    @NonNull
    public static Calendar getYesterday() {
        return getYesterday(null);
    }

    /// 获取昨天
    @NonNull
    public static Calendar getYesterday(@Nullable Calendar dateTime) {
        if (dateTime != null) {
            dateTime.add(Calendar.DATE, -1);
            return dateTime;
        }
        return getYesterday(getCalendar());
    }

    /// 获取明天
    @NonNull
    public static Calendar getTomorrow(long millis) {
        return getTomorrow(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取明天
    @NonNull
    public static Calendar getTomorrow() {
        return getTomorrow(null);
    }

    /// 获取明天
    @NonNull
    public static Calendar getTomorrow(@Nullable Calendar dateTime) {
        if (dateTime != null) {
            dateTime.add(Calendar.DATE, 1);
            return dateTime;
        }
        return getTomorrow(getCalendar());
    }

    /// 获取后天
    @NonNull
    public static Calendar getAfterTomorrow(long millis) {
        return getAfterTomorrow(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取后天
    @NonNull
    public static Calendar getAfterTomorrow() {
        return getAfterTomorrow(null);
    }

    /// 获取后天
    @NonNull
    public static Calendar getAfterTomorrow(@Nullable Calendar dateTime) {
        if (dateTime != null) {
            dateTime.add(Calendar.DATE, 2);
            return dateTime;
        }
        return getAfterTomorrow(getCalendar());
    }

    /// 获取前天
    @NonNull
    public static Calendar getBeforeYesterday(long millis) {
        return getBeforeYesterday(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取前天
    @NonNull
    public static Calendar getBeforeYesterday() {
        return getBeforeYesterday(null);
    }

    /// 获取前天
    @NonNull
    public static Calendar getBeforeYesterday(@Nullable Calendar dateTime) {
        if (dateTime != null) {
            dateTime.add(Calendar.DATE, -2);
            return dateTime;
        }
        return getBeforeYesterday(getCalendar());
    }


    /// 获取昨天
    @NonNull
    public static Calendar getYesterdayMonth(long millis) {
        return getYesterdayMonth(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取昨天
    @NonNull
    public static Calendar getYesterdayMonth() {
        return getYesterdayMonth(null);
    }

    /// 获取昨天
    @NonNull
    public static Calendar getYesterdayMonth(@Nullable Calendar dateTime) {
        if (dateTime != null) {
            dateTime.add(Calendar.MONTH, -1);
            return dateTime;
        }
        return getYesterdayMonth(getCalendar());
    }

    /// 获取明天
    @NonNull
    public static Calendar getTomorrowMonth(long millis) {
        return getTomorrowMonth(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取明天
    @NonNull
    public static Calendar getTomorrowMonth() {
        return getTomorrowMonth(null);
    }

    /// 获取明天
    @NonNull
    public static Calendar getTomorrowMonth(@Nullable Calendar dateTime) {
        if (dateTime != null) {
            dateTime.add(Calendar.MONTH, 1);
            return dateTime;
        }
        return getTomorrowMonth(getCalendar());
    }

    /// 获取后天
    @NonNull
    public static Calendar getAfterTomorrowMonth(long millis) {
        return getAfterTomorrowMonth(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取后天
    @NonNull
    public static Calendar getAfterTomorrowMonth() {
        return getAfterTomorrowMonth(null);
    }

    /// 获取后天
    @NonNull
    public static Calendar getAfterTomorrowMonth(@Nullable Calendar dateTime) {
        if (dateTime != null) {
            dateTime.add(Calendar.MONTH, 2);
            return dateTime;
        }
        return getAfterTomorrowMonth(getCalendar());
    }

    /// 获取前天
    @NonNull
    public static Calendar getBeforeYesterdayMonth(long millis) {
        return getBeforeYesterdayMonth(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取前天
    @NonNull
    public static Calendar getBeforeYesterdayMonth() {
        return getBeforeYesterdayMonth(null);
    }

    /// 获取前天
    @NonNull
    public static Calendar getBeforeYesterdayMonth(@Nullable Calendar dateTime) {
        if (dateTime != null) {
            dateTime.add(Calendar.MONTH, -2);
            return dateTime;
        }
        return getBeforeYesterdayMonth(getCalendar());
    }

    /// 获取昨天
    @NonNull
    public static Calendar getYesterdayWeek(long millis) {
        return getYesterdayWeek(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取昨天
    @NonNull
    public static Calendar getYesterdayWeek() {
        return getYesterdayWeek(null);
    }

    /// 获取昨天
    @NonNull
    public static Calendar getYesterdayWeek(@Nullable Calendar dateTime) {
        if (dateTime != null) {
            dateTime.add(Calendar.WEEK_OF_MONTH, -1);
            return dateTime;
        }
        return getYesterdayWeek(getCalendar());
    }

    /// 获取明天
    @NonNull
    public static Calendar getTomorrowWeek(long millis) {
        return getTomorrowWeek(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取明天
    @NonNull
    public static Calendar getTomorrowWeek() {
        return getTomorrowWeek(null);
    }

    /// 获取明天
    @NonNull
    public static Calendar getTomorrowWeek(@Nullable Calendar dateTime) {
        if (dateTime != null) {
            dateTime.add(Calendar.WEEK_OF_MONTH, 1);
            return dateTime;
        }
        return getTomorrowWeek(getCalendar());
    }

    /// 获取后天
    @NonNull
    public static Calendar getAfterTomorrowWeek(long millis) {
        return getAfterTomorrowWeek(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取后天
    @NonNull
    public static Calendar getAfterTomorrowWeek() {
        return getAfterTomorrowWeek(null);
    }

    /// 获取后天
    @NonNull
    public static Calendar getAfterTomorrowWeek(@Nullable Calendar dateTime) {
        if (dateTime != null) {
            dateTime.add(Calendar.WEEK_OF_MONTH, 2);
            return dateTime;
        }
        return getAfterTomorrowWeek(getCalendar());
    }

    /// 获取前天
    @NonNull
    public static Calendar getBeforeYesterdayWeek(long millis) {
        return getBeforeYesterdayWeek(fromMillisecondsSinceEpoch(millis));
    }

    /// 获取前天
    @NonNull
    public static Calendar getBeforeYesterdayWeek() {
        return getBeforeYesterdayWeek(null);
    }

    /// 获取前天
    @NonNull
    public static Calendar getBeforeYesterdayWeek(@Nullable Calendar dateTime) {
        if (dateTime != null) {
            dateTime.add(Calendar.WEEK_OF_MONTH, -2);
            return dateTime;
        }
        return getBeforeYesterdayWeek(getCalendar());
    }
}
