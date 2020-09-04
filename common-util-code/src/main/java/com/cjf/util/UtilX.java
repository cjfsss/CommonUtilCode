package com.cjf.util;

import android.app.Application;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.cjf.util.function.UtilFunction;
import com.cjf.util.log.LogX;
import com.cjf.util.toast.ToastX;

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
        LogX.setDelegate(function.getLogDelegate());
        ToastX.setDelegete(function.getToastDelegate());
    }


    @NonNull
    public static Application getApplication() {
        return utilFunction.getApplication();
    }

    public static int getPrimaryColor() {
        return utilFunction.getPrimaryColor();
    }
}
