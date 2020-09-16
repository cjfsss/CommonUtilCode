package com.cjf.util.path;

import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cjf.util.UtilX;

import java.io.File;

/**
 * <p>Title: PathManager </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/2/28 22:04
 */
public class PathManager {

    private static final String TAG = "PathManager";

    @Nullable
    public static String getExternalDir(@NonNull String path) {
        try {
            if (SDCardUtils.isSDCardEnableByEnvironment()
                    && !TextUtils.isEmpty(SDCardUtils.getSDCardPathByEnvironment())) {
                // 外部存储可以使用
                String environment = SDCardUtils.getSDCardPathByEnvironment();
                if (!TextUtils.isEmpty(environment)) {
                    // /storage/emulated/0
                    return environment + File.separator + path;
                }
                return getExternalRoot(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getExternalRoot(path);
    }

    @Nullable
    public static String getExternalDownloadDir() {
        return getExternalDir(Environment.DIRECTORY_DOWNLOADS);
    }

    @Nullable
    public static String getExternalDcimDir() {
        return getExternalDir(Environment.DIRECTORY_DCIM);
    }

    @Nullable
    public static String getExternalMovieDir() {
        return getExternalDir(Environment.DIRECTORY_MOVIES);
    }

    @Nullable
    public static String getExternalMusicDir() {
        return getExternalDir(Environment.DIRECTORY_MUSIC);
    }

    @Nullable
    public static String getExternalPictureDir() {
        return getExternalDir(Environment.DIRECTORY_PICTURES);
    }

    /**
     * 获取内部存储
     */
    @NonNull
    public static String getRoot(@NonNull String type) {
        @Nullable
        File root = UtilX.getApplication().getFilesDir();
        if (root != null) {
            // /data/data/com.learn.test/files
            return root.getAbsolutePath() + File.separator + type;
        }
        // /data/data/com.learn.test/cache
        return UtilX.getApplication().getCacheDir().getAbsolutePath() + File.separator + type;
    }

    /**
     * 获取外部存储
     *
     * @param type 文件夹的类型
     * @return
     */
    @NonNull
    public static String getExternalRoot(@NonNull String type) {
        @Nullable
        File root = UtilX.getApplication().getExternalFilesDir(type);
        if (root != null) {
            // /storage/emulated/0/Android/data/com.learn.test/files
            return root.getAbsolutePath();
        }
        // 使用内部存储
        return getRoot(type);
    }

    @NonNull
    public static String getDir(@NonNull String path) {
        @Nullable
        String dir = getExternalDownloadDir();
        if (TextUtils.isEmpty(dir)) {
            dir = getExternalRoot(Environment.DIRECTORY_DOWNLOADS);
        }
        return dir + File.separator + path;
    }

    @NonNull
    public static String getDataDownloadDir() {
        return getExternalRoot(Environment.DIRECTORY_DOWNLOADS);
    }

    @NonNull
    public static String getDataDcimDir() {
        return getExternalRoot(Environment.DIRECTORY_DCIM);
    }

    @NonNull
    public static String getDataMovieDir() {
        return getExternalRoot(Environment.DIRECTORY_MOVIES);
    }

    @NonNull
    public static String getDataMusicDir() {
        return getExternalRoot(Environment.DIRECTORY_MUSIC);
    }

    @NonNull
    public static String getDataPictureDir() {
        return getExternalRoot(Environment.DIRECTORY_PICTURES);
    }

    @NonNull
    public static String getDataBaseDir() {
        return getExternalRoot("database");
    }

    @NonNull
    public static String getLogDir() {
        return getDir("log") + File.separator +
               UtilX.getApplication().getPackageName();
    }

    @NonNull
    public static String getZipDir() {
        return getDir("zip");
    }

    @NonNull
    public static String getApkDir() {
        return getDir("apk");
    }

    @NonNull
    public static String getMapDir() {
        return getDir("map");
    }

    @NonNull
    public static String getPrivateMapDir() {
        return getExternalRoot("map");
    }

}
