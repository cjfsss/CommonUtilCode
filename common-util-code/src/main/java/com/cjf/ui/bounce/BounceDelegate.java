package com.cjf.ui.bounce;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;

import com.cjf.ui.ViewLife;

/**
 * <p>Title: BounceDelegate </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/4/9 17:48
 */
public class BounceDelegate implements ViewLife {

    public static final int BOUNCE_TYPE_BOTH = 0;
    // 只有上面面回弹
    public static final int BOUNCE_TYPE_TOP = 1;
    // 只有下面回弹
    public static final int BOUNCE_TYPE_BOTTOM = 2;
    // 只有左边回弹
    public static final int BOUNCE_TYPE_LEFT = 3;
    // 只有右边回弹
    public static final int BOUNCE_TYPE_RIGHT = 4;
    // 禁止回弹
    public static final int BOUNCE_TYPE_DISABLE = 5;

    private int mTouchSlop;
    private int mDownX;
    private int mDownY;
    private boolean isIntercept;
    private View mTargetView;
    private float resistance;
    private int orientation;
    private long mDuration;
    private Interpolator mInterpolator;
    private boolean isNeedReset;
    private int resetDistance;
    private int mBounceType = 0;
    private OnBounceDistanceChangeListener mOnBounceDistanceChangeListener;

    public BounceDelegate(View targetView) {
        mTargetView = targetView;
        mTouchSlop = ViewConfiguration.get(mTargetView.getContext()).getScaledTouchSlop();
        mInterpolator = new AccelerateDecelerateInterpolator();
        resetDistance = Integer.MAX_VALUE;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mBounceType == BOUNCE_TYPE_DISABLE) {
            // 禁止回弹
            return false;
        }
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (mTargetView != null) {
                    mTargetView.clearAnimation();
                }
                mDownX = (int) ev.getX();
                mDownY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int difX = (int) (ev.getX() - mDownX);
                int difY = (int) (ev.getY() - mDownY);
                if (orientation == LinearLayout.HORIZONTAL) {
                    if (Math.abs(difX) > mTouchSlop && Math.abs(difX) > Math.abs(difY)) {
                        ViewParent parent = mTargetView.getParent();
                        while (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                            parent = parent.getParent();
                            isIntercept = true;
                        }
                        if (!mTargetView.canScrollHorizontally(-1) && difX > 0) {
                            if (mBounceType == BOUNCE_TYPE_RIGHT) {
                                // 只有右边回弹,左边回弹禁止
                                return false;
                            }
                            //右拉到边界，左边
                            return true;
                        }
                        if (!mTargetView.canScrollHorizontally(1) && difX < 0) {
                            if (mBounceType == BOUNCE_TYPE_LEFT) {
                                // 只有左边回弹,右边回弹禁止
                                return false;
                            }
                            //左拉到边界，右边
                            return true;
                        }
                    }
                } else {
                    // 上下回弹
                    if (Math.abs(difY) > mTouchSlop && Math.abs(difY) > Math.abs(difX)) {
                        ViewParent parent = mTargetView.getParent();
                        while (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                            parent = parent.getParent();
                            isIntercept = true;
                        }
                        if (!mTargetView.canScrollVertically(-1) && difY > 0) {
                            if (mBounceType == BOUNCE_TYPE_BOTTOM) {
                                // 只有下边回弹,上边回弹禁止
                                return false;
                            }
                            //下拉到边界,上边
                            return true;
                        }
                        if (!mTargetView.canScrollVertically(1) && difY < 0) {
                            if (mBounceType == BOUNCE_TYPE_TOP) {
                                // 只有上边回弹,下边回弹禁止
                                return false;
                            }
                            //上拉到边界,下边
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isIntercept) {
                    ViewParent parent = mTargetView.getParent();
                    while (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(false);
                        parent = parent.getParent();
                    }
                }
                isIntercept = false;
                mDownX = 0;
                mDownY = 0;
                break;
            default:
                break;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (mBounceType == BOUNCE_TYPE_DISABLE) {
            // 禁止回弹
            return false;
        }
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
                if (orientation == LinearLayout.HORIZONTAL) {
                    int difX = (int) ((event.getX() - mDownX) / resistance);
                    boolean isRebound = false;
                    if (!mTargetView.canScrollHorizontally(-1) && difX > 0) {
                        //右拉到边界,左边
                        isRebound = true;
                        if (mBounceType == BOUNCE_TYPE_RIGHT) {
                            // 只有右边回弹,左边回弹禁止
                            isRebound = false;
                        }
                    } else if (!mTargetView.canScrollHorizontally(1) && difX < 0) {
                        //左拉到边界，右边
                        isRebound = true;
                        if (mBounceType == BOUNCE_TYPE_LEFT) {
                            // 只有左边回弹,右边回弹禁止
                            isRebound = false;
                        }
                    }
                    if (isRebound) {
                        mTargetView.setTranslationX(difX);
                        if (mOnBounceDistanceChangeListener != null) {
                            mOnBounceDistanceChangeListener.onDistanceChange(Math.abs(difX), difX > 0 ?
                                    OnBounceDistanceChangeListener.DIRECTION_RIGHT : OnBounceDistanceChangeListener.DIRECTION_LEFT);
                        }
                        return true;
                    }
                } else {
                    int difY = (int) ((event.getY() - mDownY) / resistance);
                    boolean isRebound = false;
                    if (!mTargetView.canScrollVertically(-1) && difY > 0) {
                        //下拉到边界,上边
                        isRebound = true;
                        if (mBounceType == BOUNCE_TYPE_BOTTOM) {
                            // 只有下边回弹,上边回弹禁止
                            isRebound = false;
                        }
                    } else if (!mTargetView.canScrollVertically(1) && difY < 0) {
                        //上拉到边界，下边
                        isRebound = true;
                        if (mBounceType == BOUNCE_TYPE_TOP) {
                            // 只有上边回弹,下边回弹禁止
                            isRebound = false;
                        }
                    }
                    if (isRebound) {
                        mTargetView.setTranslationY(difY);
                        if (mOnBounceDistanceChangeListener != null) {
                            mOnBounceDistanceChangeListener.onDistanceChange(Math.abs(difY), difY > 0 ?
                                    OnBounceDistanceChangeListener.DIRECTION_DOWN : OnBounceDistanceChangeListener.DIRECTION_UP);
                        }
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (orientation == LinearLayout.HORIZONTAL) {
                    int difX = (int) mTargetView.getTranslationX();
                    if (difX != 0) {
                        if (Math.abs(difX) <= resetDistance || isNeedReset) {
                            mTargetView.animate().translationX(0).setDuration(mDuration).setInterpolator(mInterpolator);
                        }
                        if (mOnBounceDistanceChangeListener != null) {
                            mOnBounceDistanceChangeListener.onFingerUp(Math.abs(difX), difX > 0 ?
                                    OnBounceDistanceChangeListener.DIRECTION_RIGHT : OnBounceDistanceChangeListener.DIRECTION_LEFT);
                        }
                    }
                } else {
                    int difY = (int) mTargetView.getTranslationY();
                    if (difY != 0) {
                        if (Math.abs(difY) <= resetDistance || isNeedReset) {
                            mTargetView.animate().translationY(0).setDuration(mDuration).setInterpolator(mInterpolator);
                        }
                        if (mOnBounceDistanceChangeListener != null) {
                            mOnBounceDistanceChangeListener.onFingerUp(Math.abs(difY), difY > 0 ?
                                    OnBounceDistanceChangeListener.DIRECTION_DOWN : OnBounceDistanceChangeListener.DIRECTION_UP);
                        }
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }

    public void setBounceType(int bounceType) {
        mBounceType = bounceType;
    }

    public void setTargetView(View targetView) {
        this.mTargetView = targetView;
    }

    public void setResistance(float resistance) {
        this.resistance = resistance;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setDuration(long mDuration) {
        this.mDuration = mDuration;
    }

    public void setInterpolator(Interpolator mInterpolator) {
        this.mInterpolator = mInterpolator;
    }

    public void setNeedReset(boolean needReset) {
        isNeedReset = needReset;
    }

    public void setResetDistance(int resetDistance) {
        this.resetDistance = resetDistance;
    }

    public void setOnBounceDistanceChangeListener(OnBounceDistanceChangeListener onBounceDistanceChangeListener) {
        this.mOnBounceDistanceChangeListener = onBounceDistanceChangeListener;
    }

    @Override
    public void onAttachedToWindow(Object... objects) {
        for (Object object : objects) {
            if (object instanceof View) {
                mTargetView = (View) object;
            }
        }
        if (mTargetView != null) {
            mTouchSlop = ViewConfiguration.get(mTargetView.getContext()).getScaledTouchSlop();
            mInterpolator = new AccelerateDecelerateInterpolator();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        mInterpolator = null;
        mTargetView = null;
        mOnBounceDistanceChangeListener = null;
    }
}
