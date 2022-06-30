package com.cjf.util.utils;

import android.content.ContentValues;
import android.util.Pair;

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
public class MapUtils {
    /**
     * 将map转换为List
     *
     * @return List
     */

    public static <K, V> List<K> toListKey(Map<K, V> map) {
        if (map == null || map.isEmpty()) {
            return new LinkedList<>();
        }
        final Set<K> keySet = map.keySet();
        return new LinkedList<>(keySet);
    }

    /**
     * 将map转换为List
     *
     * @return List
     */

    public static <K, V> List<V> toListValue(Map<K, V> map) {
        if (map == null || map.isEmpty()) {
            return new ArrayList<V>();
        }
        final List<V> list = new ArrayList<>();
        final Set<K> keySet = map.keySet();
        final int size = keySet.size();
        int i = 0;
        for (K key : keySet) {
            V item = map.get(key);
            if (item == null) {
                continue;
            }
            list.add(item);
            i++;
        }
        return list;
    }


    public static <K, V> String toNull(Map<K, V> map, String key) {
        if (map == null) {
            return null;
        }
        Object object = map.get(key);
        if (object == null) {
            return null;
        }
        return StringUtils.toNULL(object);
    }


    public static <K, V> String get(Map<K, V> map, String key, String value) {
        String toNull = get(map, key);
        if (toNull == null) {
            return value;
        }
        return toNull;
    }


    public static <K, V> String get(Map<K, V> map, String key) {
        return toNull(map, key);
    }


    public static <K, V> String getEmpty(Map<K, V> map, String key, String value) {
        String toNull = get(map, key, value);
        if (toNull == null) {
            return value == null ? "" : value;
        }
        return toNull;
    }


    public static <K, V> String getEmpty(Map<K, V> map, String key) {
        return getEmpty(map, key, "");
    }

    public static <K, V> int getInt(Map<K, V> map, String key, int value) {
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

    public static <K, V> int getInt(Map<K, V> map, String key) {
        return getInt(map, key, 0);
    }

    public static <K, V> long getLong(Map<K, V> map, String key, long value) {
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

    public static <K, V> long getLong(Map<K, V> map, String key) {
        return getLong(map, key, 0L);
    }

    public static <K, V> double getDouble(Map<K, V> map, String key, double value) {
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

    public static <K, V> double getDouble(Map<K, V> map, String key) {
        return getDouble(map, key, 0D);
    }


    public static <K, V> String getRate(Map<K, V> map, String key, String defaultValue) {
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


    public static <K, V> String getRate(Map<K, V> map, String key) {
        return getRate(map, key, "-");
    }


    public static <K, V> String getTime(Map<K, V> map, String key, String defaultValue) {
        String value = toNull(map, key);
        if (value == null) {
            return defaultValue;
        }
        return StringUtils.toTime(value);
    }


    public static <K, V> String getTime(Map<K, V> map, String key) {
        return getTime(map, key, null);
    }


    public static List<Pair<String, Object>> toPair(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        Set<String> keySet = map.keySet();
        List<Pair<String, Object>> list = new ArrayList<>();
        for (String key : keySet) {
            list.add(new Pair<String, Object>(key, map.get(key)));
        }
        return list;
    }

    @SafeVarargs

    public static Map<String, Object> toMap(Pair<String, Object>... pairs) {
        if (pairs == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        for (Pair<String, Object> pair : pairs) {
            map.put(pair.first, pair.second);
        }
        return map;
    }

    /**
     * 将Map转换成ContentValues
     *
     * @param map
     * @return
     */
    public static ContentValues convert(Map<String, Object> map) {
        ContentValues values = new ContentValues();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            values.put(key, getEmpty(map, key));
        }
        return values;
    }

    /**
     * 将Map转换成ContentValues
     *
     * @param map
     * @return
     */
    public static ContentValues convertString(Map<String, String> map) {
        ContentValues values = new ContentValues();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            values.put(key, getEmpty(map, key));
        }
        return values;
    }
}
