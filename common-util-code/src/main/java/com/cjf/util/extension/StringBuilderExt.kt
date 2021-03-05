package com.cjf.util.extension

import com.cjf.util.utils.LongUtils
import com.cjf.util.utils.StringBuilderUtils

/**
 * <p>Title: StringBuilderExt </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2021/1/22 9:24
 * @version : 1.0
 */

fun StringBuilder.deleteEnd(): StringBuilder {
    return StringBuilderUtils.deleteEnd(this)
}