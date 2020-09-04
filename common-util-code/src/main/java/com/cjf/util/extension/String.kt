package com.cjf.util.extension

import android.text.TextUtils
import com.cjf.util.utils.StringUtils

/**
 * <p>Title: String </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/3 13:56
 * @version : 1.0
 */

fun String?.toStringZero(): String {
    if (TextUtils.isEmpty(this)) {
        return "0"
    }
    return StringUtils.toStringZero(this)
}

fun String?.toLongZero(): Long {
    if (TextUtils.isEmpty(this)) {
        return 0L
    }
    return this!!.toLong()
}

fun String?.toIntZero(): Int {
    if (TextUtils.isEmpty(this)) {
        return 0
    }
    return this!!.toInt()
}

fun String?.trimNR(): String {
    if (TextUtils.isEmpty(this)) {
        return ""
    }
    return this?.replace("\n", "")?.replace("\r", "")?.trim().toString()
}

fun String?.noData(): String {
    if (TextUtils.isEmpty(this)) {
        return "暂无"
    }
    return this.toString()
}

fun String?.toTime(): String {
    if (this.isNullOrEmpty()) {
        return "暂无"
    }
    return StringUtils.toTime(this)
}

fun String?.toNULL(): String? {
    if (TextUtils.isEmpty(this) || TextUtils.equals(this, "null")
            || TextUtils.equals(this, "NULL")
    ) {
        return null
    }
    return this.toString()
}

fun String?.empty(): String {
    if (TextUtils.isEmpty(this) || TextUtils.equals(this, "null")
            || TextUtils.equals(this, "NULL")
    ) {
        return ""
    }
    return this.toString()
}
