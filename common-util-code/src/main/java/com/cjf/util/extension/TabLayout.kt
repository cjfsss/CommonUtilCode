package com.cjf.util.extension

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.drawable.DrawableCompat
import com.cjf.util.R
import com.cjf.util.utils.ResUtils
import com.google.android.material.tabs.TabLayout

/**
 * <p>Title: TabLayout </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/27 21:06
 * @version : 1.0
 */

fun TabLayout.tabBottom(text: CharSequence?, resIcon: Int = 0, marginTop: Int = dp2px(4f)): View {
    val tabView = View.inflate(context, R.layout.design_layout_tab_bottom, null)
    val imageView = tabView.findViewById<AppCompatImageView>(R.id.imageView)
    val textView = tabView.findViewById<AppCompatTextView>(R.id.text)
    textView.margin(0, marginTop + dp2px(24f), 0, 0)
    updateCustomView(textView, text, imageView, resIcon)
    return tabView
}

fun TabLayout.updateCustomView(textView: TextView?, text: CharSequence?, iconView: ImageView?, resIcon: Int = 0) {
    val icon = textView?.drawable(resIcon)
    if (icon != null) {
        DrawableCompat.setTintList(icon, tabIconTint)
    }
    if (tabTextColors != null) {
        textView?.setTextColor(tabTextColors)
    }
    val iconMutate = if (icon != null) DrawableCompat.wrap(icon).mutate() else null
    if (iconView != null) {
        if (iconMutate != null) {
            iconView.setImageDrawable(iconMutate)
            iconView.visibility = View.VISIBLE
            visibility = View.VISIBLE
        } else {
            iconView.visibility = View.GONE
            iconView.setImageDrawable(null)
        }
    }
    val hasText = !TextUtils.isEmpty(text)
    if (textView != null) {
        if (hasText) {
            textView.text = text
            visibility = View.VISIBLE
        } else {
            textView.visibility = View.GONE
            textView.text = null
        }
    }
}