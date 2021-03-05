package com.cjf.util.utils;

import android.text.TextUtils;
import android.widget.TextView;

import static java.lang.Math.PI;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/03/21
 *     desc  : 坐标相关工具类
 * </pre>
 */
public final class CoordinateUtils {

    private final static double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
    private final static double A = 6378245.0;
    private final static double EE = 0.00669342162296594323;

    /**
     * BD09 坐标转 GCJ02 坐标
     *
     * @param lng BD09 坐标纬度
     * @param lat BD09 坐标经度
     * @return GCJ02 坐标：[经度，纬度]
     */
    public static double[] bd09ToGcj02(double lng, double lat) {
        double x = lng - 0.0065;
        double y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double gg_lng = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new double[]{gg_lng, gg_lat};
    }

    /**
     * GCJ02 坐标转 BD09 坐标
     *
     * @param lng GCJ02 坐标经度
     * @param lat GCJ02 坐标纬度
     * @return BD09 坐标：[经度，纬度]
     */
    public static double[] gcj02ToBd09(double lng, double lat) {
        double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * X_PI);
        double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * X_PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new double[]{bd_lng, bd_lat};
    }

    /**
     * GCJ02 坐标转 WGS84 坐标
     *
     * @param lng GCJ02 坐标经度
     * @param lat GCJ02 坐标纬度
     * @return WGS84 坐标：[经度，纬度]
     */
    public static double[] gcj02ToWGS84(double lng, double lat) {
        if (outOfChina(lng, lat)) {
            return new double[]{lng, lat};
        }
        double dlat = transformLat(lng - 105.0, lat - 35.0);
        double dlng = transformLng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - EE * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((A * (1 - EE)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (A / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        return new double[]{lng * 2 - mglng, lat * 2 - mglat};
    }

    /**
     * WGS84 坐标转 GCJ02 坐标
     *
     * @param lng WGS84 坐标经度
     * @param lat WGS84 坐标纬度
     * @return GCJ02 坐标：[经度，纬度]
     */
    public static double[] wgs84ToGcj02(double lng, double lat) {
        if (outOfChina(lng, lat)) {
            return new double[]{lng, lat};
        }
        double dlat = transformLat(lng - 105.0, lat - 35.0);
        double dlng = transformLng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - EE * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((A * (1 - EE)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (A / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        return new double[]{mglng, mglat};
    }

    /**
     * BD09 坐标转 WGS84 坐标
     *
     * @param lng BD09 坐标经度
     * @param lat BD09 坐标纬度
     * @return WGS84 坐标：[经度，纬度]
     */
    public static double[] bd09ToWGS84(double lng, double lat) {
        double[] gcj = bd09ToGcj02(lng, lat);
        return gcj02ToWGS84(gcj[0], gcj[1]);
    }


    /**
     * WGS84 坐标转 BD09 坐标
     *
     * @param lng WGS84 坐标经度
     * @param lat WGS84 坐标纬度
     * @return BD09 坐标：[经度，纬度]
     */
    public static double[] wgs84ToBd09(double lng, double lat) {
        double[] gcj = wgs84ToGcj02(lng, lat);
        return gcj02ToBd09(gcj[0], gcj[1]);
    }

    private static double transformLat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLng(double lng, double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    private static boolean outOfChina(double lng, double lat) {
        return lng < 72.004 || lng > 137.8347 || lat < 0.8293 || lat > 55.8271;
    }

    /***
     * 将点位截取小数点前6位
     * @param point 经度或者纬度
     * @return 经度或者纬度 只有前6位
     */
    public static double pointIntercept(double point) {
        String pointStr = getString(point);
        if (pointStr.contains(".")) {
            String[] pointArr = pointStr.split("\\.");
            if (pointArr[1].length() <= 6) {
                return point;
            }
            pointArr[1] = pointArr[1].substring(0, 6);
            return getDouble(pointArr[0] + "." + pointArr[1]);
        } else {
            return point;
        }
    }

    /***
     * 将点位截取小数点前6位
     * @param pointStr 经度或者纬度
     * @return 经度或者纬度 只有前6位
     */
    public static String pointIntercept(String pointStr) {
        if (TextUtils.isEmpty(pointStr) || pointStr.contains("NaN")) {
            return "";
        }
        if (pointStr.contains(".")) {
            String[] pointArr = pointStr.split("\\.");
            if (pointArr[1].length() <= 6) {
                return pointStr;
            }
            pointArr[1] = pointArr[1].substring(0, 6);
            return pointArr[0] + "." + pointArr[1];
        } else {
            return pointStr;
        }
    }


    /**
     * @note double 位置 转换成  分 秒
     */
    public static String dblToLocation(double data) {
        String ret_s = "";
        int tmp_i_du = (int) data;
        ret_s = String.valueOf(tmp_i_du) + "°";
        //度小数部分
        double tmp_d_du = data - tmp_i_du;
        int tmp_i_fen = (int) (tmp_d_du * 60);
        ret_s = ret_s.concat(String.valueOf(tmp_i_fen) + "′");
        double tmp_d_fen = tmp_d_du * 60 - tmp_i_fen;
        double tmp_i_miao = (double) (tmp_d_fen * 60.000);
        ret_s = ret_s.concat(String.format("%.2f", tmp_i_miao) + "″");
        return ret_s;
    }

    /**
     * @note double 位置 转换成  分 秒
     */
    public static double dblToLocation(String data) {
        if (data == null || data.length() == 0) {
            return 0;
        }
        if (!(data.contains("°") && data.contains("′") && data.contains("″"))) {
            throw new StringIndexOutOfBoundsException("数据格式不正确..." + data);
        }
        String duStr = data.split("°")[0];
        String fenStr = data.split("′")[0].split("°")[1];
        String miaoStr = data.split("′")[1].split("″")[0];
        double lonLat = ((Double.valueOf(miaoStr) / 60) + Double.valueOf(fenStr)) / 60 + Double.valueOf(duStr);
        return lonLat;
    }


    private static final double EARTH_RADIUS = 6378137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     */
    public static double getDistance(double lng1, double lat1, double lng2,
                                     double lat2) {
        double a, b, R;
        R = 6378137;//地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (lng1 - lng2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为千米
     */
    public static double getDistanceKilometre(double lng1, double lat1, double lng2,
                                              double lat2) {
        return getDistance(lng1, lat1, lng2, lat2) / 1000;
    }

    /**
     * 根据经纬度和半径计算经纬度范围
     *
     * @param raidus 单位米
     * @return minLat, minLng, maxLat, maxLng
     */
    public static double[] getAround(double lat, double lon, int raidus) {

        Double latitude = lat;
        Double longitude = lon;

        Double degree = (24901 * 1609) / 360.0;
        double raidusMile = raidus;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;

        Double mpdLng = degree * Math.cos(latitude * (PI / 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;
        return new double[]{minLat, minLng, maxLat, maxLng};
    }

    /**
     * 根据经纬度和半径计算经纬度范围
     *
     * @param raidus 单位米
     * @return minLat, minLng, maxLat, maxLng
     */
    public static double[] getAroundDistance(double lat, double lon, int raidus) {

        Double latitude = lat;
        Double longitude = lon;

        Double degree = (24901 * 1609) / 360.0;
        double raidusMile = raidus;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;

        Double mpdLng = degree * Math.cos(latitude * (PI / 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        return new double[]{radiusLat, radiusLng};
    }

    private static String getString(Object value) {
        if (value == null) {
            return "";
        }
        try {
            if (value instanceof TextView) {
                CharSequence text = ((TextView) value).getText();
                if (TextUtils.isEmpty(text)) {
                    return "";
                }
                return text.toString().trim();
            } else {
                String valueStr = "";
                try {
                    valueStr = (String) value;
                } catch (Exception e) {
                    try {
                        valueStr = String.valueOf(value);
                    } catch (Exception e1) {
                        valueStr = value + "";
                    }
                }
                if (TextUtils.equals("null", valueStr)) {
                    return "";
                }
                return valueStr;
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取Double值
     *
     * @param value
     * @return
     */
    private static Double getDouble(Object value) {
        try {
            return (Double) value;
        } catch (Exception e) {
            try {
                return Double.parseDouble(getString(value));
            } catch (Exception e1) {
                try {
                    return Double.valueOf(getString(value));
                } catch (Exception e2) {
                    return 0d;
                }
            }
        }
    }


    /**
     * 经纬度校验 只校验正数 0-90.000000 0-180.000000 范围内 经度longitude:
     * (?:[0-9]|[1-9][0-9]|1[0-7][0-9]|180)\\.([0-9]{6}) 纬度latitude：
     * (?:[0-9]|[1-8][0-9]|90)\\.([0-9]{6})
     *
     * @return
     */
    public static boolean checkLonLat(double longitude, double latitude) {
        return checkLonLat(getString(longitude), getString(latitude));
    }

    /**
     * 经纬度校验 只校验正数 0-90.000000 0-180.000000 范围内 经度longitude:
     * (?:[0-9]|[1-9][0-9]|1[0-7][0-9]|180)\\.([0-9]{6}) 纬度latitude：
     * (?:[0-9]|[1-8][0-9]|90)\\.([0-9]{6})
     *
     * @return
     */
    public static boolean checkLonLat(String longitude, String latitude) {
        if (longitude == null || longitude.length() == 0 ||
                latitude == null || latitude.length() == 0) {
            return false;
        }
        longitude = longitude.trim();
        latitude = latitude.trim();
        if (TextUtils.isEmpty(longitude) || TextUtils.isEmpty(latitude)
            || TextUtils.equals("NaN", longitude) || TextUtils.equals("NaN", latitude)
            || TextUtils.equals("0.0", longitude) || TextUtils.equals("0.0", latitude)
            || TextUtils.equals("0", longitude) || TextUtils.equals("0", latitude)) {
            return false;
        }
        return true;
//        String checkLongitude = "((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,6}))|((?:180)\\.([0]{0,6}))";
//        String checkLatitude = "((?:[0-9]|[1-8][0-9])\\.([0-9]{0,6}))|((?:90)\\.([0]{0,6}))";
//        return longitude.matches(checkLongitude) && latitude.matches(checkLatitude);
    }

}
