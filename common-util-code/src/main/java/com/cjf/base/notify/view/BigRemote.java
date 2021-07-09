package com.cjf.base.notify.view;

import com.cjf.base.app.AppCompatApplication;

/**
 * <p>Title: BigRemote </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/12 11:55
 */
public class BigRemote extends AbRemoteViews {
    public BigRemote(int layoutId) {
        super(AppCompatApplication.getAppCompatApplication().getPackageName(), layoutId);
    }
}
