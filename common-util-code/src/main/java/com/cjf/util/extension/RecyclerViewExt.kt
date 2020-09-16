package com.cjf.util.extension

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.recyclerview.widget.*

/**
 * <p>Title: RecyclerView </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/3 13:28
 * @version : 1.0
 */


/**
 * 连级更新选中项
 */
fun RecyclerView.notifyItemRangeChangedChecked(
        adapterPosition: Int,
        size: Int
) {
    if (isComputingLayout) {
        // 延时递归处理。
        postDelayed({
            notifyItemRangeChangedChecked(adapterPosition, size)
        }, 100)
    } else {
        adapter?.notifyItemRangeChanged(adapterPosition, size)
    }
}

/**
 * 连级更新选中项
 */
fun RecyclerView.notifyChangedChecked() {
    if (isComputingLayout) {
        // 延时递归处理。
        postDelayed({
            notifyChangedChecked()
        }, 100)
    } else {
        adapter?.notifyDataSetChanged()
    }
}

/**
 * 移除所有分割线
 */
fun RecyclerView.removeAllItemDecoration() {
    // 移除所有的分割线，这里要实现时间轴方式的展示效果
    val itemDecorationCount: Int = itemDecorationCount
    for (i in 0 until itemDecorationCount) {
        removeItemDecorationAt(i)
    }
}

/**
 * 设置分割线
 * @param color 分割线的颜色，默认是#DEDEDE
 * @param size 分割线的大小，默认是1px
 * @param isReplace 是否覆盖之前的ItemDecoration，默认是true
 *
 */
fun RecyclerView.divider(orientation: Int = DividerItemDecoration.VERTICAL, color: Int = Color.parseColor("#DEDEDE"), size: Int = dp2px(1f), isReplace: Boolean = true): RecyclerView {
    val decoration = DividerItemDecoration(context, orientation)
    decoration.setDrawable(GradientDrawable().apply {
        setColor(color)
        shape = GradientDrawable.RECTANGLE
        setSize(size.toInt(), size.toInt())
    })
    if (isReplace && itemDecorationCount > 0) {
        removeItemDecorationAt(0)
    }
    addItemDecoration(decoration)
    return this
}


fun RecyclerView.vertical(spanCount: Int = 0, isStaggered: Boolean = false): RecyclerView {
    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    if (spanCount != 0) {
        layoutManager = GridLayoutManager(context, spanCount)
    }
    if (isStaggered) {
        layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
    }
    return this
}

fun RecyclerView.horizontal(spanCount: Int = 0, isStaggered: Boolean = false): RecyclerView {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    if (spanCount != 0) {
        layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.HORIZONTAL, false)
    }
    if (isStaggered) {
        layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL)
    }
    return this
}