package com.cjf.base.notify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.cjf.base.app.AppCompatApplication;
import com.cjf.base.notify.listener.PendingIntentListener;
import com.cjf.util.singleton.ISingletonWrapper;
import com.cjf.util.singleton.SingletonManager;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: BaseNotifyBroadcast </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/12 12:09
 */
public class BaseNotifyBroadcast extends BroadcastReceiver implements ISingletonWrapper, BroadcastConstant {

    /**
     * 单例，弱引用
     *
     * @return 当前对象
     */
    public static BaseNotifyBroadcast getInstance() {
        return SingletonManager.get().getInstance(BaseNotifyBroadcast.class,
                input -> new BaseNotifyBroadcast());
    }

    /**
     * 通知栏点击事件分发监听器列表
     */
    private final List<PendingIntentListener> arrayList = new ArrayList<>();

    private AppCompatApplication getApplication() {
        return AppCompatApplication.getAppCompatApplication();
    }

    public BaseNotifyBroadcast() {
        register();
    }

    /**
     * 注册广播
     */
    public void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_NOTIFY_CLICK);
        getApplication().registerReceiver(this, intentFilter);
    }

    /**
     * 移除广播
     */
    public void onDestroy() {
        arrayList.clear();
        getApplication().unregisterReceiver(this);
    }

    /**
     * 添加监听器
     */
    public void addPendingIntentListener(PendingIntentListener pendingIntentListener) {
        if (!arrayList.contains(pendingIntentListener)) {
            arrayList.add(pendingIntentListener);
        }
    }

    /**
     * 移除点击事件监听器
     */
    @SuppressWarnings("RedundantCollectionOperation")
    public void removePendingIntentListener(PendingIntentListener pendingIntentListener) {
        if (arrayList.contains(pendingIntentListener)) {
            arrayList.remove(pendingIntentListener);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        if (TextUtils.equals(action, ACTION_NOTIFY_CLICK)) {
            int viewId = intent.getIntExtra(ACTION_NOTIFY_CLICK_VIEW_ID, 0);
            int notifyId = intent.getIntExtra(ACTION_NOTIFY_CLICK_NOTIFY_ID, -1);
            for (PendingIntentListener listener : arrayList) {
                if (listener != null) {
                    listener.onClick(notifyId, viewId);
                }
            }
        }
    }
}
