package com.cjf.util.utils;

import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;

/**
 * <p>Title: ImageViewContext </p>
 * <p>Description: 图片染色 </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/2/27 21:25
 */
public final class ImageViewContext {

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintListRes(@NonNull ImageView view,
                                           @ColorRes int colorId) {
        ImageViewCompat.setImageTintList(view,
                StateListContext.createColorStateList(view.getContext(), colorId));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintList(@NonNull ImageView view,
                                        @ColorInt int colorId) {
        ImageViewCompat.setImageTintList(view,
                StateListContext.createColorStateList(colorId));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintListRes(@NonNull ImageView view,
                                           @ColorRes int normal, @ColorRes int pressed) {
        ImageViewCompat.setImageTintList(view,
                StateListContext.createColorStateList(view.getContext(), normal, pressed));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintList(@NonNull ImageView view,
                                        @ColorInt int normal, @ColorInt int pressed) {
        ImageViewCompat.setImageTintList(view,
                StateListContext.createColorStateList(normal, pressed));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintListRes(@NonNull ImageView view,
                                           @ColorRes int normal, @ColorRes int pressed, @ColorRes int unable) {
        ImageViewCompat.setImageTintList(view,
                StateListContext.createColorStateList(view.getContext(), normal, pressed, unable));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintList(@NonNull ImageView view,
                                        @ColorInt int normal, @ColorInt int pressed, @ColorInt int unable) {
        ImageViewCompat.setImageTintList(view,
                StateListContext.createColorStateList(normal, pressed, unable));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintListRes(@NonNull ImageView view,
                                           @ColorRes int normal, @ColorRes int pressed, @ColorRes int focused, @ColorRes int unable) {
        ImageViewCompat.setImageTintList(view,
                StateListContext.createColorStateList(view.getContext(), normal, pressed, focused, unable));

    }

    /**
     * Applies a tint to the image drawable.
     */
    public static void setImageTintList(@NonNull ImageView view,
                                        @ColorInt int normal, @ColorInt int pressed, @ColorInt int focused, @ColorInt int unable) {
        ImageViewCompat.setImageTintList(view,
                StateListContext.createColorStateList(normal, pressed, focused, unable));

    }
}
