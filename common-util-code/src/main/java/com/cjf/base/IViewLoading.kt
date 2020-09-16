package com.cjf.base

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
    fun showLoading(title: String)

    /**
     * 隐藏加载进度
     */
    fun hideLoading()
}