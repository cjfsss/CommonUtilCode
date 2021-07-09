package com.cjf.base.notify;

import androidx.annotation.Nullable;

/**
 * <p>Title: BaseNotificationData </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/12 12:01
 */
public interface BaseNotificationData {
    int getSmallIcon();

    @Nullable
    CharSequence getContentTitle();

    @Nullable
    CharSequence getContentText();

    int getNotifyId();
}
