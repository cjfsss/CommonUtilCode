package com.cjf.base.notify;

import com.cjf.base.app.AppCompatApplication;

/**
 * <p>Title: BroadcastConstant </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/12 11:47
 */
public interface BroadcastConstant {

    /**
     * 广播接收器 接收到的参数
     */
    final String ACTION_NOTIFY_CLICK_VIEW_ID = "viewID";
    /**
     * 点击事件触发的通知栏id
     */
    String ACTION_NOTIFY_CLICK_NOTIFY_ID = "notifyID";
    /**
     * 点击事件广播接收器接收 action
     */
    String ACTION_NOTIFY_CLICK =
            AppCompatApplication.getAppCompatApplication().getPackageName() + ".notify.click";
}
