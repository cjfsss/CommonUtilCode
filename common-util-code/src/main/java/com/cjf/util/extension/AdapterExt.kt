package com.cjf.util.extension

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * <p>Title: Adapter </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/3 13:24
 * @version : 1.0
 */

/**
 * 设置点击监听, 并实现事件节流
 */
fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.clickItem(
        action: (baseAdapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit
) {
    setOnItemClickListener { adapter, view, position ->
        if (!_viewClickFlag) {
            _viewClickFlag = true
            action(adapter, view, position)
        }
        view.removeCallbacks(_clickRunnable)
        view.postDelayed(_clickRunnable, 350)
    }
}

fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.clearClickItem() {
    setOnItemClickListener(null)
}

/**
 * 设置长按监听
 */
fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.longClick(
        action: (baseAdapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Boolean
) {
    setOnItemLongClickListener(action)
}

fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.clearLongClickItem() {
    setOnItemLongClickListener(null)
}

/**
 * 设置点击监听, 并实现事件节流
 */
fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.clickChildItem(
        action: (baseAdapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit
) {
    setOnItemChildClickListener { adapter, view, position ->
        if (!_viewClickFlag) {
            _viewClickFlag = true
            action(adapter, view, position)
        }
        view.removeCallbacks(_clickRunnable)
        view.postDelayed(_clickRunnable, 350)
    }
}

fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.clearClickChildItem() {
    setOnItemChildClickListener(null)
}

/**
 * 设置长按监听
 */
fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.longChildClick(
        action: (baseAdapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Boolean
) {
    setOnItemChildLongClickListener(action)
}

fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.clearLongClickChildItem() {
    setOnItemChildLongClickListener(null)
}

fun BaseNodeProvider.addChildClickViewIds(viewHolder: BaseViewHolder, @IdRes vararg ids: Int) {
    getAdapter()?.addChildClickViewIds(viewHolder, *ids)
}

fun BaseNodeProvider.addChildLongClickViewIds(viewHolder: BaseViewHolder, @IdRes vararg ids: Int) {
    getAdapter()?.addChildLongClickViewIds(viewHolder, *ids)
}

fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.addChildClickViewIds(viewHolder: VH, @IdRes vararg ids: Int) {
    addChildClickViewIds(*ids)
    for (viewId in getChildClickViewIds()) {
        val childView: View? = viewHolder.itemView.findViewById(viewId)
        if (childView != null) {
            if (!childView.isClickable) {
                childView.isClickable = true
            }
            childView.setOnClickListener { v: View ->
                var adapterPosition: Int = viewHolder.adapterPosition
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                adapterPosition -= headerLayoutCount
                val itemChildClickListener: OnItemChildClickListener = getOnItemChildClickListener()
                        ?: return@setOnClickListener
                itemChildClickListener.onItemChildClick(this, v, adapterPosition)
            }
        }
    }
}

/**
 * 设置需要长按点击事件的子view
 * @param viewIds IntArray
 */
fun <T, VH : BaseViewHolder> BaseQuickAdapter<T, VH>.addChildLongClickViewIds(viewHolder: VH, @IdRes vararg viewIds: Int) {
    addChildLongClickViewIds(*viewIds)
    for (viewId in getChildLongClickViewIds()) {
        val childView: View? = viewHolder.itemView.findViewById(viewId)
        if (childView != null) {
            if (!childView.isClickable) {
                childView.isClickable = true
            }
            childView.setOnLongClickListener { v: View ->
                var adapterPosition: Int = viewHolder.adapterPosition
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnLongClickListener false
                }
                adapterPosition -= headerLayoutCount
                val itemChildClickListener: OnItemChildLongClickListener = getOnItemChildLongClickListener()
                        ?: return@setOnLongClickListener false
                itemChildClickListener.onItemChildLongClick(this, v, adapterPosition)
            }
        }
    }
}
