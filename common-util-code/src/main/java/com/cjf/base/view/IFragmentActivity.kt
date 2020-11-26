package com.cjf.base.view

import android.app.Activity
import android.os.Bundle
import android.view.View

/**
 * <p>Title: IFragmentActivity </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/11/21 14:04
 * @version : 1.0
 */
interface IFragmentActivity {

    fun getLayoutId(): Int

    fun initView(view: View, savedInstanceState: Bundle?)

    fun initData(view: View, savedInstanceState: Bundle?)


    fun getBaseActivity(): Activity?

    /**
     * 获取Bundle
     *
     * @return 返回Intent中的Bundle
     */
    fun getBundle(): Bundle?

    /**
     * 销毁当前 Fragment 所在的 Activity
     */
    fun finishActivity() {
        val activity = getBaseActivity()
        if (activity != null && !activity.isFinishing) {
            activity.finish()
        }
    }
}