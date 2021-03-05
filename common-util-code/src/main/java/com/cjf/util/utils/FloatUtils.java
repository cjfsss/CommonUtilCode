package com.cjf.util.utils;

import androidx.annotation.Nullable;

/**
 * <p>Title: FloatUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/1/22 19:55
 */
public class FloatUtils {


    /**
     * 将Float转换为float类型
     *
     * @param value 目标值
     */
    public static float toFloatZero(@Nullable final Float value) {
        if (value == null) {
            return 0f;
        }
        return value;
    }
}
