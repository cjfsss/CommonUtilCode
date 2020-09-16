package com.cjf.util.extension

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.BarUtils

/**
 * <p>Title: Bar </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/14 10:33
 * @version : 1.0
 */

fun View.openStateBar(activity: Activity) {
    BarUtils.transparentStatusBar(activity)
    height(BarUtils.getStatusBarHeight() + BarUtils.getActionBarHeight())
    setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
}

fun View.openStateBarBgImg(activity: Activity) {
    BarUtils.transparentStatusBar(activity)
    height(BarUtils.getStatusBarHeight() * 2 + BarUtils.getActionBarHeight())
    setPadding(0, BarUtils.getStatusBarHeight(), 0, BarUtils.getStatusBarHeight())
}

fun View.openStateBar(fragment: Fragment) {
    BarUtils.transparentStatusBar(fragment.requireActivity())
    height(BarUtils.getStatusBarHeight() + BarUtils.getActionBarHeight())
    setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
}
