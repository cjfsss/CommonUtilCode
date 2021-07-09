package com.cjf.base.notify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cjf.base.app.AppCompatApplication;
import com.cjf.base.notify.listener.PendingIntentListener;
import com.cjf.base.notify.view.BaseRemoteViews;
import com.cjf.base.notify.view.BigRemote;
import com.cjf.base.notify.view.ContentRemote;
import com.cjf.base.notify.view.CustomRemote;
import com.cjf.base.notify.view.TickerRemote;

/**
 * <p>Title: BaseNotification </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/12 12:02
 */
public abstract class BaseNotification<T extends BaseNotificationData> implements IBaseNotify<T> {

    private T data;

    public BaseNotification(T data) {
        this.data = data;
        initChannel();
        initBuilder();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private AppCompatApplication getApplication() {
        return AppCompatApplication.getAppCompatApplication();
    }

    /**
     * Manager
     */
    private NotificationManagerCompat getNotificationManagerCompat() {
        return NotificationControl.getInstance().getNotificationManagerCompat();
    }

    /**
     * 通知栏构造器
     */
    @NonNull
    private NotificationCompat.Builder mBuilder;

    /**
     * 自定义视图管理类
     */
    private final BaseRemoteViews mBaseRemoteViews = new BaseRemoteViews();

    @Override
    public void initChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //获取用户的渠道id
            @Nullable
            NotificationChannel notificationChannel =
                    getNotificationManagerCompat().getNotificationChannel(getChannelIdStr());

            if (notificationChannel == null) {

                //为用户配置默认渠道 让用户配置 并创建 如果渠道已经创建了，就不做任何操作 不重建渠道
                notificationChannel = new NotificationChannel(
                        getChannelIdStr(),
                        getChannelName(),
                        NotificationManager.IMPORTANCE_DEFAULT
                );

                configureChannel(notificationChannel);

                getNotificationManagerCompat().createNotificationChannel(notificationChannel);
            }
        }
    }

    @Override
    public void initBuilder() {
        mBuilder = new NotificationCompat.Builder(getApplication().getBaseContext(), getChannelIdStr());
        configureNotify(mBuilder);
    }

    /**
     * 显示
     */
    @Override
    public void show() {
        show(data);
    }

    /**
     * 显示 并配置数据
     */
    @Override
    public void show(T data) {
        this.data = data;
        convert(mBaseRemoteViews, data);
        configureRemote();
        getNotificationManagerCompat().notify(getNotificationId(), mBuilder.build());
    }

    /**
     * 显示为前台通知
     * android.permission.FOREGROUND_SERVICE
     */
    @Override
    public void show(Service service) {
        show(service, data);
    }

    /**
     * 显示为前台通知 并配置数据
     * android.permission.FOREGROUND_SERVICE
     */
    @Override
    public void show(Service service, T data) {
        this.data = data;
        convert(mBaseRemoteViews, data);
        configureRemote();
        service.startForeground(getNotificationId(), mBuilder.build());
    }

    /**
     * 显示为前台通知并设置 service type
     * android.permission.FOREGROUND_SERVICE
     */
    @Override
    public void show(Service service, int foregroundServiceType) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            show(service, foregroundServiceType, data);
        } else {
            show(service, data);
        }
    }

    /**
     * 显示为前台通知并设置 并配置数据
     * android.permission.FOREGROUND_SERVICE
     */
    @Override
    @RequiresApi(Build.VERSION_CODES.Q)
    public void show(Service service, int foregroundServiceType, T data) {
        this.data = data;
        convert(mBaseRemoteViews, data);
        configureRemote();
        service.startForeground(getNotificationId(), mBuilder.build(), foregroundServiceType);
    }

    /**
     * 取消这个通知
     */
    @Override
    public void cancel() {
        getNotificationManagerCompat().cancel(getNotificationId());
    }

    @Override
    public void cancel(String tag) {
        getNotificationManagerCompat().cancel(tag, getNotificationId());
    }

    @Override
    public void cancelAllNotify() {
        getNotificationManagerCompat().cancelAll();
    }

    @Override
    public void addPendingIntentListener(PendingIntentListener pendingIntentListener) {
        NotificationControl.getInstance().addPendingIntentListener(pendingIntentListener);
    }

    @Override
    public void removePendingIntentListener(PendingIntentListener pendingIntentListener) {
        NotificationControl.getInstance().removePendingIntentListener(pendingIntentListener);
    }

    /**
     * 获取srt类型的ChannelId 形式为 {你的app报名:getChannelId()}
     */
    private String getChannelIdStr() {
        return getApplication().getPackageName() + ":" + getChannelId();
    }

    /**
     * 添加大图
     */
    protected void addBigRemoteViews(int layoutId) {
        mBaseRemoteViews.bigRemote = new BigRemote(layoutId);
    }

    /**
     * 添加小图
     */
    protected void addContentRemoteViews(int layoutId) {
        mBaseRemoteViews.contentRemote = new ContentRemote(layoutId);
    }

    /**
     * 添加TickerView
     */
    protected void addTickerRemoteViews(String tickerText, int layoutId) {
        mBaseRemoteViews.tickerRemote = new TickerRemote(tickerText, layoutId);
    }

    /**
     * 添加自定义视图
     */
    protected void addCustomContentRemoteView(int layoutId) {
        mBaseRemoteViews.customContentRemote = new CustomRemote(layoutId);
    }

    /**
     * 配置视图
     */
    private void configureRemote() {
        if (mBaseRemoteViews.customContentRemote != null) {
            mBuilder.setCustomContentView(mBaseRemoteViews.customContentRemote);
        }
        if (mBaseRemoteViews.contentRemote != null) {
            mBuilder.setContent(mBaseRemoteViews.contentRemote);
        }
        if (mBaseRemoteViews.bigRemote != null) {
            mBuilder.setCustomBigContentView(mBaseRemoteViews.bigRemote);
        }
        if (mBaseRemoteViews.tickerRemote != null) {
            if (!TextUtils.isEmpty(mBaseRemoteViews.tickerRemote.getTickerText())) {
                mBuilder.setTicker(mBaseRemoteViews.tickerRemote.getTickerText(), mBaseRemoteViews.tickerRemote);
            }
        }
    }

    /**
     * 配置数据
     */
    protected abstract void convert(BaseRemoteViews mBaseRemoteViews, T data);

    /**
     * 配置用户的渠道
     *
     * @param notificationChannel @RequiresApi(api = Build.VERSION_CODES.O)
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void configureChannel(NotificationChannel notificationChannel) {
        notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
    }

    /**
     * 配置通知信息
     */
    public void configureNotify(NotificationCompat.Builder mBuilder) {
        //设置通知的共同属性
        mBuilder.setShowWhen(true)
                .setSmallIcon(getData().getSmallIcon())
                .setContentTitle(getData().getContentTitle())
                .setContentText(getData().getContentText())
                .setTicker(getData().getContentTitle())
                .setContentInfo(getData().getContentText())
                .setAutoCancel(true)
                .setSubText(getData().getContentText())
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setGroup(getChannelName());
    }

    /**
     * 获取渠道名字 id已经默认为app的报名了
     */
    abstract String getChannelName();

    /**
     * 获取通知id
     */
    public int getNotificationId() {
        return data.getNotifyId();
    }

    /**
     * 获取渠道id  最终类型为 {你的app报名:0} 的字符串
     */
    abstract String getChannelId();
}
