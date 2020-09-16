package com.cjf.util.notify;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.cjf.util.UtilX;


/**
 * <p>Title: NotifyManager </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2019/12/26 23:11
 */
public interface NotifyManager {

    @NonNull
    default String getChannelId() {
        return getContext().getPackageName();
    }

    @NonNull
    default Context getContext() {
        return UtilX.getApplication();
    }

    @NonNull
    default NotificationCompat.Builder getBuilder() {
        return new NotificationCompat.Builder(getContext(), getChannelId());
    }

    @NonNull
    NotificationCompat.Builder getBuilder(@NonNull String title, @NonNull String content, int smallIcon,
            boolean isOngoing);

    @NonNull
    default NotificationCompat.Builder getForegroundBuilder(@NonNull String title, @NonNull String content,
            int smallIcon) {
        return getBuilder(title, content, smallIcon, true);
    }

    @NonNull
    default NotificationCompat.Builder getBuilder(@NonNull String title, @NonNull String content, int smallIcon) {
        return getBuilder(title, content, smallIcon, false);
    }

    @NonNull
    default NotificationCompat.Builder getProgressBuilder(@NonNull String title, @NonNull String content, int smallIcon) {
        return getBuilder(title, content, smallIcon, false)
                .setProgress(100, 50, true);
    }

    @NonNull
    default NotificationCompat.Builder getProgressBuilder(@NonNull String title, @NonNull String content, int smallIcon,
            int progress) {
        return getBuilder(title, content, smallIcon, false)
                .setProgress(100, progress, false);
    }

    @NonNull
    default NotificationCompat.Builder getForegroundProgressBuilder(@NonNull String title, @NonNull String content,
            int smallIcon, int progress) {
        return getBuilder(title, content, smallIcon, true)
                .setProgress(100, progress, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void startForeground(@NonNull Service service, int id, @NonNull Notification notification);

    default void notify(int id, @NonNull Notification notification) {
        notify(null, id, notification);
    }

    void notify(@Nullable String tag, int id, @NonNull Notification notification);

    default void cancel(int id) {
        cancel(null, id);
    }

    void cancel(@Nullable String tag, int id);

    void cancelAll();
}
