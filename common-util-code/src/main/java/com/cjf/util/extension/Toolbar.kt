package com.cjf.util.extension

import android.graphics.Paint
import android.text.TextUtils
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

/**
 * <p>Title: Toolbar </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/3 13:31
 * @version : 1.0
 */


fun Toolbar.setCenterSubtitle() {
    val originalTitle: CharSequence = subtitle
    for (i in 0 until childCount) {
        val view = getChildAt(i)
        if (view is TextView) {
            if (TextUtils.equals(view.text, originalTitle)) {
                view.gravity = Gravity.CENTER
                val params =
                    Toolbar.LayoutParams(
                        Toolbar.LayoutParams.WRAP_CONTENT,
                        Toolbar.LayoutParams.MATCH_PARENT
                    )
                params.gravity = Gravity.CENTER
                view.setLayoutParams(params)
                break
            }
        }
    }
}

fun Toolbar.setCenterTitle() {
    val originalTitle: CharSequence = title
    for (i in 0 until childCount) {
        val view = getChildAt(i)
        if (view is TextView) {
            if (TextUtils.equals(view.text, originalTitle)) {
                view.gravity = Gravity.CENTER
                val params =
                    Toolbar.LayoutParams(
                        Toolbar.LayoutParams.WRAP_CONTENT,
                        Toolbar.LayoutParams.MATCH_PARENT
                    )
                params.gravity = Gravity.CENTER
                view.setLayoutParams(params)
                break
            }
        }
    }
}

fun Toolbar.setCenterTitleBold() {
    val originalTitle: CharSequence = title
    for (i in 0 until childCount) {
        val view = getChildAt(i)
        if (view is TextView) {
            if (TextUtils.equals(view.text, originalTitle)) {
                view.gravity = Gravity.CENTER
                val params =
                    Toolbar.LayoutParams(
                        Toolbar.LayoutParams.WRAP_CONTENT,
                        Toolbar.LayoutParams.MATCH_PARENT
                    )
                params.gravity = Gravity.CENTER
                view.setLayoutParams(params)
                view.paintFlags = Paint.FAKE_BOLD_TEXT_FLAG
                break
            }
        }
    }
}

fun Toolbar.setTitleBold() {
    val originalTitle: CharSequence? = title
    if (TextUtils.isEmpty(originalTitle)) {
        return
    }
    for (i in 0 until childCount) {
        val view = getChildAt(i)
        if (view is TextView) {
            if (TextUtils.equals(view.text, originalTitle)) {
                view.paintFlags = Paint.FAKE_BOLD_TEXT_FLAG
                break
            }
        }
    }
}