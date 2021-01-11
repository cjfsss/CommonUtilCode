package com.cjf.base.delegate.fragment

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.Fragment
import com.cjf.base.view.IFragmentActivity

/**
 * <p>Title: BaseDelegate </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/12/15 15:51
 * @version : 1.0
 */
abstract class ViewFragmentDelegate(rootView: View, fragment: Fragment) : LayoutFragmentDelegate(fragment), IFragmentActivity {

    init {
        mRootView = rootView
    }

    @SuppressLint("RestrictedApi")
    override fun applyLayout(): View {
        initView(mRootView!!, null)
        initData(mRootView!!, null)
        return mRootView!!
    }


    override fun getLayoutId(): Int {
        return 0
    }

}