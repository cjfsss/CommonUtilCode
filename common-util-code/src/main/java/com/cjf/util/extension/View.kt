package com.cjf.util.extension

import android.view.View
import com.cjf.util.utils.ViewUtils

/**
 * <p>Title: View </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/4 15:04
 * @version : 1.0
 */

fun View.sp2px(size: Float): Float {
    return ViewUtils.sp2px(context, size)
}

fun View.dp2px(size: Float): Float {
    return ViewUtils.dp2px(context, size)
}

fun View.setCommonPaddingTopBottom() {
    ViewUtils.setCommonPaddingTopBottom(this)
}

fun View.setCommonPaddingLeftRight(view: View) {
    ViewUtils.setCommonPaddingLeftRight(this)
}

fun View.setCommonPadding(view: View) {
    ViewUtils.setCommonPadding(this)
}

fun View.setPadding(leftRight: Int, topBottom: Int) {
    ViewUtils.setPadding(this, leftRight, topBottom)
}

/**
 * Add the top margin size equals status bar's height for view.
 *
 * @param view The view.
 */
fun View.addMarginHeight(left: Int, top: Int, right: Int, bottom: Int) {
    ViewUtils.addMarginHeight(this, left, top, right, bottom)
}

fun View.addMarginTopHeight(top: Int) {
    ViewUtils.addMarginTopHeight(this, top)
}

fun View.addMarginBottomHeight(bottom: Int) {
    ViewUtils.addMarginBottomHeight(this, bottom)
}