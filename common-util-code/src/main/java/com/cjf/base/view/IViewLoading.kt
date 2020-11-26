package com.cjf.base.view

import androidx.annotation.StringRes
import com.cjf.util.utils.ResUtils

/**
 * <p>Title: IViewLoading </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/7/21 11:04
 * @version : 1.0
 */
interface IViewLoading {

    /**
     * 显示加载进度
     */
    fun showLoading() {
        showLoading("正在加载...")
    }

    /**
     * 显示加载进度
     *
     * @param titleId 要显示的文字
     */
    fun showLoading(@StringRes titleId: Int) {
        showLoading(ResUtils.getString(titleId))
    }

    /**
     * 显示加载进度
     *
     * @param title 要显示的文字
     */
    fun showLoading(title: String) {
        showLoading(title, true)
    }

    /**
     * 设置点击弹窗外面是否关闭弹窗，默认为true
     *
     * @param title 要显示的文字
     * @param isDismissOnBackPressed 设置按下返回键是否关闭弹窗，默认为true
     * @param isDismissOnTouchOutside 设置点击弹窗外面是否关闭弹窗，默认为true
     */
    fun showLoading(title: String, isDismissOnBackPressed: Boolean, isDismissOnTouchOutside: Boolean)

    /**
     * 设置点击弹窗外面是否关闭弹窗，默认为true
     *
     * @param title 要显示的文字
     */
    fun showLoading(title: String, isDismissOnTouchOutside: Boolean) {
        showLoading(title, isDismissOnTouchOutside, isDismissOnTouchOutside)
    }

    /**
     * 隐藏加载进度
     */
    fun hideLoading()
}