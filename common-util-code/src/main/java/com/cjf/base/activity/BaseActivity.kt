package com.cjf.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cjf.base.IViewLoading
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
        hideLoading()
        super.onDestroy()
    }
}