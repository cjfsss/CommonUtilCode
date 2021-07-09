package com.cjf.base.notify;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresPermission;
import androidx.core.app.NotificationManagerCompat;

import com.cjf.base.app.AppCompatApplication;
import com.cjf.base.notify.listener.PendingIntentListener;
import com.cjf.util.singleton.ISingletonWrapper;
import com.cjf.util.singleton.SingletonWeakManager;

import java.lang.reflect.Method;

/**
 * <p>Title: NotificationControl </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/12 12:03
 */
public class NotificationControl implements ISingletonWrapper {

    /**
     * 单例，弱引用
     *
     * @return 当前对象
     */
    public static NotificationControl getInstance() {
        return SingletonWeakManager.get().getInstance(NotificationControl.class,
                input -> new NotificationControl());
    }

    private NotificationManagerCompat mNotificationManagerCompat;

    public NotificationManagerCompat getNotificationManagerCompat() {
        if (mNotificationManagerCompat == null) {
            mNotificationManagerCompat = NotificationManagerCompat.from(AppCompatApplication.getAppCompatApplication().getBaseContext());
        }
        return mNotificationManagerCompat;
    }


    /**
     * 是否开启通知栏权限
     */
    public boolean areNotificationsEnabled() {
        return getNotificationManagerCompat().areNotificationsEnabled();
    }


    /**
     * 添加点击事件监听器 监听所有通知
     */
    public void addPendingIntentListener(PendingIntentListener pendingIntentListener) {
        BaseNotifyBroadcast.getInstance().addPendingIntentListener(pendingIntentListener);
    }

    /**
     * 移除监听器
     */
    public void removePendingIntentListener(PendingIntentListener pendingIntentListener) {
        BaseNotifyBroadcast.getInstance().removePendingIntentListener(pendingIntentListener);
    }

    /**
     * 取消一个通知
     */
    public void cancel(int notifyId) {
        getNotificationManagerCompat().cancel(notifyId);
    }

    /**
     * 取消一个通知
     */
    public void cancel(int notifyId, String tag) {
        getNotificationManagerCompat().cancel(tag, notifyId);
    }

    /**
     * 取消所有通知
     */
    public void cancelAllNotify() {
        getNotificationManagerCompat().cancelAll();
    }

    /**
     * 调用此方法，会释放所有与通知相关的接口，通知，类
     */
    public void releaseAll() {
        getNotificationManagerCompat().cancelAll();
        BaseNotifyBroadcast.getInstance().onDestroy();
    }

    /**
     * 设置展开和折叠通知栏
     */
    @SuppressLint("ObsoleteSdkInt")
    @RequiresPermission(Manifest.permission.EXPAND_STATUS_BAR)
    public void setNotificationBarVisibility(boolean isVisible) {
        String methodName;
        if (isVisible) {
            if (Build.VERSION.SDK_INT <= 16) {
                methodName = "expand";
            } else methodName = "expandNotificationsPanel";
        } else {
            if (Build.VERSION.SDK_INT <= 16) methodName = "collapse";
            else methodName = "collapsePanels";
        }
        invokePanels(methodName);
    }

    private void invokePanels(String methodName) {
        try {
            @SuppressLint("WrongConstant") Object service =
                    getApplication().getSystemService("statusbar");
            @SuppressLint("PrivateApi") Class<?> statusBarManager =
                    Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AppCompatApplication getApplication() {
        return AppCompatApplication.getAppCompatApplication();
    }
}
