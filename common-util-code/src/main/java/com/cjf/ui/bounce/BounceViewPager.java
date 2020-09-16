package com.cjf.ui.bounce;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.ViewPager;

import com.cjf.util.R;

/**
 * <p>Title: ElasticNestedScrollView </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/4/9 17:45
 */
public class BounceViewPager extends ViewPager implements BounceAction {

    private BounceDelegate mBounceDelegate;

    public BounceViewPager(@NonNull Context context) {
        this(context, null);
    }

    public BounceViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        mBounceDelegate = new BounceDelegate(this);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BounceViewPager, defStyleAttr, 0);
        try {
            int orientation = array.getInt(R.styleable.BounceViewPager_BounceOrientation, LinearLayout.HORIZONTAL);
            float resistance = array.getFloat(R.styleable.BounceViewPager_BounceResistance, 3f);
            int duration = array.getInteger(R.styleable.BounceViewPager_BounceDuration, 300);
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
