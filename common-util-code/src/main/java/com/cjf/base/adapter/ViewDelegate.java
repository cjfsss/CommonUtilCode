package com.cjf.base.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * <p>Title: ViewDelegate </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/4/11 15:19
 */
public class ViewDelegate {

    private SparseArray<View> mViews;
    private View mContentView;

    private ViewDelegate() {
    }

    public final static ViewDelegate create() {
        return new ViewDelegate();
    }

    /**
     * 记载布局文件
     *
     * @param context    上下文
     * @param layoutView 布局文件获取容器
     * @param container  父容器
     * @return
     */
    public View inflate(Context context, Object layoutView, ViewGroup container) {
        final View rootView;
        if (layoutView instanceof Integer) {
            if (container != null) {
                rootView = LayoutInflater.from(context).inflate((Integer) layoutView, container, false);
            } else {
                rootView = LayoutInflater.from(context).inflate((Integer) layoutView, null);
            }
        } else if (layoutView instanceof View) {
            rootView = (View) layoutView;
        } else {
            throw new RuntimeException("layoutView must be int or View!");
        }
        if (rootView == null) {
            throw new RuntimeException("layoutView can not be empty!");
        }
        return mContentView = rootView;
    }

    public <T extends View> T getView(int viewId) {
        return getView(mContentView, viewId);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(View contentView, int viewId) {
        View view = getViews().get(viewId);
        if (view == null) {
            view = contentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    private SparseArray<View> getViews() {
        if (mViews == null) {
            mViews = new SparseArray<>();
        }
        return mViews;
    }

    /**
     * 获取上下文
     *
     * @return 上下文，有可能为空
     */
    public Context getContext() {
        if (mContentView != null) {
            return mContentView.getContext();
        }
        return null;
    }

    /**
     * 获取根View
     *
     * @return
     */
    public View getContentView() {
        return mContentView;
    }

    public void setContentTag(Object tag){
        getContentView().setTag(tag);
    }

    /**
     * @param viewId
     * @param text
     * @return
     */
    public ViewDelegate setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    @SuppressLint("SetTextI18n")
    public ViewDelegate setTextAppend(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(tv.getText().toString() + text);
        return this;
    }


    public ViewDelegate setText(int viewId, Spannable text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewDelegate setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewDelegate setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewDelegate setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public ViewDelegate setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewDelegate setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewDelegate setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    @SuppressLint("ObsoleteSdkInt")
    public ViewDelegate setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public ViewDelegate setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewDelegate setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewDelegate setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public ViewDelegate setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewDelegate setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public ViewDelegate setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public ViewDelegate setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ViewDelegate setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewDelegate setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewDelegate setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public ViewDelegate setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewDelegate setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public ViewDelegate setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public void onDestroy() {
        if (mViews != null) {
            mViews.clear();
        }
        mContentView = null;
    }

}
