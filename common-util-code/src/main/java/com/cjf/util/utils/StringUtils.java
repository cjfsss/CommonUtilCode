package com.cjf.util.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ImageUtils;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import kotlin.text.Charsets;

/**
 * <p>Title: StringUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/1 23:26
 */
public class StringUtils {


    @SuppressLint({"HardwareIds", "MissingPermission"})
    @NonNull
    public static String getUUIDByPhoneInfo() {
        String serial;
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serial = Build.getSerial();
            } else {
                serial = Build.SERIAL;
            }
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 获取md5的UUID
     */
    @NonNull
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    @NonNull
    public static String getyyyyMMdd() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        return formatDate.format(new Date());
    }

    @SuppressWarnings("SpellCheckingInspection")
    @NonNull
    public static String getyyyyMMddHHmmssSSSS() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        return formatDate.format(new Date());
    }

    @NonNull
    public static String getUUIDTimeMD5() {
        return md5ToString(getUUID(), getyyyyMMddHHmmssSSSS());
    }

    /**
     * 对字符串进行编码
     *
     * @param target 目标
     * @return
     */
    @NonNull
    public static String toBase64Encoder(@Nullable String target) {
        target = toEmpty(target);
        if (TextUtils.isEmpty(target)) {
            return target;
        }
        return Base64.encodeToString(target.getBytes(Charsets.UTF_8), Base64.NO_WRAP);
    }

    /**
     * 对字符串进行解密
     *
     * @param target 目标值
     * @return
     */
    @NonNull
    public static String toBase64Decoder(@Nullable String target) {
        target = toEmpty(target);
        if (TextUtils.isEmpty(target)) {
            return target;
        }
        return new String(Base64.decode(target.getBytes(Charsets.UTF_8), Base64.NO_WRAP), Charsets.UTF_8);
    }

    /**
     * 对字符串进行编码
     *
     * @param target 目标
     * @return
     */
    @NonNull
    public static String toBase64UrlEncoder(@Nullable String target) {
        target = toEmpty(target);
        if (TextUtils.isEmpty(target)) {
            return target;
        }
        final String base64 = Base64.encodeToString(target.getBytes(Charsets.UTF_8), Base64.NO_WRAP);
        try {
            return URLEncoder.encode(base64, Charsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }

    /**
     * 对字符串进行解密
     *
     * @param target 目标值
     * @return
     */
    @NonNull
    public static String toUrlBase64Decoder(@Nullable String target) {
        target = toEmpty(target);
        if (TextUtils.isEmpty(target)) {
            return target;
        }
        try {
            final String base64 = URLDecoder.decode(target, Charsets.UTF_8.name());
            return new String(Base64.decode(base64.getBytes(Charsets.UTF_8), Base64.NO_WRAP), Charsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }

    @NonNull
    public static String md5ToString(@NonNull String... targets) {
        StringBuilder targetBuilder = new StringBuilder();
        for (String target : targets) {
            targetBuilder.append(target);
        }
        return EncryptUtils.encryptMD5ToString(targetBuilder.toString());
    }


    /**
     * 确定时间中的T和.后面的数字
     *
     * @param time 时间
     */
    @NonNull
    public static String toTime(@Nullable String time) {
        if (time == null || TextUtils.isEmpty(time)) {
            return "";
        }
        if (time.contains("T") || time.contains("+") || time.contains(".")) {
            if (time.contains("T")) {
                time = time.replace("T", " ");
            }
            if (time.contains(".")) {
                time = time.substring(0, time.lastIndexOf("."));
            }
            if (time.contains("+")) {
                time = time.substring(0, time.lastIndexOf("+"));
            }
            return time;
        }
        return time;
    }

    /**
     * 确定时间中的T和.后面的数字
     *
     * @param time 时间
     */
    @NonNull
    public static String toTimeN(@Nullable String time) {
        String toTime = toTime(time);
        if (TextUtils.isEmpty(toTime)) {
            return toTime;
        }
        if (toTime.contains("/")) {
            toTime = toTime.replace("/", "-");
        }
        if (!toTime.contains(".")) {
            return toTime;
        }
        return toTime.substring(0, toTime.lastIndexOf("."));
    }

    @NonNull
    public static String toStringZero(@Nullable String value) {
        if (value == null || TextUtils.isEmpty(toNULL(value))) {
            return "0";
        }
        if (value.indexOf(".") > 0) {
            value = value.replaceAll("0+?$", "");//去掉多余的0
            value = value.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return value;
    }

    /**
     * 将String转换为Long类型
     *
     * @param value 目标值
     */
    public static long toLongZero(@Nullable final String value) {
        if (value == null || TextUtils.isEmpty(toNULL(value))) {
            return 0L;
        }
        return Long.parseLong(value);
    }

    /**
     * 将String转换为Int类型
     *
     * @param value 目标值
     */
    public static int toIntZero(@Nullable final String value) {
        if (value == null || TextUtils.isEmpty(toNULL(value))) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    /**
     * 将String转换为float类型
     *
     * @param value 目标值
     */
    public static float toFloatZero(@Nullable final String value) {
        if (value == null || TextUtils.isEmpty(toNULL(value))) {
            return 0f;
        }
        return Float.parseFloat(value);
    }

    /**
     * 将String转换为double类型
     *
     * @param value 目标值
     */
    public static double toDoubleZero(@Nullable final String value) {
        if (value == null || TextUtils.isEmpty(toNULL(value))) {
            return 0.0D;
        }
        return Double.parseDouble(value);
    }


    /**
     * 获取已,隔开的第一个
     */
    @NonNull
    public static String getFirstSplit(@NonNull String target) {
        if (TextUtils.isEmpty(toNULL(target))) {
            return target;
        }
        if (target.contains(",")) {
            return target.split(",")[0];
        }
        return target;
    }

    /**
     * 获取已,隔开的排除第一个
     *
     * @param target 目标字符串
     */
    public static String getExcludeFirstSplit(@NonNull String target) {
        if (TextUtils.isEmpty(toNULL(target))) {
            return target;
        }
        if (target.contains(",")) {
            String[] split = target.split(",");
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < split.length; i++) {
                builder.append(split[i]).append(",");
            }
            return StringBuilderUtils.deleteEnd(builder).toString();
        }
        return "";
    }

    /**
     * 去除html中的空格
     *
     * @param html 目标
     */
    @NonNull
    public static String htmlDeleteSpace(@NonNull String html) {
        if (TextUtils.isEmpty(html)) {
            return "";
        }
        if (html.contains("&nbsp;")) {
            html = html.replaceAll("&nbsp;", " ");
        }
        return html.trim();
    }

    /**
     * 从连接地址中获取Url
     *
     * @param url 访问地址
     */
    public static String getGetUrl(@NonNull String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (url.contains("?")) {
            return url.split("\\?")[0];
        }
        return url;
    }

    /**
     * 从连接地址中获取Url
     *
     * @param url 访问地址
     */
    public static Map<String, String> getGetUrlParams(@NonNull String url) {
        Map<String, String> map = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            map = new ArrayMap<>();
        } else {
            map = new HashMap<>();
        }
        if (TextUtils.isEmpty(url)) {
            return map;
        }
        if (url.contains("?")) {
            String paramStr = url.split("\\?")[1];
            if (paramStr.contains("&")) {
                String[] params = paramStr.split("&");
                for (String param : params) {
                    if (param.contains("=")) {
                        String[] paramSplit = param.split("=");
                        if (paramSplit.length == 1) {
                            map.put(paramSplit[0], null);
                        } else {
                            map.put(paramSplit[0], paramSplit[1]);
                        }
                    }
                }
            } else {
                if (paramStr.contains("=")) {
                    String[] paramSplit = paramStr.split("=");
                    if (paramSplit.length == 1) {
                        map.put(paramSplit[0], null);
                    } else {
                        map.put(paramSplit[0], paramSplit[1]);
                    }
                }
            }
        } else {
            if (url.contains("&")) {
                String[] params = url.split("&");
                for (String param : params) {
                    if (param.contains("=")) {
                        String[] paramSplit = param.split("=");
                        if (paramSplit.length == 1) {
                            map.put(paramSplit[0], null);
                        } else {
                            map.put(paramSplit[0], paramSplit[1]);
                        }
                    }
                }
            } else {
                if (url.contains("=")) {
                    String[] paramSplit = url.split("=");
                    if (paramSplit.length == 1) {
                        map.put(paramSplit[0], null);
                    } else {
                        map.put(paramSplit[0], paramSplit[1]);
                    }
                }
            }
        }
        return map;
    }

    @Nullable
    public static String toNULL(@Nullable String target) {
        if (TextUtils.isEmpty(target) || TextUtils.equals(target, "null")
                || TextUtils.equals(target, "NULL")) {
            return null;
        }
        return target;
    }

    @NonNull
    public static String toEmpty(@Nullable String target) {
        if (TextUtils.isEmpty(target) || TextUtils.equals(target, "null")
                || TextUtils.equals(target, "NULL")) {
            return "";
        }
        return target == null ? "" : target;
    }

    @NonNull
    public static String trimNR(@Nullable String target) {
        if (target == null || TextUtils.isEmpty(toNULL(target))) {
            return "";
        }
        return target.replace("\n", "")
                .replace("\r", "").trim();
    }

    @NonNull
    public static String noData(@Nullable String target) {
        String toNULL = toNULL(target);
        if (toNULL == null) {
            return "暂无";
        }
        return toNULL;
    }

    @NonNull
    public static String noDataTime(@Nullable String target) {
        String toNULL = toNULL(target);
        if (toNULL == null) {
            return "暂无";
        }
        return toTime(target);
    }

    @NonNull
    public static String noDataTimeN(@Nullable String target) {
        String toNULL = toNULL(target);
        if (toNULL == null) {
            return "暂无";
        }
        return toTimeN(target);
    }

    @NonNull
    public static String deleteEndZero(@Nullable String target) {
        String str = toEmpty(target);
        if (str.indexOf(".") > 0) {
            // 去掉多余的0
            str = str.replaceAll("0+?\\$", "");
            // 如最后一位是.则去掉
            str = str.replaceAll("[.]\\$", "");
        }
        return str;
    }
    /**
     * 对入参保留最多两位小数(舍弃末尾的0)，如:
     * 3.345->3.34
     * 3.40->3.4
     * 3.0->3
     */
    @NonNull
    public static String toDecimalFormat(@Nullable final Object number) {
        return toDecimalFormat("0.##", number);
    }

    /**
     * 对入参保留最多几位小数(舍弃末尾的0)，如:
     * 3.345->3.34
     * 3.40->3.4
     * 3.0->3
     */
    @NonNull
    public static String toDecimalFormat(@NonNull final String pattern, @Nullable final Object number) {
        if (number == null) {
            return "0";
        }
        DecimalFormat format = new DecimalFormat(pattern);
        //未保留小数的舍弃规则，RoundingMode.FLOOR表示直接舍弃。
        format.setRoundingMode(RoundingMode.FLOOR);
        return format.format(number);
    }
    /**
     * Return whether the string is null or white space.
     *
     * @param s The string.
     * @return {@code true}: yes<br> {@code false}: no
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }


}
