package com.cjf.base.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.CacheDiskUtils;
import com.cjf.util.utils.CoordinateUtils;

/**
 * <p>Title: LocationSourceAndroid </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 9:09
 */
@SuppressLint("MissingPermission")
public class LocationSourceAndroid extends LocationSource implements LocationUtils.OnLocationChangeListener {

    private long minTime;
    private long minDistance;

    public LocationSourceAndroid() {
        this(1000 * 10, 10);
    }

    public LocationSourceAndroid(long minTime, long minDistance) {
        this.minTime = minTime;
        this.minDistance = minDistance;
    }

    @Override
    protected void onStartForegroundLocation(Activity activity) {

    }

    @Override
    protected void onStopForegroundLocation() {

    }

    @Override
    protected void onStart() {
        LocationUtils.register(minTime, minDistance, this);
    }

    @Override
    protected void onStop() {
        LocationUtils.unregister();
    }


    @Override
    public void getLastKnownLocation(android.location.Location location) {

    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        if (location != null && location.getLongitude() != 0 && location.getLatitude() != 0) {
            // 定位成功
            if (!CoordinateUtils.checkLonLat(location.getLongitude(), location.getLatitude())) {
                return;
            }
            updateLocation(new LocationData(location));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public LocationData getLocation() {
        return (LocationData) super.getLocation();
    }

    @Override
    protected void saveCacheLocation(String key, Location location) {
        CacheDiskUtils.getInstance("location").put(key, (Parcelable) new Location(location));
        if (location instanceof LocationData) {
            CacheDiskUtils.getInstance("location").put(key + "androidLocation", ((LocationData) location).getLocation());
        }
    }

    @Override
    protected Location getCacheLocation(String key) {
        LocationSource.Location location = CacheDiskUtils.getInstance("location")
                .getParcelable(key, LocationSource.Location.CREATOR);
        if (location == null) {
            return null;
        }
        android.location.Location androidLocation = CacheDiskUtils.getInstance("location")
                .getParcelable(key + "androidLocation", android.location.Location.CREATOR);
        if (androidLocation == null) {
            return new LocationData(location);
        }
        return new LocationData(location, androidLocation);
    }

    @Override
    public String getCoordType() {
        return CoordType.WGS84.getName();
    }

    public static class LocationData extends LocationSource.Location {

        private android.location.Location location;

        public LocationData() {
            super();
        }

        public LocationData(Location location) {
            super(location);
        }

        public LocationData(Location location, android.location.Location location1) {
            super(location);
            this.location = location1;
        }

        public LocationData(android.location.Location location) {
            super(location);
            this.location = location;
        }

        @Override
        protected void initCoordType() {
            setCoordType(CoordType.WGS84.getName());
        }

        @Override
        public <T> T getLocationFor(Class<T> clazz) {
            return clazz.cast(location);
        }

        public android.location.Location getLocation() {
            return location;
        }

        @Override
        @NonNull
        public String toString() {
            return "LocationData{" +
                    "location=" + location +
                    "} " + super.toString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.location, flags);
        }

        protected LocationData(Parcel in) {
            super(in);
            this.location = in.readParcelable(android.location.Location.class.getClassLoader());
        }

        public static final Creator<LocationData> CREATOR = new Creator<LocationData>() {
            @Override
            public LocationData createFromParcel(Parcel source) {
                return new LocationData(source);
            }

            @Override
            public LocationData[] newArray(int size) {
                return new LocationData[size];
            }
        };
    }
}
