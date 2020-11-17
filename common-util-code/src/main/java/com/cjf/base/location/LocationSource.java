package com.cjf.base.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;
import com.cjf.thread.executor.ThreadTaskExecutor;
import com.cjf.util.utils.CoordinateUtils;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>Title: LocationDataSource </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/17 23:06
 */
public abstract class LocationSource implements LocationSourceListener {

    private final AtomicReference<Status> mStartStatus;
    private LocationSource.Location mCurrentLocation;
    private LocationSource.Location mLastKnownLocation;
    private Throwable mThrowable;
    private final List<LocationChangedListener> mLocationChangedListeners = new ArrayList<>();
    private final List<StatusChangedListener> mStatusChangedListeners = new ArrayList<>();
    private LocationSource.LocationChangedListener mUploadLocationListener;
    private long oldTime;
    // 3分钟
    private int mUploadPositionTimeInterval = 0;
    // 是否立即上传
    private boolean isImmediatelyUpload = false;

    protected LocationSource() {
        this.mStartStatus = new AtomicReference<>(LocationSource.Status.STOPPED);
    }

    public final void addLocationChangedListener(final LocationSource.LocationChangedListener listener) {
        if (!mLocationChangedListeners.contains(listener)) {
            mLocationChangedListeners.add(listener);
        }
    }

    public final void removeLocationChangedListener(LocationSource.LocationChangedListener listener) {
        mLocationChangedListeners.remove(listener);
    }

    public final void addStatusChangedListener(final LocationSource.StatusChangedListener listener) {
        if (!mStatusChangedListeners.contains(listener)) {
            mStatusChangedListeners.add(listener);
        }
    }

    public final void removeStatusChangedListener(LocationSource.StatusChangedListener listener) {
        mStatusChangedListeners.remove(listener);
    }

    public void setUploadLocationListener(LocationChangedListener mUploadLocationListener) {
        this.mUploadLocationListener = mUploadLocationListener;
    }

    public final Throwable getError() {
        return this.mThrowable;
    }

    private void setLastKnownLocation(Location mLastKnownLocation) {
        this.mLastKnownLocation = mLastKnownLocation;
    }

    protected final void setCurrentLocation(LocationSource.Location location) {
        this.mCurrentLocation = location;
    }

    protected void changedStatus(LocationSource.Status status) {
        mStartStatus.set(status);
        changedStatus();
    }

    private void changedStatus() {
        StatusChangedEvent locationChangedEvent = new StatusChangedEvent(this, mStartStatus.get());
        for (StatusChangedListener listener : mStatusChangedListeners) {
            if (listener != null) {
                listener.statusChanged(locationChangedEvent);
            }
        }
    }

    public final void startForegroundLocation(Activity activity) {
        onStartForegroundLocation(activity);
    }

    protected abstract void onStartForegroundLocation(Activity activity);

    public final void stopForegroundLocation() {
        onStopForegroundLocation();
    }

    protected abstract void onStopForegroundLocation();


    public final void startLocation() {
        if (this.mStartStatus.get() == LocationSource.Status.STOPPED || this.mStartStatus.get() == LocationSource.Status.FAILED_TO_START) {
            this.mThrowable = null;
            onStart();
            changedStatus(Status.STARTING);
        } else {
            notifyLocation();
        }
    }

    protected abstract void onStart();

    public void stopLocation() {
        if (isStarted()) {
            onStop();
            changedStatus(Status.STOPPED);
        }
    }

    protected abstract void onStop();

    /**
     * 更新点位
     *
     * @param location 点位
     */
    public final void updateLocation(LocationSource.Location location) {
        if (location != null) {
            Location lastKnownLocation = getLocation();
            if (lastKnownLocation != null) {
                // 先记录上一次的点位
                setLastKnownLocation(lastKnownLocation);
                // 保存上一次的点位
                saveCacheLocation(getLastKnownLocationKey(), lastKnownLocation);
            }
            // 设置本次的点位
            setCurrentLocation(location);
            // 保存本次的点位
            saveCacheLocation(getCurrentLocationKey(), location);
        }
        notifyLocation();
    }


    @Override
    public void notifyLocation() {
        Location location = getLocation();
        LocationChangedEvent locationChangedEvent = new LocationChangedEvent(this, location);
        for (LocationChangedListener listener : mLocationChangedListeners) {
            if (listener != null) {
                listener.locationChanged(locationChangedEvent);
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void uploadLocation(LocationSource.Location location) {
        if (mUploadLocationListener == null || location == null) {
            return;
        }
        ThreadTaskExecutor.getInstance().postIo(new Runnable() {
            @Override
            public void run() {
                oldTime = SPUtils.getInstance("location").getLong("uploadTimeInterval", 0L);
                // 5分钟上传一次
                long newTime = System.currentTimeMillis();
                // 延迟时间 5分钟
                long intervalTime = newTime - oldTime;
                if (isImmediatelyUpload || intervalTime >= getUploadTimeInterval() * 1000) {
                    if (!NetworkUtils.isConnected()) {
                        return;
                    }
                    // 重置时间
                    oldTime = System.currentTimeMillis();
                    SPUtils.getInstance("location").put("uploadTimeInterval", oldTime);
                    // 开始上传
                    if (mUploadLocationListener != null) {
                        ThreadTaskExecutor.getInstance().postToMain(new Runnable() {
                            @Override
                            public void run() {
                                mUploadLocationListener.locationChanged(new LocationChangedEvent(LocationSource.this, location));
                                // 关闭立即上传
                                isImmediatelyUpload = false;
                            }
                        });
                    }
                }
            }
        });
    }

    public void notifyUploadLocation() {
        isImmediatelyUpload = true;
        Location location = getLocation();
        if (location != null) {
            uploadLocation(location);
        }
    }

    public int getUploadTimeInterval() {
        if (mUploadPositionTimeInterval == 0) {
            mUploadPositionTimeInterval = SPUtils.getInstance("location").getInt("uploadPositionTimeInterval", 120);
        }
        return mUploadPositionTimeInterval;
    }

    public void setUploadTimeInterval(int uploadPositionTimeInterval) {
        this.mUploadPositionTimeInterval = uploadPositionTimeInterval;
        SPUtils.getInstance("location").put("uploadPositionTimeInterval", uploadPositionTimeInterval);
    }

    public LocationSource.Location getLocation() {
        if (mCurrentLocation != null) {
            return mCurrentLocation;
        }
        return getCacheLocation(getCurrentLocationKey());
    }

    @Override
    public Location getLastKnownLocation() {
        if (mLastKnownLocation != null) {
            return mLastKnownLocation;
        }
        return getCacheLocation(getLastKnownLocationKey());
    }

    @Override
    public boolean isLatLngRepeated() {
        Location location = getLocation();
        if (location == null) {
            return false;
        }
        Location lastKnownLocation = getLastKnownLocation();
        if (lastKnownLocation == null) {
            return false;
        }
        // 判断本次和上一次经纬度是否一样
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        double lastLongitude = lastKnownLocation.getLongitude();
        double lastLatitude = lastKnownLocation.getLatitude();
        return longitude == lastLongitude && latitude == lastLatitude;
    }

    protected String getCurrentLocationKey() {
        return "currentLocationSource-" + getCoordType();
    }

    protected String getLastKnownLocationKey() {
        return "lastKnownLocationSource-" + getCoordType();
    }

    protected abstract void saveCacheLocation(String key, Location location);

    protected abstract LocationSource.Location getCacheLocation(String key);

    public abstract String getCoordType();

    public boolean isStarted() {
        return mStartStatus.get() == Status.STARTING;
    }

    public interface StatusChangedListener {
        void statusChanged(LocationSource.StatusChangedEvent statusChangedEvent);
    }

    public static final class StatusChangedEvent extends EventObject {
        private final LocationSource mSource;
        private final LocationSource.Status mStatus;

        public StatusChangedEvent(LocationSource source, LocationSource.Status status) {
            super(source);
            this.mSource = source;
            this.mStatus = status;
        }

        public LocationSource getSource() {
            return this.mSource;
        }

        public LocationSource.Status getStatus() {
            return this.mStatus;
        }
    }

    public static enum Status {
        STOPPED,
        STARTING,
        STARTED,
        FAILED_TO_START;

        private Status() {
        }
    }

    public interface LocationChangedListener {
        void locationChanged(LocationSource.LocationChangedEvent locationChangedEvent);
    }

    public enum CoordType {

        BD09LL("bd09ll"),//（百度经纬度坐标）
        BD09MC("bd09mc"),//（百度墨卡托坐标）
        GCJ02("gcj02"),//（经国测局加密的坐标）
        WGS84("wgs84"),//（gps获取的原始坐标）
        MAPBAR("MAPBAR"),//图吧坐标
        MAPABC("MAPABC"),//图盟坐标
        SOSOMAP("SOSOMAP"),//搜搜坐标
        ALIYUN("ALIYUN"),//阿里云
        GOOGLE("GOOGLE");//谷歌坐标

        // 成员变量
        private String name;

        CoordType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    public static final class LocationChangedEvent extends EventObject {
        private final LocationSource mSource;
        private final LocationSource.Location mLocation;

        public LocationChangedEvent(LocationSource source, LocationSource.Location location) {
            super(source);
            this.mSource = source;
            this.mLocation = location;
        }

        public LocationSource getSource() {
            return this.mSource;
        }

        public LocationSource.Location getLocation() {
            return this.mLocation;
        }
    }

    public static class Location implements Parcelable {

        private LatLngSource latLngSource;
        private double altitude = 0.0f;
        private double horizontalAccuracy = 0.0f;
        private double verticalAccuracy = 0.0;
        private float speed = 0.0f;
        private float bearing = 0.0f;
        private double latitude = 0;
        private double longitude = 0;
        private String provider;
        private long timeStamp;
        private String coordType;

        public Location() {
            initCoordType();
        }

        public Location(Location location) {
            this(location.latitude, location.longitude);
            altitude = location.getAltitude();
            speed = location.getSpeed();
            bearing = location.getBearing();
            provider = location.getProvider();
            horizontalAccuracy = location.getHorizontalAccuracy();
            verticalAccuracy = (double) location.getVerticalAccuracy();
        }

        public Location(android.location.Location location) {
            this(location.getLatitude(), location.getLongitude());
            altitude = location.getAltitude();
            speed = location.getSpeed();
            bearing = location.getBearing();
            provider = location.getProvider();
            horizontalAccuracy = location.getAccuracy();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                verticalAccuracy = (double) location.getVerticalAccuracyMeters();
            }
        }

        public Location(double latitude, double longitude) {
            initCoordType();
            this.latitude = CoordinateUtils.pointIntercept(latitude);
            this.longitude = CoordinateUtils.pointIntercept(longitude);
            latLngSource = new LatLngSource(this.latitude, this.longitude);
            timeStamp = System.currentTimeMillis();
        }

        public Location(double latitude, double longitude, double altitude, double horizontalAccuracy, double verticalAccuracy, float speed, float bearing) {
            this(latitude, longitude);
            this.altitude = altitude;
            this.horizontalAccuracy = horizontalAccuracy;
            this.verticalAccuracy = verticalAccuracy;
            this.speed = speed;
            this.bearing = bearing;
        }

        protected void initCoordType() {
        }

        public <T> T getLocationFor(Class<T> clazz) {
            return null;
        }

        public LatLngSource getLatLngSource() {
            return latLngSource;
        }

        public void setLatLngSource(LatLngSource latLngSource) {
            this.latLngSource = latLngSource;
        }

        public double getAltitude() {
            return altitude;
        }

        public void setAltitude(double altitude) {
            this.altitude = altitude;
        }

        public double getHorizontalAccuracy() {
            return horizontalAccuracy;
        }

        public void setHorizontalAccuracy(double horizontalAccuracy) {
            this.horizontalAccuracy = horizontalAccuracy;
        }

        public double getVerticalAccuracy() {
            return verticalAccuracy;
        }

        public void setVerticalAccuracy(double verticalAccuracy) {
            this.verticalAccuracy = verticalAccuracy;
        }

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public float getBearing() {
            return bearing;
        }

        public void setBearing(float bearing) {
            this.bearing = bearing;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getCoordType() {
            return coordType;
        }

        public void setCoordType(String coordType) {
            this.coordType = coordType;
        }

        @Override
        @NonNull
        public String toString() {
            return "Location{" +
                    "latLngSource=" + latLngSource.toString() + ", altitude=" + altitude +
                    ", horizontalAccuracy=" + horizontalAccuracy +
                    ", verticalAccuracy=" + verticalAccuracy +
                    ", speed=" + speed + ", bearing=" + bearing +
                    ", latitude=" + latitude + ", longitude=" + longitude +
                    ", provider='" + provider + '\'' + ", timeStamp=" + timeStamp +
                    ", coordType='" + coordType + '\'' + '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.latLngSource, flags);
            dest.writeDouble(this.altitude);
            dest.writeDouble(this.horizontalAccuracy);
            dest.writeDouble(this.verticalAccuracy);
            dest.writeFloat(this.speed);
            dest.writeFloat(this.bearing);
            dest.writeDouble(this.latitude);
            dest.writeDouble(this.longitude);
            dest.writeString(this.provider);
            dest.writeLong(this.timeStamp);
            dest.writeString(this.coordType);
        }

        protected Location(Parcel in) {
            this.latLngSource = in.readParcelable(LatLngSource.class.getClassLoader());
            this.altitude = in.readDouble();
            this.horizontalAccuracy = in.readDouble();
            this.verticalAccuracy = in.readDouble();
            this.speed = in.readFloat();
            this.bearing = in.readFloat();
            this.latitude = in.readDouble();
            this.longitude = in.readDouble();
            this.provider = in.readString();
            this.timeStamp = in.readLong();
            this.coordType = in.readString();
        }

        public static final Creator<Location> CREATOR = new Creator<Location>() {
            @Override
            public Location createFromParcel(Parcel source) {
                return new Location(source);
            }

            @Override
            public Location[] newArray(int size) {
                return new Location[size];
            }
        };
    }
}
