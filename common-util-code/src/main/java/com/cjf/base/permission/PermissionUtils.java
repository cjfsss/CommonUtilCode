package com.cjf.base.permission;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.content.PermissionChecker;

import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 是否有权限
     *
     * @param activity    上下文
     * @param permissions 权限
     * @return true 有权限
     */
    public static List<String> getNeedPermissionList(Activity activity, String[] permissions) {
        // 需要申请的权限
        List<String> needPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (PermissionChecker.checkSelfPermission(activity, permission) != PermissionChecker.PERMISSION_GRANTED) {
                // 该权限还没有被申请
                needPermissionList.add(permission);
            }
        }
        return needPermissionList;
    }

    /**
     * 是否有权限
     *
     * @param activity    上下文
     * @param permissions 权限
     * @return true 有权限
     */
    public static List<String> getNeedPermissionList(Activity activity, String[] permissions,
                                                     int[] grantResult) {
        // 需要申请的权限
        List<String> needPermissionList = new ArrayList<>();
        int grantResultsSize = grantResult.length;
        for (int i = 0; i < grantResultsSize; i++) {
            if (grantResult[i] != PackageManager.PERMISSION_GRANTED) {
                // 授权失败
                needPermissionList.add(permissions[i]);
            }
        }
        return needPermissionList;
    }
}
