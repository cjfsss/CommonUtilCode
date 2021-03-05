package com.cjf.base.location

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 * <p>Title: BaseViewModel </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/8/5 13:06
 * @version : 1.0
 */
open class LocationViewModel(application: Application,
                             val minTime: Long = 1000 * 10L,
                             val minDistance: Long = 10L) : AndroidViewModel(application),
        LocationSourceListener {

    // 是否开启上传
    private var isOpenUpload = false

    private val mLocationDataSource by lazy {
        LocationSourceAndroid(minTime, minDistance)
    }

    override fun addLocationChangedListener(listener: LocationSource.LocationChangedListener?) {
        mLocationDataSource.addLocationChangedListener(listener);
    }

    override fun removeLocationChangedListener(listener: LocationSource.LocationChangedListener?) {
        mLocationDataSource.removeLocationChangedListener(listener)
    }

    override fun addStatusChangedListener(listener: LocationSource.StatusChangedListener?) {
        mLocationDataSource.addStatusChangedListener(listener)
    }

    override fun removeStatusChangedListener(listener: LocationSource.StatusChangedListener?) {
        mLocationDataSource.removeStatusChangedListener(listener)
    }

    override fun setUploadLocationListener(listener: LocationSource.LocationChangedListener) {
        mLocationDataSource.setUploadLocationListener(listener)
    }

    override fun getError(): Throwable {
        return mLocationDataSource.error
    }

    override fun startForegroundLocation(activity: Activity?) {
        mLocationDataSource.startForegroundLocation(activity)
    }

    override fun stopForegroundLocation() {
        mLocationDataSource.stopForegroundLocation()
    }

    /**
     * 开启定位
     */
    override fun startLocation() {
        //判断是否开启了，没有开启就开启
        mLocationDataSource.startLocation()
    }

    /**
     * 停止定位服务
     */
    override fun stopLocation() {
        //判断服务是否开启了，若开启了则停止
        mLocationDataSource.stopLocation()
    }

    override fun updateLocation(location: LocationSource.Location?) {
        if (location != null) {
            mLocationDataSource.updateLocation(location);
        }
    }

    override fun notifyLocation() {
        mLocationDataSource.notifyLocation()
    }

    override fun getLocation(): LocationSource.Location? {
        return mLocationDataSource.location
    }

    override fun getLastKnownLocation(): LocationSource.Location {
        return mLocationDataSource.lastKnownLocation
    }

    override fun isLatLngRepeated(): Boolean {
        return mLocationDataSource.isLatLngRepeated
    }

    /**
     * 立即执行
     */
    override fun notifyUploadLocation() {
        mLocationDataSource.notifyUploadLocation()
    }

    override fun setUploadTimeInterval(positionTimeInterval: Int) {
        mLocationDataSource.uploadTimeInterval = positionTimeInterval
    }

    override fun getUploadTimeInterval(): Int {
        return mLocationDataSource.uploadTimeInterval
    }

    private fun isOpenUpload(): Boolean {
        return isOpenUpload
    }

    fun setOpenUpload(isOpenUpload: Boolean) {
        this.isOpenUpload = isOpenUpload
    }

    override fun uploadLocation(location: LocationSource.Location?) {
        if (!isOpenUpload()) {
            return
        }
        mLocationDataSource.uploadLocation(location)
    }

}