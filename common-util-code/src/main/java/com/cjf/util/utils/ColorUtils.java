package com.cjf.util.utils;

import android.graphics.Color;

import androidx.annotation.NonNull;

/**
 * <p>Title: ColorUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/1 11:10
 */
public class ColorUtils {
    /**
     * 设置颜色的alpha值
     *
     * @param color 需要被设置的颜色值
     * @param alpha 取值为[0,1]，0表示全透明，1表示不透明
     * @return 返回改变了 alpha 值的颜色值
     */
    public static int setColorAlpha(int color, float alpha) {
        return color & 0x00ffffff | (int) (alpha * 255) << 24; // 清掉alpha信息后加上新的alpha信息
    }

    /**
     * 将 color 颜色值转换为十六进制字符串
     *
     * @param color 颜色值
     * @return 转换后的字符串
     */
    public static String colorToString(int color) {
        return String.format("#%08X", color);
    }

    /**
     * 将String转换为 color 颜色
     *
     * @param color 颜色值 字符串
     * @return 转换后的 color
     */
    public static int stringToColor(@NonNull String color) {
        return Color.parseColor(color);
    }
}
