package com.cjf.util.extension

import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cjf.util.utils.ResUtils

/**
 * Description: 资源操作相关
 * Create by dance, at 2018/12/11
 */

fun Context.color(id: Int) = ResUtils.getColor(this, id)

fun Context.string(id: Int) = ResUtils.getString(this, id)

fun Context.stringArray(id: Int) = ResUtils.getStringArray(this, id)

fun Context.intArray(id: Int) = ResUtils.getIntArray(this, id)

fun Context.textArray(id: Int) = ResUtils.getTextArray(this, id)

fun Context.mipmapArray(id: Int) = ResUtils.getMipmapArray(this, id)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.stateListAnimator(id: Int) = ResUtils.getStateListAnimator(this, id)

fun Context.drawable(id: Int) = ResUtils.getDrawable(this, id)

fun Context.dimenPx(id: Int) = resources.getDimensionPixelSize(id)

fun View.color(id: Int) = context.color(id)

fun View.string(id: Int) = context.string(id)

fun View.stringArray(id: Int) = context.stringArray(id)

fun View.intArray(id: Int) = context.intArray(id)

fun View.textArray(id: Int) = context.textArray(id)

fun View.mipmapArray(id: Int) = context.mipmapArray(id)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun View.stateListAnimator(id: Int) = context.stateListAnimator(id)

fun View.drawable(id: Int) = context.drawable(id)

fun View.dimenPx(id: Int) = context.dimenPx(id)


fun Fragment.color(id: Int) = context?.color(id)

fun Fragment.string(id: Int) = context?.string(id)

fun Fragment.intArray(id: Int) = context?.intArray(id)

fun Fragment.textArray(id: Int) = context?.textArray(id)

fun Fragment.mipmapArray(id: Int) = context?.mipmapArray(id)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Fragment.stateListAnimator(id: Int) = context?.stateListAnimator(id)

fun Fragment.stringArray(id: Int) = context?.stringArray(id)

fun Fragment.drawable(id: Int) = context?.drawable(id)

fun Fragment.dimenPx(id: Int) = context?.dimenPx(id)


fun RecyclerView.ViewHolder.color(id: Int) = itemView.color(id)

fun RecyclerView.ViewHolder.string(id: Int) = itemView.string(id)

fun RecyclerView.ViewHolder.stringArray(id: Int) = itemView.stringArray(id)

fun RecyclerView.ViewHolder.intArray(id: Int) = itemView.intArray(id)

fun RecyclerView.ViewHolder.textArray(id: Int) = itemView.textArray(id)

fun RecyclerView.ViewHolder.mipmapArray(id: Int) = itemView.mipmapArray(id)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun RecyclerView.ViewHolder.stateListAnimator(id: Int) = itemView.stateListAnimator(id)

fun RecyclerView.ViewHolder.drawable(id: Int) = itemView.drawable(id)

fun RecyclerView.ViewHolder.dimenPx(id: Int) = itemView.dimenPx(id)

fun BaseViewHolder.color(id: Int) = itemView.color(id)

fun BaseViewHolder.string(id: Int) = itemView.string(id)

fun BaseViewHolder.stringArray(id: Int) = itemView.stringArray(id)

fun BaseViewHolder.intArray(id: Int) = itemView.intArray(id)

fun BaseViewHolder.textArray(id: Int) = itemView.textArray(id)

fun BaseViewHolder.mipmapArray(id: Int) = itemView.mipmapArray(id)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun BaseViewHolder.stateListAnimator(id: Int) = itemView.stateListAnimator(id)

fun BaseViewHolder.drawable(id: Int) = itemView.drawable(id)

fun BaseViewHolder.dimenPx(id: Int) = itemView.dimenPx(id)