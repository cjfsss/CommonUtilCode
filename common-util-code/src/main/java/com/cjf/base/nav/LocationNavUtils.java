package com.cjf.base.nav;

import android.app.Activity;
import android.content.pm.PackageInfo;

import com.cjf.base.nav.baidu.BaiduMapNavigation;
import com.cjf.util.UtilX;
import com.cjf.util.toast.ToastX;

import java.util.List;

/**
 * <p>Title: LocationNavUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 9:31
 */
public class LocationNavUtils {

    /**
     * 判断手机中是否有百度app
     */
    public static boolean isNavigationApkBaiDu() {
        return isNavigationApk("com.baidu.BaiduMap");
    }

    /**
     * 判断手机中是否有高德app
     */
    public static boolean isNavigationApkAMap() {
        return isNavigationApk("com.autonavi.minimap");
    }

    /**
     * 判断手机中是否有导航app
     *
     * @param packageName 包名
     */
    public static boolean isNavigationApk(String packageName) {
        List<PackageInfo> packages = UtilX.getApplication().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if (packageInfo.packageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

}
