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
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.cjf.util.UtilX;


/**
 * <p>Title: ResUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/2 9:44
 */
public class ResUtils {

    /**
     * 获取colorPrimary的颜色,需要V7包的支持
     *
     * @return 0xAARRGGBB
     */
    public static int getColorPrimary() {
        return UtilX.getPrimaryColor();
    }


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
    public static boolean isTablet(Context context) {
        if (context == null) {
            context = UtilX.getApplication();
        }
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
               Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static Context getContext() {
        return UtilX.getApplication();
    }


    /**
     * 获取字符串
     */
    public static String getString(@StringRes int id) {
        return getString(getContext(), id);
    }

    /**
     * 获取字符串
     *
     * @return
     */
    public static String getString(@StringRes int id, Object... formatArgs) {
        return getString(getContext(), id, formatArgs);
    }

    public static CharSequence[] getTextArray(@ArrayRes int id) {
        return getTextArray(getContext(), id);
    }

    public static String[] getStringArray(@ArrayRes int id) {
        return getStringArray(getContext(), id);
    }

    public static int[] getIntArray(@ArrayRes int id) {
        return getIntArray(getContext(), id);
    }

    public static int[] getMipmapArray(@ArrayRes int id) {
        return getMipmapArray(getContext(), id);
    }

    @Nullable
    public static Drawable getDrawable(@DrawableRes int id) {
        return getDrawable(getContext(), id);
    }

    @Nullable
    public static ColorStateList getColorStateList(@ColorRes int id) {
        return getColorStateList(getContext(), id);
    }

    @ColorInt
    public static int getColor(@ColorRes int id) {
        return getColor(getContext(), id);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static StateListAnimator getStateListAnimator(@AnimatorRes int id) {
        return getStateListAnimator(getContext(), id);
    }

    /**
     * 判断是否是平板
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isTablet() {
        return (getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
               Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
