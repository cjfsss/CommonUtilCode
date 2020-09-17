package com.cjf.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cjf.base.IViewLoading
import com.cjf.util.extension.clearAllClick
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

/**
 * Description:
 * Create by dance, at 2019/5/16
 */
abstract class BaseActivity : AppCompatActivity(), IViewLoading {

    private val mProgressDialog: LoadingPopupView by lazy {
        XPopup.Builder(this).asLoading()
    }

    private val adapterList by lazy {
        ArrayList<RecyclerView.Adapter<*>>()
    }

    protected fun <T : RecyclerView.ViewHolder, A : RecyclerView.Adapter<T>> bindAdapter(adapter: A): A {
        adapterList.add(adapter)
        return adapter
    }

//    protected fun <T, VH : BaseViewHolder, A : BaseQuickAdapter<T, VH>> bindAdapter(adapter: A): A {
//        adapterList.add(adapter)
//        return adapter
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initData()
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()

    protected abstract fun initData()

    override fun showLoading(title: String) {
        mProgressDialog.setTitle(title)?.show()
    }

    override fun hideLoading() {
        mProgressDialog.dismiss()
    }

    override fun onDestroy() {
        for (adapter in adapterList) {
            if (adapter is BaseQuickAdapter<*, *>) {
                adapter.clearAllClick()
            }
        }
        adapterList.clear()
        hideLoading()
        super.onDestroy()
    }
}