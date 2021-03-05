package com.cjf.base.permission;

import android.app.Activity;

import androidx.core.content.PermissionChecker;

import com.yanzhenjie.permission.runtime.Permission;

/**
 * <p>Title: PermissionUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/1/30 19:13
 */
public class PermissionUtils {

    /**
     * 是否有权限
     *
     * @param activity    上下文
     * @param permissions 权限
     * @return true 有权限
     */
    public static boolean checkSelfPermission(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (PermissionChecker.checkSelfPermission(activity, permission) != PermissionChecker.PERMISSION_GRANTED) {
                // 没有权限
                return false;
            }
        }
        return true;
    }
}
