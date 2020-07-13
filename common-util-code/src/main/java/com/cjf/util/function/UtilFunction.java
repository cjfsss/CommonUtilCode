package com.cjf.util.function;

import android.app.Application;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/**
 * <p>Title: UtilFunction </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/7/13 20:57
 */
public interface UtilFunction {

    @NonNull
    Application getApplication();

    @ColorInt
    int getPrimaryColor();
}
