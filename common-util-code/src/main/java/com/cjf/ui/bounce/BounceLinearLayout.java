package com.cjf.ui.bounce;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.cjf.util.R;

/**
 * <p>Title: BounceLinearLayoutCompat </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/4/25 22:58
 */
public class BounceLinearLayout extends LinearLayout implements BounceAction {
    private BounceDelegate mBounceDelegate;

    public BounceLinearLayout(@NonNull Context context) {
        this(context, null);
    }

    public BounceLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        mBounceDelegate = new BounceDelegate(this);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BounceLinearLayout, defStyleAttr, 0);
        try {
            int orientation = array.getInt(R.styleable.BounceLinearLayout_BounceOrientation, LinearLayout.HORIZONTAL);
            float resistance = array.getFloat(R.styleable.BounceLinearLayout_BounceResistance, 3f);
            int duration = array.getInteger(R.styleable.BounceLinearLayout_BounceDuration, 300);
            if (resistance < 1) {
                resistance = 1f;
            }
            setOrientation(orientation);
            setDuration(duration);
            setResistance(resistance);
        } finally {
            array.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            mBounceDelegate.setTargetView(getChildAt(0));
        } else {
            throw new IllegalArgumentException("it must have innerView");
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mBounceDelegate.onInterceptTouchEvent(ev)) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mBounceDelegate.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public BounceDelegate getBounceDelegate() {
        return mBounceDelegate;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mBounceDelegate.onAttachedToWindow(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBounceDelegate.onDetachedFromWindow();
    }

}
