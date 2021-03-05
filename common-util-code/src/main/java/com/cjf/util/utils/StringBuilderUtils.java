package com.cjf.util.utils;

import androidx.annotation.NonNull;

/**
 * <p>Title: StringBuilderUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/1/22 9:23
 */
public class StringBuilderUtils {

    @NonNull
    public static StringBuilder deleteEnd(@NonNull StringBuilder builder) {
        if (builder.length() > 1) {
            return builder.delete(builder.length() - 1, builder.length());
        }
        return builder;
    }
}
