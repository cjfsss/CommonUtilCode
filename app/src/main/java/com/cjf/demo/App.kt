package com.cjf.demo

import android.app.Application
import com.cjf.util.UtilX
import com.cjf.util.function.UtilFunction
import com.cjf.util.toast.ToastX
import com.cjf.util.utils.ResUtils

/**
 * <p>Title: App </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/27 16:54
 * @version : 1.0
 */
class App : Application(), UtilFunction {

    override fun onCreate() {
        super.onCreate()
        UtilX.init(this)
    }

    override fun getApplication(): Application {
        return this
    }

    override fun getPrimaryColor(): Int {
        return ResUtils.getColor(R.color.colorPrimary)
    }
}