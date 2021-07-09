package com.cjf.base.notify.view;

import androidx.annotation.Nullable;

import com.cjf.base.app.AppCompatApplication;

/**
 * <p>Title: BigRemote </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/12 11:55
 */
public class TickerRemote extends AbRemoteViews {
    private CharSequence tickerText;

    public TickerRemote(@Nullable CharSequence tickerText, int layoutId) {
        super(AppCompatApplication.getAppCompatApplication().getPackageName(), layoutId);
        this.tickerText = tickerText;
    }

    public void setTickerText(CharSequence tickerText) {
        this.tickerText = tickerText;
    }

    public CharSequence getTickerText() {
        return tickerText;
    }
}
