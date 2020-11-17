package com.cjf.base.location;

import android.app.Activity;
import android.content.Context;

import com.cjf.util.UtilX;

/**
 * <p>Title: LocationSourceListener </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/18 23:27
 */
public interface LocationSourceListener {


    void addLocationChangedListener(final LocationSource.LocationChangedListener listener);

    void removeLocationChangedListener(LocationSource.LocationChangedListener listener);

    void addStatusChangedListener(final LocationSource.StatusChangedListener listener);

    void removeStatusChangedListener(LocationSource.StatusChangedListener listener);

    void setUploadLocationListener(LocationSource.LocationChangedListener listener);

    Throwable getError();

    LocationSource.Location getLocation();

    LocationSource.Location getLastKnownLocation();

    /**
     * 本次经纬度和上一次经纬度是否重复
     * @return true 重复
     */
    boolean isLatLngRepeated();

    default Context getContext() {
        return UtilX.getApplication();
    }

    void uploadLocation(LocationSource.Location location);

    void notifyUploadLocation();

    void updateLocation(LocationSource.Location location);

    void notifyLocation();

    void startLocation();

    void stopLocation();

    void startForegroundLocation(Activity activity);

    void stopForegroundLocation();

    void setUploadTimeInterval(int uploadPositionTimeInterval);

    int getUploadTimeInterval();
}
