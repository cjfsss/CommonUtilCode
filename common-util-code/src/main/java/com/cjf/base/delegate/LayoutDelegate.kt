package com.cjf.base.delegate

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.cjf.base.view.IFragmentActivity
import com.cjf.util.extension.gone
import com.cjf.util.extension.visible

/**
 * <p>Title: BaseDelegate </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/12/15 15:51
 * @version : 1.0
 */
abstract class LayoutDelegate(activity: AppCompatActivity) : Delegate(activity), IFragmentActivity {

    protected open var mRootView: View? = null

    open fun applyLayout(): View {
        mRootView = View.inflate(getBaseActivity(), getLayoutId(), null)
        initView(mRootView!!, null)
        initData(mRootView!!, null)
        return mRootView!!
    }

    open fun getRootView(): View? {
        return mRootView
    }

    fun setVisibility(isVisibility: Boolean) {
        if (isVisibility) {
            getRootView()?.visible()
        } else {
            getRootView()?.gone()
        }
    }

    override fun getBaseActivity(): AppCompatActivity {
        return getActivity()
    }

    override fun getBundle(): Bundle? {
        val intent = getActivity().intent ?: return null
        return intent.getBundleExtra("bundle")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        mRootView = null
        super.onDestroy(owner)
    }
}