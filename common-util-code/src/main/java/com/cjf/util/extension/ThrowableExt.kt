package com.cjf.util.extension

import com.cjf.util.log.LogX

/**
 * <p>Title: Throwable </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/3 13:32
 * @version : 1.0
 */
fun Throwable.print(vararg arg: String?) {
    if (arg.isNullOrEmpty()) {
        LogX.printErrStackTrace(javaClass.name, this, arg)
    } else {
        LogX.printErrStackTrace(javaClass.name, this)
    }
}

fun Throwable.print() {
    this.print(null)
}
