package com.cjf.util.extension

import com.cjf.util.utils.StringUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Description: 时间日期相关
 * Create by lxj, at 2018/12/7
 */

/**
 *  字符串日期格式（比如：2018-4-6)转为毫秒
 *  @param format 时间的格式，默认是按照yyyy-MM-dd HH:mm:ss来转换，如果您的格式不一样，则需要传入对应的格式
 */
fun String?.toDateMillsYMD(format: String = "yyyy-MM-dd"): Long {
    return toDateMills(format)
}

fun String?.toDateMills(format: String = "yyyy-MM-dd HH:mm:ss"): Long {
    if (this.isNullOrEmpty()) {
        return 0
    }
    val time = StringUtils.toTime(this)
    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    return SimpleDateFormat(format, Locale.getDefault()).parse(time).time
}

fun String?.toDateStringYMD(format: String = "yyyy-MM-dd"): String {
    return toDateString(format)
}

fun String?.toDateString(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val timeMills = this.toDateMills(format)
    if (timeMills == 0L) {
        return ""
    }
    return timeMills.toDateString(format)
}

fun String?.toDateStringUnavailable(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val time = this.toDateString(format)
    if (time.isEmpty()) {
        return "暂无"
    }
    return time
}

/**
 * Long类型时间戳转为字符串的日期格式
 * @param format 时间的格式，默认是按照yyyy-MM-dd HH:mm:ss来转换，如果您的格式不一样，则需要传入对应的格式
 */
fun Long?.toDateString(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    if (this == null) {
        return ""
    }
    return SimpleDateFormat(format, Locale.getDefault()).format(Date(this))
}

fun Long?.toDateStringUnavailable(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val time = this.toDateString(format)
    if (time.isEmpty()) {
        return "暂无"
    }
    return time
}

fun Int?.toDateString(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    if (this == null) {
        return ""
    }
    return SimpleDateFormat(format, Locale.getDefault()).format(Date(this.toLong()))
}

fun Int?.toDateStringUnavailable(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val time = this.toDateString(format)
    if (time.isEmpty()) {
        return "暂无"
    }
    return time
}

