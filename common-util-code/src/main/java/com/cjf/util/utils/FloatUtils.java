package com.cjf.util.utils;

import androidx.annotation.NonNull;
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

    /**
     * 对入参保留最多两位小数(舍弃末尾的0)，如:
     * 3.345->3.34
     * 3.40->3.4
     * 3.0->3
     */
    public static float toDecimalFormat(@Nullable final Float number) {
        return Float.parseFloat(StringUtils.toDecimalFormat(number));
    }

    /**
     * 对入参保留最多几位小数(舍弃末尾的0)，如:
     * 3.345->3.34
     * 3.40->3.4
     * 3.0->3
     */
    public static float toDecimalFormat(@NonNull final String pattern, @Nullable final Float number) {
        return Float.parseFloat(StringUtils.toDecimalFormat(pattern, number));
    }
}
