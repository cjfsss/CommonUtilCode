package com.cjf.util.notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


/**
 * <p>Title: NotifyManagerCompat </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2019/12/26 22:36
 */
public final class NotifyManagerCompat implements NotifyManager {

    private static final String TAG = "NotifyManagerCompat";

    private NotificationManagerCompat mManagerCompat;
    private NotificationManager mNotificationManager;

    public static NotifyManager create() {
        return new NotifyManagerCompat();
    }

    @NonNull
    private NotificationManager getNotificationManager() {
        if (mNotificationManager == null) {
            return mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }

    @NonNull
    private NotificationManagerCompat getManagerCompat() {
        if (mManagerCompat == null) {
            mManagerCompat = NotificationManagerCompat.from(getContext());
        }
        return mManagerCompat;
    }

    public void notify(@Nullable String tag, int id, @NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = getNotificationChannel();
            if (notificationChannel == null) {
                getNotificationManager().createNotificationChannel(getNotificationChannelNew());
            }
            getNotificationManager().notify(tag, id, notification);
        } else {
            getManagerCompat().notify(tag, id, notification);
        }
    }

    public void cancel(@Nullable String tag, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getNotificationManager().cancel(tag, id);
        } else {
            getManagerCompat().cancel(tag, id);
        }
    }

    public void cancelAll() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getNotificationManager().cancelAll();
        } else {
            getManagerCompat().cancelAll();
        }
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationChannel getNotificationChannelNew() {
        return new NotificationChannel(getChannelId(),
                TAG, NotificationManager.IMPORTANCE_LOW);
    }

    @Nullable
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationChannel getNotificationChannel() {
        return getNotificationManager().getNotificationChannel(getChannelId());
    }

    @Nullable
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationChannel getNotificationChannel(@NonNull String channelId) {
        return getNotificationManager().getNotificationChannel(channelId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(@NonNull NotificationChannel channel) {
        getNotificationManager().createNotificationChannel(channel);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startForeground(@NonNull Service service, int id, @NonNull Notification notification) {
        getNotificationManager().createNotificationChannel(getNotificationChannelNew());
        service.startForeground(id, notification);
    }

    @NonNull
    public NotificationCompat.Builder getBuilder(@NonNull String title, @NonNull String content, int smallIcon, boolean isOngoing) {
        NotificationCompat.Builder builder = getBuilder();
        builder.setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(smallIcon)
                // 设置通知栏能否被清楚，true不能被清除，false可以被清除
                .setOngoing(isOngoing)
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        return builder;
    }

}
