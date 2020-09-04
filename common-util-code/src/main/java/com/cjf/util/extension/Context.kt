package com.cjf.util.extension

import android.content.Context
import android.util.TypedValue
import com.cjf.util.utils.ResUtils
import com.cjf.util.utils.ViewUtils

/**
 * <p>Title: Context </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/4 15:07
 * @version : 1.0
 */

fun Context.isTablet(): Boolean {
    return ResUtils.isTablet(this)
}

fun Context.sp2px(size: Float): Float {
    return ViewUtils.sp2px(this, size)
}

fun Context.dp2px(size: Float): Float {
    return ViewUtils.dp2px(this, size)
}