package com.cjf.base.activity

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.cjf.base.permission.PermissionUtils
import com.cjf.base.permission.RuntimeRationale
import com.cjf.util.R
import com.cjf.util.utils.ResUtils
import com.lxj.xpopup.XPopup
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import com.yanzhenjie.permission.runtime.PermissionDef


/**
 * <p>Title: AndActivity </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/8/12 13:25
 * @version : 1.0
 */
abstract class AndPermissionActivity : BaseActivity() {

    private var permissions: Array<String>? = null

    /**
     * Request permissions.
     */
    fun requestPermission(@PermissionDef permissions: Array<String>) {
        this.permissions = permissions
        // 获取需要申请的权限
        val needPermissionList: List<String> = PermissionUtils.getNeedPermissionList(this, permissions)
        if (needPermissionList.isEmpty()) {
            // 没有需要申请的权限，不需要再进行申请了
            onGrantedSuccess()
            return
        }
        if (AndPermission.hasPermissions(this, permissions)) {
            onGrantedSuccess()
            return
        }
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(RuntimeRationale())
                .onGranted {
                    onGrantedSuccess()
                }
                .onDenied {
                    showSettingDialog(this@AndPermissionActivity, permissions.asList())
                }
                .start()
    }

    abstract fun onGrantedSuccess()

    open fun gotoSettingCancel() {

    }

    /**
     * Display setting dialog.
     */
    private fun showSettingDialog(
            context: Context,
            permissions: List<String>
    ) {
        val permissionNames: List<String> = Permission.transformText(context, permissions)
        val message: String = context.getString(
                R.string.common_permission_always_failed,
                TextUtils.join("\n", permissionNames)
        )
        XPopup.Builder(context)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asConfirm(
                        ResUtils.getString(R.string.title), message, ResUtils.getString(R.string.cancel), ResUtils.getString(R.string.common_permission_authorization),
                        { setPermission() },
                        { gotoSettingCancel() }, false
                ).show()
    }

    /**
     * Set permissions.
     */
    private fun setPermission() {
        AndPermission.with(this).runtime().setting().start(1000)
    }

    override fun onActivityResult(
            requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        onPermissionResult(requestCode)
    }

    protected open fun onPermissionResult(requestCode: Int) {
        when (requestCode) {
            1000 -> {
                permissions?.let {
                    requestPermission(it)
                }
            }
        }
    }
}