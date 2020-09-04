package com.cjf.util.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.ArrayMap;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @NonNull
    public static StringBuilder deleteEnd(@NonNull StringBuilder builder) {
        if (builder.length() > 1) {
            return builder.delete(builder.length() - 1, builder.length());
        }
        return builder;
    }

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
    public static String toTime(@NonNull String time) {
        if (!TextUtils.isEmpty(time)) {
            if (!time.contains("T")) {
                return time;
            }
            time = time.replace("T", " ");
            if (!time.contains(".")) {
                return time;
            }
            time = time.substring(0, time.lastIndexOf("."));
        }
        return time;
    }

    public static String toStringZero(String value) {
        if (TextUtils.isEmpty(value)) {
            return "0";
        }
        if (value.indexOf(".") > 0) {
            value = value.replaceAll("0+?$", "");//去掉多余的0
            value = value.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return value;
    }
    /**
     * 获取已,隔开的第一个
     */
    @NonNull
    public static String getFirstSplit(@NonNull String target) {
        if (TextUtils.isEmpty(target)) {
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
        if (TextUtils.isEmpty(target)) {
            return target;
        }
        if (target.contains(",")) {
            String[] split = target.split(",");
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < split.length; i++) {
                builder.append(split[i]).append(",");
            }
            return deleteEnd(builder).toString();
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
}
