package com.cjf.util.extension

import android.app.Activity
import android.text.TextUtils
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.cjf.util.listener.OnCancelListener
import com.cjf.util.listener.OnConfirmListener
import com.cjf.util.utils.ResUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.impl.ConfirmPopupView

/**
 * <p>Title: Dialog </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/1 9:51
 * @version : 1.0
 */

/****************************************** 便捷方法 ****************************************/
/****************************************** 便捷方法  */

fun XPopup.Builder.ofNav(activity: Activity): XPopup.Builder {
    var navBarHeight = 0;
    if (BarUtils.isNavBarVisible(activity)) {
        navBarHeight = -BarUtils.getNavBarHeight();
    }
    return this.offsetY(navBarHeight);
}

/**
 * 显示确认和取消对话框
 *
 * @param title           对话框标题，传空串会隐藏标题
 * @param content         对话框内容
 * @param cancelBtnText   取消按钮的文字内容
 * @param confirmBtnText  确认按钮的文字内容
 * @param confirmListener 点击确认的监听器
 * @param cancelListener  点击取消的监听器
 * @param isHideCancel    是否隐藏取消按钮
 * @param bindLayoutId    自定义的布局Id，没有则传0
 * @return
 */
fun XPopup.Builder.asConfirmRes(
        titleId: Int?, contentId: Int?, cancelId: Int?, confirmId: Int?,
        confirmListener: com.lxj.xpopup.interfaces.OnConfirmListener? = null, cancelListener: com.lxj.xpopup.interfaces.OnCancelListener? = null,
        isHideCancel: Boolean = false, bindLayoutId: Int
): ConfirmPopupView {
    var title: CharSequence? = null
    if (titleId != null && titleId != 0) {
        title = ResUtils.getString(titleId)
    }
    var content: CharSequence? = null
    if (contentId != null && contentId != 0) {
        content = ResUtils.getString(contentId)
    }
    var cancelBtnText: CharSequence? = null
    if (cancelId != null && cancelId != 0) {
        cancelBtnText = ResUtils.getString(cancelId)
    }
    var confirmBtnText: CharSequence? = null
    if (confirmId != null && confirmId != 0) {
        confirmBtnText = ResUtils.getString(confirmId)
    }
    return asConfirm(
            title, content, cancelBtnText, confirmBtnText,
            confirmListener, cancelListener, isHideCancel, bindLayoutId
    )
}

fun XPopup.Builder.asConfirmRes(
        titleId: Int?, contentId: Int?, cancelId: Int?, confirmId: Int?,
        confirmListener: com.lxj.xpopup.interfaces.OnConfirmListener? = null, cancelListener: com.lxj.xpopup.interfaces.OnCancelListener? = null,
        isHideCancel: Boolean = false
): ConfirmPopupView {
    return asConfirmRes(
            titleId, contentId, cancelId, confirmId, confirmListener, cancelListener,
            isHideCancel, 0
    )
}

fun XPopup.Builder.asConfirmRes(
        titleId: Int?, contentId: Int?,
        confirmListener: com.lxj.xpopup.interfaces.OnConfirmListener?, cancelListener: com.lxj.xpopup.interfaces.OnCancelListener?
): ConfirmPopupView {
    return asConfirmRes(titleId, contentId, null, null, confirmListener, cancelListener, false, 0)
}

fun XPopup.Builder.asConfirmRes(
        titleId: Int?, contentId: Int?,
        confirmListener: com.lxj.xpopup.interfaces.OnConfirmListener?
): ConfirmPopupView {
    return asConfirmRes(titleId, contentId, null, null, confirmListener, null, false, 0)
}


fun AlertDialog.Builder.asConfirmRes(
        titleId: Int?, contentId: Int?, cancelId: Int?, confirmId: Int?,
        confirmListener: OnConfirmListener? = null, cancelListener: OnCancelListener? = null,
        isHideCancel: Boolean = false, bindLayoutId: Int
): AlertDialog.Builder {
    var title: CharSequence? = null
    if (titleId != null && titleId != 0) {
        title = ResUtils.getString(titleId)
    }
    var content: CharSequence? = null
    if (contentId != null && contentId != 0) {
        content = ResUtils.getString(contentId)
    }
    var cancelBtnText: CharSequence? = null
    if (cancelId != null && cancelId != 0) {
        cancelBtnText = ResUtils.getString(cancelId)
    }
    var confirmBtnText: CharSequence? = null
    if (confirmId != null && confirmId != 0) {
        confirmBtnText = ResUtils.getString(confirmId)
    }
    return asConfirm(
            title, content, cancelBtnText, confirmBtnText,
            confirmListener, cancelListener, isHideCancel, bindLayoutId
    )
}

fun AlertDialog.Builder.asConfirm(
        title: CharSequence?, content: CharSequence?,
        cancelBtnText: CharSequence?, confirmBtnText: CharSequence?,
        confirmListener: OnConfirmListener? = null, cancelListener: OnCancelListener? = null,
        isHideCancel: Boolean = false, bindLayoutId: Int
): AlertDialog.Builder {
    if (!TextUtils.isEmpty(title)) {
        setTitle(title)
    }
    if (!TextUtils.isEmpty(content)) {
        setMessage(content)
    }
    if (!isHideCancel && !TextUtils.isEmpty(cancelBtnText)) {
        setNegativeButton(cancelBtnText) { _, _ -> cancelListener?.onCancel() }
    }
    if (!TextUtils.isEmpty(confirmBtnText)) {
        setPositiveButton(confirmBtnText) { _, _ -> confirmListener?.onConfirm() }
    }
    if (bindLayoutId != 0) {
        setView(bindLayoutId)
    }
    return this
}

fun AlertDialog.Builder.asConfirmRes(
        titleId: Int?, contentId: Int?, cancelId: Int?, confirmId: Int?,
        confirmListener: OnConfirmListener? = null, cancelListener: OnCancelListener? = null,
        isHideCancel: Boolean = false
): AlertDialog.Builder {
    return asConfirmRes(
            titleId, contentId, cancelId, confirmId, confirmListener, cancelListener,
            isHideCancel, 0
    )
}

fun AlertDialog.Builder.asConfirmRes(
        titleId: Int?, contentId: Int?,
        confirmListener: OnConfirmListener?, cancelListener: OnCancelListener?
): AlertDialog.Builder {
    return asConfirmRes(titleId, contentId, null, null, confirmListener, cancelListener, false, 0)
}

fun AlertDialog.Builder.asConfirmRes(
        titleId: Int?, contentId: Int?,
        confirmListener: OnConfirmListener?
): AlertDialog.Builder {
    return asConfirmRes(titleId, contentId, null, null, confirmListener, null, false, 0)
}

fun AlertDialog.Builder.asConfirm(
        title: CharSequence, content: CharSequence?,
        cancelBtnText: CharSequence?, confirmBtnText: CharSequence?,
        confirmListener: OnConfirmListener? = null, cancelListener: OnCancelListener? = null,
        isHideCancel: Boolean = false
): AlertDialog.Builder {
    return asConfirm(
            title, content, cancelBtnText, confirmBtnText, confirmListener, cancelListener,
            isHideCancel, 0
    )
}

fun AlertDialog.Builder.asConfirmRes(
        title: CharSequence?, content: CharSequence?,
        confirmListener: OnConfirmListener?, cancelListener: OnCancelListener?
): AlertDialog.Builder {
    return asConfirm(title, content, null, null, confirmListener, cancelListener, false, 0)
}

fun AlertDialog.Builder.asConfirmRes(
        title: CharSequence?, content: CharSequence?,
        confirmListener: OnConfirmListener?
): AlertDialog.Builder {
    return asConfirm(title, content, null, null, confirmListener, null, false, 0)
}


fun Activity?.showLoading(title: String): BasePopupView? {
    if (this == null) {
        return null
    }
    val asLoading = XPopup.Builder(this).asLoading()
    asLoading.setTitle(title)?.show()
    return asLoading
}

fun showLoading(title: String): BasePopupView? {
    return ActivityUtils.getTopActivity().showLoading(title)
}

fun Fragment?.showLoading(title: String): BasePopupView? {
    if (this == null) {
        return null
    }
    val asLoading = XPopup.Builder(requireActivity()).asLoading()
    asLoading.setTitle(title)?.show()
    return asLoading
}