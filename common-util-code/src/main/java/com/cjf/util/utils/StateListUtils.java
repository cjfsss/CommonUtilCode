package com.cjf.util.utils;

import android.app.Application;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;

/**
 * <p>Title: StateListUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/3/17 10:04
 */
public class StateListUtils {

    private static Application getApplication() {
        return Utils.getApp();
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateListRes(@ColorRes int color) {
        return StateListContext.createColorStateList(getApplication(), color);
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateListRes(@ColorRes int normal, @ColorRes int pressed) {
        return StateListContext.createColorStateList(getApplication(), normal, pressed);
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateListRes(@ColorRes int normal, @ColorRes int pressed, @ColorRes int unable) {
        return StateListContext.createColorStateList(getApplication(), normal, pressed, unable);
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateListRes(@ColorRes int normal, @ColorRes int pressed, @ColorRes int focused, @ColorRes int unable) {
        return StateListContext.createColorStateList(getApplication(), normal, pressed, focused, unable);
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateList(@ColorInt int normal, @ColorInt int pressed) {
        return StateListContext.createColorStateList(normal, pressed);
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateList(@ColorInt int normal, @ColorInt int pressed, @ColorInt int unable) {
        return StateListContext.createColorStateList(normal, pressed, unable);
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateList(@ColorInt int normal, @ColorInt int pressed, @ColorInt int focused, @ColorInt int unable) {
        return StateListContext.createColorStateList(normal, pressed, focused, unable);
    }

    /**
     * 设置Selector。
     */
    @NonNull
    public static StateListDrawable createSelector(@DrawableRes int idNormal, @DrawableRes int idPressed, @DrawableRes int idFocused,
                                                   @DrawableRes int idUnable) {

        return StateListContext.createSelector(getApplication(), idNormal, idPressed, idFocused, idUnable);
    }

    // 设置Selector
    @NonNull
    public static StateListDrawable createSelector(Drawable normal, Drawable pressed, Drawable focused, Drawable unable) {
        return StateListContext.createSelector(normal, pressed, focused, unable);
    }
}
