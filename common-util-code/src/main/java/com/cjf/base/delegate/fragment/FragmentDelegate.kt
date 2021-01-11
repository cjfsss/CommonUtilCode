@file:Suppress("LeakingThis")

package com.cjf.base.delegate.fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cjf.base.delegate.Delegate
import com.cjf.base.view.IViewLoading

/**
 * <p>Title: BaseDelegate </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/12/15 15:51
 * @version : 1.0
 */
abstract class FragmentDelegate(private val fragment: Fragment) : Delegate(fragment.requireActivity() as AppCompatActivity) {

    init {
        @Suppress("SENSELESS_COMPARISON")
        if (fragment.lifecycle == null) {
            fragment.requireActivity().lifecycle.addObserver(this)
        } else {
            fragment.lifecycle.addObserver(this)
        }
    }

    open fun getFragment(): Fragment {
        return fragment
    }

    override fun getViewLoading(): IViewLoading? {
        val fragment = getFragment()
        if (fragment is IViewLoading) {
            return fragment
        }
        val activity = getActivity()
        if (activity is IViewLoading) {
            return activity
        }
        return null
    }
}