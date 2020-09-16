package com.cjf.ui.bounce;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
public class BounceRecyclerView extends RecyclerView implements BounceAction {

    private BounceDelegate mBounceDelegate;

    public BounceRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public BounceRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBounceDelegate = new BounceDelegate(this);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BounceRecyclerView, defStyleAttr, 0);
        try {
            int orientation = array.getInt(R.styleable.BounceRecyclerView_BounceOrientation, LinearLayout.HORIZONTAL);
            float resistance = array.getFloat(R.styleable.BounceRecyclerView_BounceResistance, 3f);
            int duration = array.getInteger(R.styleable.BounceRecyclerView_BounceDuration, 300);
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

    public <VH extends ViewHolder> void setLinearVerticalAdapter(@NonNull RecyclerView.Adapter<VH> adapter) {
        setLayoutManager(new LinearLayoutManager(getContext()));
        setItemAnimator(new DefaultItemAnimator());
        setAdapter(adapter);
    }

    public <VH extends ViewHolder> void setLinearHorizontalAdapter(@NonNull RecyclerView.Adapter<VH> adapter) {
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        setItemAnimator(new DefaultItemAnimator());
        setAdapter(adapter);
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
