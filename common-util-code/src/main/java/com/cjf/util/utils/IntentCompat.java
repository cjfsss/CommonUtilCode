package com.cjf.util.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.cjf.base.app.AppCompatApplication;
import com.cjf.util.wps.WpsParams;

import java.io.File;
import java.util.List;

/**
 * <p>Title: IntentUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/7/12 13:45
 */
public class IntentCompat {

    /**
     * Return the name of launcher activity.
     *
     * @param pkg The name of the package.
     * @return the name of launcher activity
     */
    @NonNull
    public static String getLauncherActivity(@NonNull final String pkg) {
        if (StringUtils.isSpace(pkg)) return "";
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(pkg);
        PackageManager pm = AppCompat.getApp().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        if (info == null || info.size() == 0) {
            return "";
        }
        return info.get(0).activityInfo.name;
    }

    /**
     * Return the intent of launch app.
     *
     * @param pkgName The name of the package.
     * @return the intent of launch app
     */
    @Nullable
    public static Intent getLaunchAppIntent(final String pkgName) {
        String launcherActivity = getLauncherActivity(pkgName);
        if (StringUtils.isSpace(launcherActivity)) return null;
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClassName(pkgName, launcherActivity);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * Return the intent of launch app details settings.
     *
     * @param pkgName The name of the package.
     * @return the intent of launch app details settings
     */
    @NonNull
    public static Intent getLaunchAppDetailsSettingsIntent(final String pkgName) {
        return getLaunchAppDetailsSettingsIntent(pkgName, false);
    }

    /**
     * Return the intent of launch app details settings.
     *
     * @param pkgName The name of the package.
     * @return the intent of launch app details settings
     */
    @NonNull
    public static Intent getLaunchAppDetailsSettingsIntent(final String pkgName, final boolean isNewTask) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + pkgName));
        return getIntent(intent, isNewTask);
    }

    @NonNull
    private static Intent getIntent(final Intent intent, final boolean isNewTask) {
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

    @Nullable
    public static Intent getLaunchTargetOpenFile(@NonNull final String pkgName, @Nullable final String filePath) {
        AppCompatApplication application = AppCompat.getApp();
        return getLaunchTargetOpenFile(application, application.getPackageName() + ".provider", pkgName, filePath);
    }

    @Nullable
    public static Intent getLaunchTargetOpenFile(@NonNull final String pkgName, @Nullable final File file) {
        AppCompatApplication application = AppCompat.getApp();
        return getLaunchTargetOpenFile(application, application.getPackageName() + ".provider", pkgName, file);
    }

    @Nullable
    public static Intent getLaunchTargetOpenFile(@NonNull final String pkgName,
                                                 @NonNull String authority, @Nullable final String filePath) {
        return getLaunchTargetOpenFile(AppCompat.getApp(), authority, pkgName, filePath);
    }

    @Nullable
    public static Intent getLaunchTargetOpenFile(@NonNull final String pkgName,
                                                 @NonNull String authority, @Nullable final File file) {
        return getLaunchTargetOpenFile(AppCompat.getApp(), authority, pkgName, file);
    }

    @Nullable
    public static Intent getLaunchTargetOpenFile(@NonNull Context context, @NonNull final String pkgName,
                                                 @NonNull String authority, @Nullable final String filePath) {
        if (StringUtils.toNULL(filePath) == null) {
            return null;
        }
        return getLaunchTargetOpenFile(context, authority, pkgName, new File(filePath));
    }

    @Nullable
    public static Intent getLaunchTargetOpenFile(@NonNull Context context, @NonNull final String pkgName,
                                                 @NonNull String authority, @Nullable final File file) {
        Intent intent = getLaunchAppIntent(pkgName);
        if (intent == null) {
            return null;
        }
        final Uri uri;
        if (Build.VERSION.SDK_INT >= 23) {
            // 加入读取权限 android 7.0以上时，URI不能直接暴露
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        /* 依扩展名的类型决定MimeType */
        String type = FileCompat.getFileTypeString(file.getAbsolutePath());
        intent.setDataAndType(uri, type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Nullable
    public static Intent getLaunchOpenFile(@Nullable String filePath) {
        AppCompatApplication application = AppCompat.getApp();
        return getLaunchOpenFile(application, application.getPackageName() + ".provider", filePath);
    }

    @NonNull
    public static Intent getLaunchOpenFile(@Nullable File file) {
        AppCompatApplication application = AppCompat.getApp();
        return getLaunchOpenFile(application, application.getPackageName() + ".provider", file);
    }

    @Nullable
    public static Intent getLaunchOpenFile(@NonNull String authority, @Nullable String filePath) {
        return getLaunchOpenFile(AppCompat.getApp(), authority, filePath);
    }

    @NonNull
    public static Intent getLaunchOpenFile(@NonNull String authority, @NonNull File file) {
        return getLaunchOpenFile(AppCompat.getApp(), authority, file);
    }

    @Nullable
    public static Intent getLaunchOpenFile(@NonNull Context context, @NonNull String authority, @Nullable String filePath) {
        if (StringUtils.toNULL(filePath) == null) {
            return null;
        }
        return getLaunchOpenFile(context, authority, new File(filePath));
    }

    @NonNull
    public static Intent getLaunchOpenFile(@NonNull Context context, @NonNull String authority, @NonNull File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type = FileCompat.getFileTypeString(file.getAbsolutePath());
        final Uri uri;
        if (Build.VERSION.SDK_INT >= 23) {
            // 加入读取权限
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }

    @Nullable
    public static Intent getLaunchOpenWPS(@NonNull String file) {
        return getLaunchOpenWPS(AppCompat.getApp(), file);
    }

    @Nullable
    public static Intent getLaunchOpenWPS(@NonNull Context context, @NonNull String file) {
        return getLaunchOpenWPS(context, context.getPackageName() + ".provider", file);
    }

    @Nullable
    public static Intent getLaunchOpenWPS(@NonNull Context context, @NonNull String authority, @Nullable String filePath) {
        if (StringUtils.toNULL(filePath) == null) {
            return null;
        }
        return getLaunchOpenWPS(context, authority, new File(filePath));
    }

    @Nullable
    public static Intent getLaunchOpenWPS(@NonNull File file) {
        return getLaunchOpenWPS(AppCompat.getApp(), file);
    }

    @Nullable
    public static Intent getLaunchOpenWPS(@NonNull Context context, @NonNull File file) {
        return getLaunchOpenWPS(context, context.getPackageName() + ".provider", file);
    }

    @Nullable
    public static Intent getLaunchOpenWPS(@NonNull Context context, @NonNull String authority, @NonNull File file) {
        // wps存在,打开WPS
        Intent intent = IntentCompat.getLaunchTargetOpenFile(
                context, WpsParams.PackageName.NORMAL, authority, file);
        if (intent == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        //打开模式
        bundle.putString(WpsParams.OPEN_MODE, WpsParams.OpenMode.NORMAL); // 打开模式
        bundle.putBoolean(WpsParams.SEND_SAVE_BROAD, true);
        //关闭时是否发送广播
        bundle.putString(WpsParams.THIRD_PACKAGE, context.getPackageName());
        bundle.putBoolean(WpsParams.SEND_CLOSE_BROAD, true); // 关闭时是否发送广播
        //第三方应用的包名，用于对改应用合法性的验证
        bundle.putBoolean(WpsParams.CLEAR_TRACE, true);
        //清除打开记录
        //bundle.putBoolean(CLEAR_FILE, true);
        //关闭后删除打开文件
        intent.setAction(Intent.ACTION_VIEW);
        intent.setClassName(WpsParams.PackageName.NORMAL, WpsParams.ClassName.NORMAL);
        // bundle.putBoolean(CLEAR_FILE, true); //关闭后删除打开文件
        intent.putExtras(bundle);
        context.startActivity(intent);
        return intent;
    }

    /**
     * 打开应用市场意图
     *
     * @param packageName 要打开应用的包名
     * @return
     */
    @NonNull
    public static Intent getLaunchMarket(@NonNull String packageName) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
    }

    /**
     * 获取打开网页浏览器
     *
     * @param url 访问地址
     * @return
     */
    @NonNull
    public static Intent getLaunchUrl(@NonNull String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }
}
