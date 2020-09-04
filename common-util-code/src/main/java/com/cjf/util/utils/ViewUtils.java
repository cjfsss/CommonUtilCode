package com.cjf.util.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * <p>Title: ViewUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/8 15:29
 */
public class ViewUtils {

    private static final int KEY_OFFSET = -123;

    /**
     * 设置输入内容只能为数字或者字母
     */
    @SuppressWarnings("SpellCheckingInspection")
    public static void setNumberWord(TextView... textView) {
        if (textView == null) {
            return;
        }
        for (TextView view : textView) {
            view.setKeyListener(
                    DigitsKeyListener.getInstance("0123456789qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM"));
            view.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            view.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    public static void setVisibility(int visibility, View... views) {
        if (views == null) {
            return;
        }
        for (View view : views) {
            view.setVisibility(visibility);
        }
    }

    public static void setEnabled(boolean enable, View... views) {
        if (views == null) {
            return;
        }
        for (View view : views) {
            view.setEnabled(enable);
        }
    }

    public static String getText(@NonNull TextView textView) {
        if (textView == null || TextUtils.isEmpty(textView.getText())) {
            return "";
        }
        return textView.getText().toString().trim();
    }

    public static void clearText(TextView... textView) {
        if (textView == null) {
            return;
        }
        for (TextView view : textView) {
            view.setText("");
        }
    }

    public static void clearHint(TextView... textView) {
        if (textView == null) {
            return;
        }
        for (TextView view : textView) {
            view.setHint("");
        }
    }

    public final static void setOnClickListener(View.OnClickListener onClick, View... views) {
        if (views == null) {
            return;
        }
        for (View view : views) {
            view.setOnClickListener(onClick);
        }
    }

    public static void setCommonPaddingTopBottom(@NonNull View view) {
        int leftRight = (int) dp2px(view.getContext(), 0);
        int topBottom = (int) dp2px(view.getContext(), 8);
        setPadding(view, leftRight, topBottom);
    }

    public static void setCommonPaddingLeftRight(@NonNull View view) {
        int leftRight = (int) dp2px(view.getContext(), 16);
        int topBottom = (int) dp2px(view.getContext(), 0);
        setPadding(view, leftRight, topBottom);
    }

    public static void setCommonPadding(@NonNull View view) {
        int leftRight = (int) dp2px(view.getContext(), 16);
        int topBottom = (int) dp2px(view.getContext(), 8);
        setPadding(view, leftRight, topBottom);
    }

    public static void setPadding(@NonNull View view, int leftRight, int topBottom) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            view.setPaddingRelative(leftRight, topBottom, leftRight, topBottom);
        } else {
            view.setPadding(leftRight, topBottom, leftRight, topBottom);
        }
    }

    public static float dp2px(@NonNull Context context, float i) {
        return (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }

    public static float sp2px(@NonNull Context context, float i) {
        return (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, i, context.getResources().getDisplayMetrics());
    }

    /**
     * Add the top margin size equals status bar's height for view.
     *
     * @param view The view.
     */
    public static void addMarginHeight(@NonNull View view, int left, int top, int right, int bottom) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Object haveSetOffset = view.getTag(KEY_OFFSET);
        if (haveSetOffset != null && (Boolean) haveSetOffset) {
            return;
        }
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin + left, layoutParams.topMargin + top,
                                layoutParams.rightMargin + right, layoutParams.bottomMargin + bottom);
        view.setTag(KEY_OFFSET, true);
    }

    public static void addMarginTopHeight(@NonNull View view, int top) {
        addMarginHeight(view, 0, top, 0, 0);
    }

    public static void addMarginBottomHeight(@NonNull View view, int bottom) {
        addMarginHeight(view, 0, 0, 0, bottom);
    }

}
