package com.cjf.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cjf.util.R

/**
 * <p>Title: CustomLoadMoreView </p>
 * <p>Description: 默认加载更多 </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/7/19 18:16
 * @version : 1.0
 */
class DefaultLoadMoreView : BaseLoadMoreView() {

    override fun getRootView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.simple_view_load_more, parent, false)
    }

    override fun getLoadingView(holder: BaseViewHolder): View {
        return holder.getView(R.id.load_more_loading_view)
    }

    override fun getLoadComplete(holder: BaseViewHolder): View {
        return holder.getView(R.id.load_more_load_complete_view)
    }

    override fun getLoadEndView(holder: BaseViewHolder): View {
        return holder.getView(R.id.load_more_load_end_view)
    }

    override fun getLoadFailView(holder: BaseViewHolder): View {
        return holder.getView(R.id.load_more_load_fail_view)
    }
}