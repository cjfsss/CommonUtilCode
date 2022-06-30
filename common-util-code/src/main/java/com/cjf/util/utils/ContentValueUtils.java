package com.cjf.util.utils;

import android.content.ContentValues;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Title: MapUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/3/22 14:39
 */
public class ContentValueUtils {
    /**
     * 将map转换为List
     *
     * @return List
     */

    public static List<String> toListKey(ContentValues map) {
        if (map == null || map.size() == 0) {
            return new LinkedList<>();
        }
        final Set<String> keySet = map.keySet();
        return new LinkedList<>(keySet);
    }

    /**
     * 将map转换为List
     *
     * @return List
     */

    public static List<Object> toListValue(ContentValues map) {
        if (map == null || map.size() == 0) {
            return new ArrayList<>();
        }
        final List<Object> list = new ArrayList<>();
        final Set<String> keySet = map.keySet();
        final int size = keySet.size();
        int i = 0;
        for (String key : keySet) {
            Object item = map.get(key);
            if (item == null) {
                continue;
            }
            list.add(item);
            i++;
        }
        return list;
    }


    public static String toNull(ContentValues map, String key) {
        if (map == null) {
            return null;
        }
        Object object = map.get(key);
        if (object == null) {
            return null;
        }
        String target = (String) object;
        if (TextUtils.isEmpty(target) || TextUtils.equals(target, "null") || TextUtils.equals(target, "NULL")) {
            return null;
        }
        return target;
    }


    public static String get(ContentValues map, String key, String value) {
        String toNull = get(map, key);
        if (toNull == null) {
            return value;
        }
        return toNull;
    }


    public static String get(ContentValues map, String key) {
        return toNull(map, key);
    }


    public static String getEmpty(ContentValues map, String key, String value) {
        String toNull = get(map, key, "");
        if (toNull == null) {
            return value == null ? "" : value;
        }
        return toNull;
    }


    public static String getEmpty(ContentValues map, String key) {
        return getEmpty(map, key, "");
    }

    public static int getInt(ContentValues map, String key, int value) {
        String toNull = get(map, key);
        if (toNull == null) {
            return value;
        }
        try {
            return Integer.parseInt(toNull);
        } catch (NumberFormatException e) {
            return value;
        }
    }

    public static int getInt(ContentValues map, String key) {
        return getInt(map, key, 0);
    }

    public static long getLong(ContentValues map, String key, long value) {
        String toNull = get(map, key);
        if (toNull == null) {
            return value;
        }
        try {
            return Long.parseLong(toNull);
        } catch (NumberFormatException e) {
            return value;
        }
    }

    public static long getLong(ContentValues map, String key) {
        return getLong(map, key, 0L);
    }

    public static double getDouble(ContentValues map, String key, double value) {
        String toNull = get(map, key);
        if (toNull == null) {
            return value;
        }
        try {
            return Double.parseDouble(StringUtils.deleteEndZero(toNull));
        } catch (NumberFormatException e) {
            return value;
        }
    }

    public static double getDouble(ContentValues map, String key) {
        return getDouble(map, key, 0D);
    }


    public static String getRate(ContentValues map, String key, String defaultValue) {
        String value = toNull(map, key);
        if (value == null) {
            if (defaultValue == null || defaultValue.length() == 0) {
                return "-";
            }
            return defaultValue;
        }
        if (value.contains("%")) {
            return value;
        }
        return StringUtils.deleteEndZero(value) + "%";
    }


    public static String getRate(ContentValues map, String key) {
        return getRate(map, key, "-");
    }


    public static String getTime(ContentValues map, String key, String defaultValue) {
        String value = toNull(map, key);
        if (value == null) {
            return defaultValue;
        }
        return StringUtils.toTime(value);
    }


    public static String getTime(ContentValues map, String key) {
        return getTime(map, key, null);
    }

    /**
     * 将ContentValues转换成Map
     *
     * @param map
     * @return
     */
    public static Map<String, Object> convert(ContentValues values) {
        Map<String, Object> map = new HashMap<>();
        Set<String> keySet = values.keySet();
        for (String key : keySet) {
            map.put(key, getEmpty(values, key));
        }
        return map;
    }
    /**
     * 将ContentValues转换成Map
     *
     * @param map
     * @return
     */
    public static Map<String, String> convertString(ContentValues values) {
        Map<String, String> map = new HashMap<>();
        Set<String> keySet = values.keySet();
        for (String key : keySet) {
            map.put(key, getEmpty(values, key));
        }
        return map;
    }
}
