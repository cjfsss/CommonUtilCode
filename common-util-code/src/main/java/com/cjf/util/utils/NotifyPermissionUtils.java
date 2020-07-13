package com.cjf.util.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.cjf.util.log.LogX;
import com.cjf.util.toast.ToastX;


/**
 * <p>Title: NotifyPermission </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/2 15:43
 */
public class NotifyPermissionUtils {

    private static final String TAG = "NotifyPermission";

    public static void setOnNotifyPermissionListener(@NonNull Activity activity, @NonNull OnNotifyPermissionListener listener) {
        if (isNotificationListenersEnabled(activity)) {
            listener.onNotifyPermission();
        } else {
            gotoNotificationAccessSetting(activity);
        }
    }

    public static void onActivityResult(int requestCode, @NonNull Activity activity, @NonNull OnNotifyPermissionListener listener) {
        if (requestCode == 200) {
            setOnNotifyPermissionListener(activity, listener);
        }
    }

    private static boolean isNotificationListenersEnabled(@NonNull Activity context) {
        String pkgName = context.getPackageName();
        final String flat = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean gotoNotificationAccessSetting(@NonNull Activity context) {
        try {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivityForResult(intent, 200);
            return true;

        } catch (ActivityNotFoundException e) {//普通情况下找不到的时候需要再特殊处理找一次
            try {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.Settings$NotificationAccessSettingsActivity");
                intent.setComponent(cn);
                intent.putExtra(":settings:show_fragment", "NotificationAccessSettings");
                context.startActivityForResult(intent, 200);
                return true;
            } catch (Exception e1) {
                LogX.printErrStackTrace(TAG, e1, "Settings$NotificationAccessSettingsActivity not found");
            }
            ToastX.showShort("对不起，您的手机暂不支持");
            LogX.printErrStackTrace(TAG, e, "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS not found");
            return false;
        }
    }

    public interface OnNotifyPermissionListener {
        void onNotifyPermission();
    }
}
