package com.cjf.util.utils;

import androidx.annotation.NonNull;
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
public class DoubleUtils {
    /**
     * 将Double转换为double类型
     *
     * @param value 目标值
     */
    public static double toDoubleZero(@Nullable final Double value) {
        if (value == null) {
            return 0.0D;
        }
        return value;
    }

    /**
     * 对入参保留最多两位小数(舍弃末尾的0)，如:
     * 3.345->3.34
     * 3.40->3.4
     * 3.0->3
     */
    public static double toDecimalFormat(@Nullable final Double number) {
        return Double.parseDouble(StringUtils.toDecimalFormat(number));
    }

    /**
     * 对入参保留最多几位小数(舍弃末尾的0)，如:
     * 3.345->3.34
     * 3.40->3.4
     * 3.0->3
     */
    public static double toDecimalFormat(@NonNull final String pattern, @Nullable final Double number) {
        return Double.parseDouble(StringUtils.toDecimalFormat(pattern, number));
    }
}
