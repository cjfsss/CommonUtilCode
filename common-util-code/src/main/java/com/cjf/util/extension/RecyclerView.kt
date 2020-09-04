package com.cjf.util.extension

import androidx.recyclerview.widget.RecyclerView

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