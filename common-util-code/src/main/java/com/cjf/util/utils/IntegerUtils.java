package com.cjf.util.utils;

import androidx.annotation.Nullable;

/**
 * <p>Title: DoubleUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/1/22 19:55
 */
public class IntegerUtils {


    /**
     * 将Integer转换为int类型
     *
     * @param value 目标值
     */
    public static int toIntZero(@Nullable final Integer value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

}
