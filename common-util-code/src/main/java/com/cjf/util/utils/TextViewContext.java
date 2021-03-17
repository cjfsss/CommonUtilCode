package com.cjf.util.utils;

import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;

/**
 * <p>Title: TextViewContext </p>
 * <p>Description: 文字 </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/2/27 21:25
 */
public final class TextViewContext {

    public static void setTextSizeRes(@NonNull TextView view,
                                      @DimenRes int id) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResContext.getTextSize(view.getContext(), id));
    }

    public static void setTextSize(@NonNull TextView view,
                                   float size) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView,
                                                                       @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end,
                                                                       @Nullable Drawable bottom) {
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(textView, start, top, end, bottom);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView,
                                                                       @DrawableRes int start, @DrawableRes int top, @DrawableRes int end,
                                                                       @DrawableRes int bottom) {
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(textView, start, top, end, bottom);
    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintListRes(@NonNull TextView view,
                                           @ColorRes int colorId) {
        TextViewCompat.setCompoundDrawableTintList(view,
                StateListContext.createColorStateList(view.getContext(), colorId));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintList(@NonNull TextView view,
                                        @ColorInt int colorId) {
        TextViewCompat.setCompoundDrawableTintList(view,
                StateListContext.createColorStateList(colorId));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintListRes(@NonNull TextView view,
                                           @ColorRes int normal, @ColorRes int pressed) {
        TextViewCompat.setCompoundDrawableTintList(view,
                StateListContext.createColorStateList(view.getContext(), normal, pressed));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintList(@NonNull TextView view,
                                        @ColorInt int normal, @ColorInt int pressed) {
        TextViewCompat.setCompoundDrawableTintList(view,
                StateListContext.createColorStateList(normal, pressed));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintListRes(@NonNull TextView view,
                                           @ColorRes int normal, @ColorRes int pressed, @ColorRes int unable) {
        TextViewCompat.setCompoundDrawableTintList(view,
                StateListContext.createColorStateList(view.getContext(), normal, pressed, unable));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintList(@NonNull TextView view,
                                        @ColorInt int normal, @ColorInt int pressed, @ColorInt int unable) {
        TextViewCompat.setCompoundDrawableTintList(view,
                StateListContext.createColorStateList(normal, pressed, unable));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintListRes(@NonNull TextView view,
                                           @ColorRes int normal, @ColorRes int pressed, @ColorRes int focused, @ColorRes int unable) {
        TextViewCompat.setCompoundDrawableTintList(view,
                StateListContext.createColorStateList(view.getContext(), normal, pressed, focused, unable));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintList(@NonNull TextView view,
                                        @ColorInt int normal, @ColorInt int pressed, @ColorInt int focused, @ColorInt int unable) {
        TextViewCompat.setCompoundDrawableTintList(view,
                StateListContext.createColorStateList(normal, pressed, focused, unable));

    }
}
