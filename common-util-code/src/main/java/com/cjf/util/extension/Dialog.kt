package com.cjf.util.extension

import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import com.cjf.util.listener.OnCancelListener
import com.cjf.util.listener.OnConfirmListener
import com.cjf.util.utils.ResUtils

/**
 * <p>Title: Dialog </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/1 9:51
 * @version : 1.0
 */



fun AlertDialog.Builder.asConfirmRes(
        titleId: Int?, contentId: Int?, cancelId: Int?, confirmId: Int?,
        confirmListener: OnConfirmListener? = null, cancelListener: OnCancelListener? = null,
        isHideCancel: Boolean = false, bindLayoutId: Int
): AlertDialog.Builder {
    var title: CharSequence? = null
    if (titleId != null && titleId == 0) {
        title = ResUtils.getString(titleId)
    }
    var content: CharSequence? = null
    if (contentId != null && contentId == 0) {
        content = ResUtils.getString(contentId)
    }
    var cancelBtnText: CharSequence? = null
    if (cancelId != null && cancelId == 0) {
        cancelBtnText = ResUtils.getString(cancelId)
    }
    var confirmBtnText: CharSequence? = null
    if (confirmId != null && confirmId == 0) {
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