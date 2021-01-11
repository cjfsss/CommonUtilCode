package com.cjf.base.delegate

import android.annotation.SuppressLint
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
abstract class ViewDelegate(rootView: View, activity: AppCompatActivity) : LayoutDelegate(activity), IFragmentActivity {

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