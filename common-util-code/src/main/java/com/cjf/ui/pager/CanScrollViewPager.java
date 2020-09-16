package com.cjf.ui.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * <p>Title: CanScrollViewPager </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/3/17 20:07
 */
public class CanScrollViewPager extends ViewPager {
    private boolean isCanScroll = false;

    public CanScrollViewPager(@NonNull Context context) {
        this(context, null);
    }

    public CanScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 设置其是否能滑动换页
     *
     * @param canScroll false 不能滑动换页， true 可以滑动换页
     */
    public void setCanScroll(boolean canScroll) {
        isCanScroll = canScroll;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 不拦截，一般自定义的时候都不做处理，否则孩子都无法接收到事件。
        return super.dispatchTouchEvent(ev);
    }
    /**
     * 是否拦截
     * 拦截:会走到自己的onTouchEvent方法里面来
     * 不拦截:事件传递给子孩子
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // return false;//可行,不拦截事件,
        // return true;//不行,孩子无法处理事件
        if (isCanScroll) {
            int currentPosition = 0;
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    currentPosition = getCurrentItem();
                    int count = getAdapter().getCount();
                    // 如果滑动到第一页或者滑动到最后一页的话，就不拦截，否则拦截
                    if (currentPosition == 0 || currentPosition == count - 1) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    break;
            }
        }
        return isCanScroll && super.onInterceptTouchEvent(ev);
    }
    /**
     * 是否消费事件
     * 消费:事件就结束
     * 不消费:往父控件传
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //return false;// 可行,不消费,传给父控件
        //return true;// 可行,消费,拦截事件
        //虽然onInterceptTouchEvent中拦截了,
        //但是如果viewpage里面子控件不是viewgroup,还是会调用这个方法.
        return isCanScroll && super.onTouchEvent(ev);

    }

    @Override
    public void setCurrentItem(int item) {
        if (!isCanScroll) {
            //false 去除滚动效果
            super.setCurrentItem(item, false);
        } else {
            super.setCurrentItem(item);
        }
    }
}
