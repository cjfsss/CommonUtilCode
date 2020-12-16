package com.cjf.base.delegate.fragment

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewStub
import androidx.fragment.app.Fragment

/**
 * <p>Title: ViewStubDelegate </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/12/15 16:48
 * @version : 1.0
 */
abstract class StubFragmentDelegate(fragment: Fragment, private val stubCompat: ViewStub) : LayoutFragmentDelegate(fragment), ViewStub.OnInflateListener {

    @SuppressLint("RestrictedApi")
    override fun applyLayout(): View {
        stubCompat.setOnInflateListener(this)
        return stubCompat.inflate()
    }


    override fun getLayoutId(): Int {
        return 0
    }

    override fun onInflate(stub: ViewStub, inflated: View) {
        initView(inflated, null)
        initData(inflated, null)
    }
}