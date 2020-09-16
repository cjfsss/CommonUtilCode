package com.cjf.util.toast

import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.blankj.utilcode.util.ToastUtils
import com.cjf.util.toast.ToastX

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
        return ToastUtils.showCustomLong(layoutId)
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
        ToastUtils.setMsgTextSize(textSize)
    }

    override fun showCustomShort(@LayoutRes layoutId: Int): View {
        return ToastUtils.showCustomShort(layoutId)
    }

    override fun setMsgColor(@ColorInt msgColor: Int) {
        ToastUtils.setMsgColor(msgColor)
    }

    override fun cancel() {
        ToastUtils.cancel()
    }

    override fun setBgColor(@ColorInt backgroundColor: Int) {
        ToastUtils.setBgColor(backgroundColor)
    }

    override fun setBgResource(@DrawableRes id: Int) {
        ToastUtils.setBgResource(id)
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
        ToastUtils.setGravity(gravity, xOffset, yOffset)
    }
}