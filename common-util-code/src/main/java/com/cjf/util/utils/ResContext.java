package com.cjf.util.utils;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.AnimatorRes;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;


/**
 * <p>Title: ils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/2 9:44
 */
public class ResContext {


    /**
     * 获取字符串
     */
    public static String getString(Context context, @StringRes int id) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getString(id);
        } else {
            return context.getResources().getString(id);
        }
    }

    /**
     * 获取字符串
     *
     * @return
     */
    public static String getString(Context context, @StringRes int id, Object... formatArgs) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getString(id, formatArgs);
        } else {
            return context.getResources().getString(id, formatArgs);
        }
    }

    public static float getTextSize(Context context, @DimenRes int id) {
        return context.getResources().getDimension(id);
    }

    public static CharSequence[] getTextArray(Context context, @ArrayRes int id) {

        return context.getResources().getTextArray(id);
    }

    public static String[] getStringArray(Context context, @ArrayRes int id) {
        return context.getResources().getStringArray(id);
    }

    public static int[] getIntArray(Context context, @ArrayRes int id) {
        return context.getResources().getIntArray(id);
    }

    public static int[] getMipmapArray(Context context, @ArrayRes int id) {
        @SuppressLint("Recycle") TypedArray array = context.getResources().obtainTypedArray(id);
        int len = array.length();
        int[] oaIcon = new int[len];
        for (int i = 0; i < len; i++) {
            oaIcon[i] = array.getResourceId(i, 0);
        }
        array.recycle();
        return oaIcon;
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int id) {
        return ContextCompat.getDrawable(context, id);
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int id) {
        return ContextCompat.getColorStateList(context, id);
    }

    @ColorInt
    public static int getColor(Context context, @ColorRes int id) {
        return ContextCompat.getColor(context, id);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static StateListAnimator getStateListAnimator(Context context, @AnimatorRes int id) {
        return AnimatorInflater.loadStateListAnimator(context, id);
    }

    /**
     * 判断是否是平板
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isTablet(@NonNull Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
