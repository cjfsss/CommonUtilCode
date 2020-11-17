package com.cjf.util;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.module.LoadMoreModuleConfig;
import com.cjf.base.adapter.DefaultLoadMoreView;
import com.cjf.base.picture.PictureSelectorEngineImpl;
import com.cjf.util.function.UtilFunction;
import com.cjf.util.log.DefaultLogDelegateImpl;
import com.cjf.util.log.LogX;
import com.cjf.util.path.PathManager;
import com.cjf.util.toast.DefaultToastDelegateImpl;
import com.cjf.util.toast.ToastX;
import com.cjf.util.utils.ResUtils;
import com.luck.picture.lib.app.IApp;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.engine.PictureSelectorEngine;
import com.lxj.xpopup.XPopup;

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
        LogUtils.getConfig().setLog2FileSwitch(AppUtils.isAppDebug()).setGlobalTag("Code");
        CrashUtils.init(PathManager.getLogDir(), new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(String crashInfo, Throwable e) {
                LogX.printErrStackTrace("Crash", e, crashInfo);
            }
        });
        ToastX.setDelegete(new DefaultToastDelegateImpl());
        ToastUtils.setBgColor(ResUtils.getColor(R.color.black));
        ToastUtils.setMsgTextSize(16);
        ToastUtils.setMsgColor(ResUtils.getColor(R.color.design_white));
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
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
