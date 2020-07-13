package com.cjf.util.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.cjf.util.UtilX;

/**
 * <p>Title: DrawableTintUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/2 9:31
 */
public class DrawableTintUtils {

    /**
     * 获取Drawable
     *
     * @param id
     * @return
     */
    @Nullable
    private static Drawable getDrawable(@NonNull Context context, int id) {
        return ContextCompat.getDrawable(context, id);
    }

    @Nullable
    public static Drawable getTintDrawable(@NonNull Context context, int id, int color) {
        return tintDrawable(getDrawable(context, id), color);
    }

    @Nullable
    private static Drawable getTintListDrawable(@NonNull Context context, int id, @Nullable ColorStateList colors) {
        return tintListDrawable(getDrawable(context, id), colors);
    }

    @Nullable
    private static Drawable getTintListDrawable(@NonNull Context context, int id, int colorId) {
        return tintListDrawable(getDrawable(context, id), ContextCompat.getColorStateList(context, colorId));
    }

    /**
     * 获取Drawable
     */
    @Nullable
    public static Drawable getDrawable(int id) {
        return getDrawable(UtilX.getApplication(), id);
    }

    @Nullable
    public static Drawable getTintDrawablePrimary(@NonNull Context context, int id) {
        return getTintDrawable(context, id, ResUtils.getColorPrimary(context));
    }

    @Nullable
    public static Drawable getTintDrawable(int id, int color) {
        return getTintDrawable(UtilX.getApplication(), id, color);
    }

    @Nullable
    public static Drawable getTintListDrawable(int id, @Nullable ColorStateList colors) {
        return getTintListDrawable(UtilX.getApplication(), id, colors);
    }

    @Nullable
    public static Drawable getTintListDrawable(int id, int colorId) {
        return getTintListDrawable(UtilX.getApplication(), id, colorId);
    }

    /**
     * Drawable 颜色转化类
     *
     * @param drawable
     * @return 改变颜色后的Drawable
     */
    @Nullable
    public static Drawable tintDrawablePrimary(@NonNull Context context, @NonNull Drawable drawable) {
        return tintDrawable(drawable, ResUtils.getColorPrimary(context));
    }

    /**
     * Drawable 颜色转化类
     *
     * @param drawable 图片
     * @param color    颜色
     * @return 改变颜色后的Drawable
     */
    @Nullable
    public static Drawable tintDrawable(@Nullable Drawable drawable, @ColorInt int color) {
        if (drawable == null) {
            return null;
        }
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    /**
     * Drawable 颜色转化类
     *
     * @param drawable 源Drawable
     * @param colors
     * @return 改变颜色后的Drawable
     */
    @Nullable
    public static Drawable tintListDrawable(@Nullable Drawable drawable, @Nullable ColorStateList colors) {
        if (drawable == null) {
            return null;
        }
        if (colors == null) {
            return drawable;
        }
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

}
