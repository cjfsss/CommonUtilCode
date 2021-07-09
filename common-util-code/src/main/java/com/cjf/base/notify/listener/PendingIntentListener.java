package com.cjf.base.notify.listener;

import androidx.annotation.IdRes;

/**
 * <p>Title: PendingIntentListener </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/12 11:14
 */
public interface PendingIntentListener {
    /**
     * @param notifyId 哪个通知栏的点击事件
     * @param viewId   点击的viewId
     */
    void onClick(int notifyId, @IdRes int viewId);
}
