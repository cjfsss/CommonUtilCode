package com.cjf.util.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

/**
 * <p>Title: StateListUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/2 10:08
 */
public class StateListContext {

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateList(Context context, @ColorRes int color) {
        return createColorStateList(ResContext.getColor(context, color));
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateList(Context context, @ColorRes int normal, @ColorRes int pressed) {
        return createColorStateList(ResContext.getColor(context, normal), ResContext.getColor(context, pressed));
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateList(Context context, @ColorRes int normal, @ColorRes int pressed, @ColorRes int unable) {
        return createColorStateList(ResContext.getColor(context, normal), ResContext.getColor(context, pressed), ResContext.getColor(context, unable));
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateList(Context context, @ColorRes int normal, @ColorRes int pressed, @ColorRes int focused, @ColorRes int unable) {
        return createColorStateList(ResContext.getColor(context, normal), ResContext.getColor(context, pressed), ResContext.getColor(context, focused), ResContext.getColor(context, unable));
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateList(@ColorInt int color) {
        return ColorStateList.valueOf(color);
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateList(@ColorInt int normal, @ColorInt int pressed) {
        int[] colors = new int[]{pressed, normal};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{};
        return new ColorStateList(states, colors);
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateList(@ColorInt int normal, @ColorInt int pressed, @ColorInt int unable) {
        int[] colors = new int[]{pressed, unable, normal};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        return new ColorStateList(states, colors);
    }

    // 设置不同状态时其文字颜色。
    @NonNull
    public static ColorStateList createColorStateList(@ColorInt int normal, @ColorInt int pressed, @ColorInt int focused, @ColorInt int unable) {
        int[] colors = new int[]{pressed, focused, normal, focused, unable, normal};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        return new ColorStateList(states, colors);
    }

    /**
     * 设置Selector。
     */
    @NonNull
    public static StateListDrawable createSelector(Context context, @DrawableRes int idNormal, @DrawableRes int idPressed, @DrawableRes int idFocused,
                                                   @DrawableRes int idUnable) {

        Drawable normal = idNormal == -1 ? null : ActivityCompat.getDrawable(context, idNormal);
        Drawable pressed = idPressed == -1 ? null : ActivityCompat.getDrawable(context, idPressed);
        Drawable focused = idFocused == -1 ? null : ActivityCompat.getDrawable(context, idFocused);
        Drawable unable = idUnable == -1 ? null : ActivityCompat.getDrawable(context, idUnable);
        return createSelector(normal, pressed, focused, unable);
    }

    // 设置Selector
    @NonNull
    public static StateListDrawable createSelector(Drawable normal, Drawable pressed, Drawable focused, Drawable unable) {
        StateListDrawable bg = new StateListDrawable();
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_focused}, focused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_window_focused}, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }
}
