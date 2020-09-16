package com.cjf.util.log

import com.blankj.utilcode.util.LogUtils

/**
 * <p>Title: DefaultLogDelegateImpl </p>
 * <p>Description: Log默认实现 </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/7/21 10:30
 * @version : 1.0
 */
class DefaultLogDelegateImpl : LogX.LogDelegate {

    override fun d(vararg objects: Any) {
        LogUtils.d(*objects)
    }

    override fun dTag(tag: String, vararg objects: Any) {
        LogUtils.dTag(tag, *objects)
    }

    override fun i(vararg objects: Any) {
        LogUtils.i(*objects)
    }

    override fun iTag(tag: String, vararg objects: Any) {
        LogUtils.iTag(tag, *objects)
    }

    override fun w(vararg objects: Any) {
        LogUtils.w(*objects)
    }

    override fun wTag(tag: String, vararg objects: Any) {
        LogUtils.wTag(tag, *objects)
    }

    override fun e(vararg objects: Any) {
        LogUtils.e(*objects)
    }

    override fun eTag(tag: String, vararg objects: Any) {
        LogUtils.eTag(tag, *objects)
    }

    override fun file(`object`: Any) {
        LogUtils.file(`object`)
    }

    override fun file(tag: String, `object`: Any) {
        LogUtils.file(tag, `object`)
    }

    override fun json(`object`: Any) {
        LogUtils.json(`object`)
    }

    override fun json(tag: String, `object`: Any) {
        LogUtils.json(tag, `object`)
    }

    override fun xml(xml: String) {
        LogUtils.xml(xml)
    }

    override fun xml(tag: String, xml: String) {
        LogUtils.xml(tag, xml)
    }

    override fun printErrStackTrace( tag: String,
                                     throwable: Throwable, vararg objects: Any?) {
        eTag(tag, throwable,  objects)
    }

}