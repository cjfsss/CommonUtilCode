package com.cjf.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cjf.base.IViewLoading
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

/**
 * Description:
 * Create by dance, at 2019/5/16
 */
abstract class BaseFragment : Fragment(), IViewLoading {

    private val mProgressDialog: LoadingPopupView by lazy {
        XPopup.Builder(activity).asLoading()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        hideLoading()
        super.onDestroy()
    }
}