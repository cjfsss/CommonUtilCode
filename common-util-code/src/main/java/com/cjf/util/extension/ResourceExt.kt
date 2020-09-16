package com.cjf.util.extension

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.cjf.util.utils.ResUtils

/**
 * Description: 资源操作相关
 * Create by dance, at 2018/12/11
 */

fun Context.color(id: Int) = ResUtils.getColor(this, id)

fun Context.string(id: Int) = ResUtils.getString(this, id)

fun Context.stringArray(id: Int) = ResUtils.getStringArray(this, id)

fun Context.drawable(id: Int) = ResUtils.getDrawable(this, id)

fun Context.dimenPx(id: Int) = resources.getDimensionPixelSize(id)

fun View.color(id: Int) = ResUtils.getColor(id)

fun View.string(id: Int) = ResUtils.getString(id)

fun View.stringArray(id: Int) = ResUtils.getStringArray(id)

fun View.drawable(id: Int) = ResUtils.getDrawable(id)

fun View.dimenPx(id: Int) = context.dimenPx(id)


fun Fragment.color(id: Int) = ResUtils.getColor(id)

fun Fragment.string(id: Int) = ResUtils.getString(id)

fun Fragment.stringArray(id: Int) = ResUtils.getStringArray(id)

fun Fragment.drawable(id: Int) = ResUtils.getDrawable(id)

fun Fragment.dimenPx(id: Int) = context!!.dimenPx(id)


fun RecyclerView.ViewHolder.color(id: Int) = itemView.color(id)

fun RecyclerView.ViewHolder.string(id: Int) = itemView.string(id)

fun RecyclerView.ViewHolder.stringArray(id: Int) = itemView.stringArray(id)

fun RecyclerView.ViewHolder.drawable(id: Int) = itemView.drawable(id)

fun RecyclerView.ViewHolder.dimenPx(id: Int) = itemView.dimenPx(id)