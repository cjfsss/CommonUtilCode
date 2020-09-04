package com.cjf.util.extension

import android.text.InputType
import android.text.TextUtils
import android.text.method.DigitsKeyListener
import android.text.method.HideReturnsTransformationMethod
import android.widget.EditText
import android.widget.TextView

/**
 * <p>Title: TextView </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/4 15:02
 * @version : 1.0
 */

fun TextView.setNumberWord() {
    keyListener = DigitsKeyListener.getInstance("0123456789qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM")
    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    transformationMethod = HideReturnsTransformationMethod.getInstance()
}

fun TextView.getTextString(): String {
    if (TextUtils.isEmpty(text)) {
        return ""
    }
    return text.trim().toString()
}

fun TextView.clearText() {
    text = ""
}