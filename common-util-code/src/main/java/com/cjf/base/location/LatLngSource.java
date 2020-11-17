package com.cjf.base.location;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * <p>Title: LatLngSource </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/19 14:59
 */
public class LatLngSource implements Parcelable {

    private double latitude = 0.0;
    private double longitude = 0.0;

    public LatLngSource() {
    }

    public LatLngSource(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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

    @Override
    @NonNull
    public String toString() {
        return "LatLngSource{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    protected LatLngSource(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Parcelable.Creator<LatLngSource> CREATOR = new Parcelable.Creator<LatLngSource>() {
        @Override
        public LatLngSource createFromParcel(Parcel source) {
            return new LatLngSource(source);
        }

        @Override
        public LatLngSource[] newArray(int size) {
            return new LatLngSource[size];
        }
    };
}
