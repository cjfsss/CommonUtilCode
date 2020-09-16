package com.cjf.base.permission

import android.content.Context
import android.text.TextUtils
import com.cjf.util.R
import com.cjf.util.utils.ResUtils
import com.lxj.xpopup.XPopup
import com.yanzhenjie.permission.Rationale
import com.yanzhenjie.permission.RequestExecutor
import com.yanzhenjie.permission.runtime.Permission

/**
 * <p>Title: RuntimeRationale </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/8/12 13:28
 * @version : 1.0
 */
class RuntimeRationale : Rationale<MutableList<String>> {
    override fun showRationale(
        context: Context,
        data: MutableList<String>,
        executor: RequestExecutor
    ) {

        val permissionNames: List<String> = Permission.transformText(context, data)
        val message = context.getString(
            R.string.common_permission_rationale,
            TextUtils.join("\n", permissionNames)
        )
        XPopup.Builder(context)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .asConfirm(ResUtils.getString(R.string.title), message,
                {
                    executor.execute()
                }, {
                    executor.cancel()
                }).show()
    }
}