package com.cjf.util.extension

import android.view.Gravity
import android.view.Window
import androidx.annotation.StyleRes
import com.cjf.util.R

/**
 * <p>Title: Window </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/4 15:38
 * @version : 1.0
 */

fun Window.fromTopFull() {
    this.from(R.style.RightAnimStyle, Gravity.TOP, true)
}

fun Window.fromRight() {
    this.from(R.style.RightAnimStyle, Gravity.RIGHT)
}

fun Window.fromBottomFull() {
    this.from(R.style.BottomAnimStyle, Gravity.BOTTOM, true)
}

fun Window.fromTop() {
    this.from(R.style.RightAnimStyle, Gravity.TOP)
}

fun Window.fromBottom() {
    this.from(R.style.BottomAnimStyle, Gravity.BOTTOM)
}

fun Window.from(@StyleRes animations: Int, gravity: Int, fullScreen: Boolean = false) {
    setWindowAnimations(animations)
    if (fullScreen) {
        decorView.setPadding(0, 0, 0, 0)
        attributes.gravity = gravity
        attributes.width = -1
        attributes.height = -2
    }
}