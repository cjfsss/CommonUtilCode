package com.cjf.util.toast

import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * <p>Title: DefaultToastDelegateImpl </p>
 * <p>Description: Toast默认实现 </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/7/21 23:49
 * @version : 1.0
 */
class DefaultToastDelegateImpl : ToastX.ToastDelegate {

    override fun showCustomLong(@LayoutRes layoutId: Int): View {
        val view = View.inflate(ActivityUtils.getTopActivity(), layoutId, null)
        ToastUtils.getDefaultMaker().setDurationIsLong(true)
        ToastUtils.getDefaultMaker().show(view)
        return view
    }

    override fun showShort(text: CharSequence) {
        ToastUtils.showShort(text)
    }

    override fun showShort(@StringRes id: Int) {
        ToastUtils.showShort(id)
    }

    override fun showShort(@StringRes id: Int, vararg args: Any?) {
        ToastUtils.showShort(id)
    }

    override fun showShort(text: String, vararg args: Any?) {
        ToastUtils.showShort(text, args)
    }

    override fun setMsgTextSize(textSize: Int) {
        ToastUtils.getDefaultMaker().setTextSize(textSize)
    }

    override fun showCustomShort(@LayoutRes layoutId: Int): View {
        val view = View.inflate(ActivityUtils.getTopActivity(), layoutId, null)
        ToastUtils.getDefaultMaker().setDurationIsLong(false)
        ToastUtils.getDefaultMaker().show(view)
        return view
    }

    override fun setMsgColor(@ColorInt msgColor: Int) {
        ToastUtils.getDefaultMaker().setTextColor(msgColor)
    }

    override fun cancel() {
        ToastUtils.cancel()
    }

    override fun setBgColor(@ColorInt backgroundColor: Int) {
        ToastUtils.getDefaultMaker().setBgColor(backgroundColor)
    }

    override fun setBgResource(@DrawableRes id: Int) {
        ToastUtils.getDefaultMaker().setBgResource(id)
    }

    override fun showLong(text: CharSequence) {
        ToastUtils.showLong(text)
    }

    override fun showLong(@StringRes id: Int) {
        ToastUtils.showLong(id)
    }

    override fun showLong(@StringRes id: Int, vararg args: Any?) {
        ToastUtils.showLong(id, args)
    }

    override fun showLong(text: String, vararg args: Any?) {
        ToastUtils.showLong(text, args)
    }

    override fun setGravity(gravity: Int, xOffset: Int, yOffset: Int) {
        ToastUtils.getDefaultMaker().setGravity(gravity, xOffset, yOffset)
    }
}