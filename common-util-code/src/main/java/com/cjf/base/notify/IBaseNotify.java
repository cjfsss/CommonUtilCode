package com.cjf.base.notify;

import android.app.Service;

import com.cjf.base.notify.listener.PendingIntentListener;

/**
 * <p>Title: IBaseNotify </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/12 11:58
 */
public interface IBaseNotify<T extends BaseNotificationData> {
    /**
     * 初始化渠道
     */
    void initChannel();

    /**
     * 初始化通知构建器
     */
    void initBuilder();

    /**
     * 显示
     */
    void show();

    /**
     * 显示 并配置数据
     */
    void show(T data);

    /**
     * 显示为前台通知
     * android.permission.FOREGROUND_SERVICE
     */
    void show(Service service);

    /**
     * 显示为前台通知 并配置数据
     * android.permission.FOREGROUND_SERVICE
     */
    void show(Service service, T data);

    /**
     * 显示为前台通知并设置 service type
     * android.permission.FOREGROUND_SERVICE
     */
    void show(Service service, int foregroundServiceType);

    /**
     * 显示为前台通知并设置 并配置数据
     * android.permission.FOREGROUND_SERVICE
     */
    void show(Service service, int foregroundServiceType, T data);

    /**
     * 取消这个通知
     */
    void cancel();

    /**
     * 取消一个通知
     */
    void cancel(String tag);

    /**
     * 取消所有通知
     */
    void cancelAllNotify();

    /**
     * 添加点击事件监听器 监听所有通知
     */
    void addPendingIntentListener(PendingIntentListener pendingIntentListener);

    /**
     * 移除监听器
     */
    void removePendingIntentListener(PendingIntentListener pendingIntentListener);
}
