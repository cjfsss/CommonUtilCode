package com.cjf.util;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.module.LoadMoreModuleConfig;
import com.cjf.base.adapter.DefaultLoadMoreView;
import com.cjf.base.picture.PictureSelectorEngineImpl;
import com.cjf.util.function.UtilFunction;
import com.cjf.util.log.DefaultLogDelegateImpl;
import com.cjf.util.log.LogX;
import com.cjf.util.toast.DefaultToastDelegateImpl;
import com.cjf.util.toast.ToastX;
import com.cjf.util.utils.ResUtils;
import com.luck.picture.lib.app.IApp;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.engine.PictureSelectorEngine;
import com.lxj.xpopup.XPopup;

import java.io.File;

/**
 * <p>Title: UtilX </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/7/13 20:46
 */
public class UtilX {

    private static UtilFunction utilFunction;

    public static Application application;

    @Keep
    public static void init(@NonNull UtilFunction function) {
        application = function.getApplication();
        UtilX.utilFunction = function;
        if (function.getApplication() instanceof IApp) {
            PictureAppMaster.getInstance().setApp((IApp) function.getApplication());
        } else {
            PictureAppMaster.getInstance().setApp(new IApp() {
                @Override
                public Context getAppContext() {
                    return getApplication();
                }

                @Override
                public PictureSelectorEngine getPictureSelectorEngine() {
                    return new PictureSelectorEngineImpl();
                }
            });
        }
        Utils.init(function.getApplication());
        LogX.setDelegate(new DefaultLogDelegateImpl());
        LogUtils.getConfig().setFileExtension(".log").setLog2FileSwitch(AppUtils.isAppDebug()).setGlobalTag("Code");
        String logDir = PathUtils.getExternalAppFilesPath();
        if (logDir == null) {
            logDir = PathUtils.getExternalAppCachePath();
            if (logDir == null) {
                logDir = PathUtils.getExternalDcimPath();
                if (logDir == null) {
                    logDir = PathUtils.getInternalAppFilesPath();
                }
            }
        }
        logDir = logDir + File.separator + "log";
        CrashUtils.init(logDir, new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(CrashUtils.CrashInfo crashInfo) {
                if (crashInfo != null) {
                    LogX.printErrStackTrace("Crash", crashInfo.getThrowable(), crashInfo);
                }
            }
        });
        ToastX.setDelegete(new DefaultToastDelegateImpl());
        ToastUtils.getDefaultMaker().setBgColor(ResUtils.getColor(R.color.black));
        ToastUtils.getDefaultMaker().setTextSize(16);
        ToastUtils.getDefaultMaker().setTextColor(ResUtils.getColor(R.color.design_white));
        ToastUtils.getDefaultMaker().setGravity(Gravity.CENTER, 0, 0);
        XPopup.setPrimaryColor(function.getPrimaryColor());
        LoadMoreModuleConfig.setDefLoadMoreView(new DefaultLoadMoreView());
    }


    @NonNull
    public static Application getApplication() {
        return utilFunction.getApplication();
    }

    public static int getPrimaryColor() {
        return utilFunction.getPrimaryColor();
    }
}
