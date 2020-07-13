package com.cjf.util.utils;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.cjf.util.UtilX;

/**
 * <p>Title: ServiceUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/2 11:22
 */
public class ServiceUtils {

    public static void startForegroundService(@NonNull Intent intent) {
        ContextCompat.startForegroundService(UtilX.application, intent);
    }

    public static void startForegroundService(@NonNull Class<?> cls, String action) {
        Intent intent = new Intent(UtilX.application, cls);
        intent.setAction(action);
        startForegroundService(intent);
    }

}
