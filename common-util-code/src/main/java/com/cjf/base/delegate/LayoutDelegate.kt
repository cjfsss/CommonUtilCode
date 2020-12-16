package com.cjf.base.delegate

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
abstract class LayoutDelegate(activity: AppCompatActivity) : Delegate(activity), IFragmentActivity {

    open fun applyLayout(): View {
        val view = View.inflate(getBaseActivity(), getLayoutId(), null)
        initView(view, null)
        initData(view, null)
        return view
    }

    override fun getBaseActivity(): AppCompatActivity {
        return getActivity()
    }

    override fun getBundle(): Bundle? {
        val intent = getActivity().intent ?: return null
        return intent.getBundleExtra("bundle")
    }
}