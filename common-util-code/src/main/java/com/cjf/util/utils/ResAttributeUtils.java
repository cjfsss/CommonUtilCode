package com.cjf.util.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cjf.util.R;

/**
 * <p>Title: ResAttributeUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/2 9:16
 */
public class ResAttributeUtils {

    /**
     * 获取主题中的属性
     *
     * @param resId 资源ID
     * @return
     */
    @NonNull
    public static TypedValue getAttribute(@NonNull Context context, int resId) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(resId, typedValue, true);
        return typedValue;
    }

    /**
     * 获取主题中的图标资源属性
     *
     * @param resId 资源ID
     * @return
     */
    public static int getAttributeRes(@NonNull Context context, int resId) {
        try {
            return getAttribute(context, resId).resourceId;
        } catch (Exception e) {
            return 0;
        }
    }

    public int getColorPrimary(@NonNull Context context) {
        return getAttribute(context, R.attr.colorPrimary).data;
    }

    /**
     * 获取主题中的字符串资源属性
     *
     * @param resId 资源ID
     * @return
     */
    @Nullable
    public static String getAttributeString(@NonNull Context context, int resId) {
        try {
            return String.valueOf(getAttribute(context, resId).coerceToString());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取主题中的图标资源属性
     *
     * @param resId 资源ID
     * @return
     */
    public static int getAttributeColor(@NonNull Context context, int resId) {
        try {
            return getColor(context, getAttributeRes(context, resId));
        } catch (Exception e) {
            return 0;
        }
    }

    private static int getColor(@NonNull Context context, int id) {
        return Build.VERSION.SDK_INT >= 23 ? context.getColor(id) : context.getResources().getColor(id);
    }

    /**
     * 获取主题中的图标资源属性
     *
     * @param resId 资源ID
     * @return
     */
    @Nullable
    public static Bitmap getAttributeBitmap(@NonNull Context context, int resId) {
        try {
            return getBitmap(context, getAttributeRes(context, resId));
        } catch (Exception e) {
            return null;
        }
    }

    @NonNull
    private static Bitmap getBitmap(@NonNull Context context, int id) {
        return BitmapFactory.decodeResource(context.getResources(), id);
    }

}
