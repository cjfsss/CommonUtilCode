package com.cjf.ui.bounce;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * <p>Title: ElasticNestedScrollView </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/4/9 17:45
 */
public class BounceViewPager2 extends BounceFrameLayout {

    private TabLayoutMediator mTabLayoutMediator;

    public BounceViewPager2(@NonNull Context context) {
        this(context, null);
    }

    public BounceViewPager2(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceViewPager2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setupWithTabLayoutViewPager2(@NonNull TabLayout tabLayout, @NonNull ViewPager2 viewPager,
            @NonNull TabLayoutMediator.TabConfigurationStrategy tabConfigurationStrategy) {
        setupWithTabLayoutViewPager2(tabLayout, viewPager, true, tabConfigurationStrategy);
    }

    public void setupWithTabLayoutViewPager2(@NonNull TabLayout tabLayout, @NonNull ViewPager2 viewPager,
            boolean autoRefresh, @NonNull TabLayoutMediator.TabConfigurationStrategy tabConfigurationStrategy) {
        mTabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, autoRefresh, tabConfigurationStrategy);
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabLayoutMediator != null) {
            mTabLayoutMediator.attach();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabLayoutMediator != null) {
            mTabLayoutMediator.detach();
        }
    }

}
