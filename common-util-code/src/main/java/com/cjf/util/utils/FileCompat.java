package com.cjf.util.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;

/**
 * <p>Title: FileCompat </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/7/12 13:55
 */
public class FileCompat {


    /**
     * 根据文件的路径获取文件的类型String
     *
     * @param filePath
     * @return
     */
    public static String getFileTypeString(String filePath) {
        if (isFileType(filePath, FileType.AUDIO)) {
            return FileType.AUDIO.getType();
        } else if (isFileType(filePath, FileType.IMAGE)) {
            return FileType.IMAGE.getType();
        } else if (isFileType(filePath, FileType.PDF)) {
            return FileType.PDF.getType();
        } else if (isFileType(filePath, FileType.TXT)) {
            return FileType.TXT.getType();
        } else if (isFileType(filePath, FileType.AUDIO)) {
            return FileType.AUDIO.getType();
        } else if (isFileType(filePath, FileType.VIDEO)) {
            return FileType.VIDEO.getType();
        } else if (isFileType(filePath, FileType.CHM)) {
            return FileType.CHM.getType();
        } else if (isFileType(filePath, FileType.WORD)) {
            return FileType.WORD.getType();
        } else if (isFileType(filePath, FileType.EXCEL)) {
            return FileType.EXCEL.getType();
        } else if (isFileType(filePath, FileType.PPT)) {
            return FileType.PPT.getType();
        } else if (isFileType(filePath, FileType.APK)) {
            return FileType.APK.getType();
        }
        return "*/*";
    }

    /**
     * 判断是否为指定类型的文件
     *
     * @param fileName
     * @param type
     * @return
     */
    public static boolean isFileType(String fileName, FileType type) {
        /* 依扩展名的类型决定MimeType */
        String fileEnd = "";
        try {
            int lastIndexOf = fileName.lastIndexOf(".");
            fileEnd = fileName.substring(lastIndexOf, fileName.length()).toLowerCase();
        } catch (Exception e) {
        }
        if (FileType.AUDIO.equals(type)
                && (equalsIgnoreCase(fileEnd, ".m4a") || equalsIgnoreCase(fileEnd, ".mp3")
                || equalsIgnoreCase(fileEnd, ".mid") || equalsIgnoreCase(fileEnd, ".wav")
                || equalsIgnoreCase(fileEnd, ".xmf") || equalsIgnoreCase(fileEnd, ".ogg") || TextUtils
                .equals(fileEnd, "" + ".amr"))) {
            return true;
        } else if (FileType.VIDEO.equals(type)
                && (equalsIgnoreCase(fileEnd, ".3gp") || equalsIgnoreCase(fileEnd, ".mp4")
                || equalsIgnoreCase(fileEnd, "" + "" + ".mts"))) {
            return true;
        } else if (FileType.IMAGE.equals(type)
                && (equalsIgnoreCase(fileEnd, ".jpg") || equalsIgnoreCase(fileEnd, ".gif")
                || equalsIgnoreCase(fileEnd, ".png") || equalsIgnoreCase(fileEnd, ".jpeg")
                || equalsIgnoreCase(fileEnd, ".bmp"))) {
            return true;
        } else if (FileType.EXCEL.equals(type)
                && (equalsIgnoreCase(fileEnd, ".xls") || equalsIgnoreCase(fileEnd, ".xlsx"))) {
            return true;
        } else if (FileType.TXT.equals(type) && equalsIgnoreCase(fileEnd, ".txt")) {
            return true;
        } else if (FileType.WORD.equals(type)
                && (equalsIgnoreCase(fileEnd, ".doc") || equalsIgnoreCase(fileEnd, ".chm")
                || equalsIgnoreCase(fileEnd, ".docx"))) {
            return true;
        } else if (FileType.PDF.equals(type) && (equalsIgnoreCase(fileEnd, ".pdf"))) {
            return true;
        } else if (FileType.PPT.equals(type)
                && (equalsIgnoreCase(fileEnd, ".ppt") || equalsIgnoreCase(fileEnd, ".dps"))) {
            return true;
        } else if (FileType.APK.equals(type) && (equalsIgnoreCase(fileEnd, ".apk"))) {
            return true;
        } else if (FileType.HTML.equals(type) && (equalsIgnoreCase(fileEnd, ".html"))) {
            return true;
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    private static boolean equalsIgnoreCase(final String a, final String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    public static String getFilePathString(String fileName) {
        if (isFileType(fileName, FileType.AUDIO)) {
            return "audio";
        } else if (isFileType(fileName, FileType.IMAGE)) {
            return "image";
        } else if (isFileType(fileName, FileType.PDF)) {
            return "pdf";
        } else if (isFileType(fileName, FileType.TXT)) {
            return "txt";
        } else if (isFileType(fileName, FileType.VIDEO)) {
            return "video";
        } else if (isFileType(fileName, FileType.CHM)) {
            return "chm";
        } else if (isFileType(fileName, FileType.WORD)) {
            return "word";
        } else if (isFileType(fileName, FileType.EXCEL)) {
            return "excel";
        } else if (isFileType(fileName, FileType.PPT)) {
            return "ppt";
        } else if (isFileType(fileName, FileType.APK)) {
            return "apk";
        }
        return "common";
    }

    public enum FileType {

        HTML("text/html"), IMAGE("image/*"), PDF("application/pdf"), TXT("text/plain"), AUDIO("audio/*"), VIDEO("video/*"), CHM(
                "application/x-chm"), WORD("application/msword"), EXCEL("application/vnd.ms-excel"), PPT("application/vnd.ms-powerpoint"), APK(
                "application/vnd.android.package-archive");

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        FileType(String type) {
            this.type = type;
        }
    }

    public static boolean getLaunchTargetOpenFile(@NonNull final String pkgName, @Nullable final String filePath) {
        try {
            Intent launchOpenFile = IntentCompat.getLaunchTargetOpenFile(pkgName, filePath);
            AppCompat.getApp().startActivity(launchOpenFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getLaunchTargetOpenFile(@NonNull final String pkgName, @Nullable final File file) {
        try {
            Intent launchOpenFile = IntentCompat.getLaunchTargetOpenFile(pkgName, file);
            AppCompat.getApp().startActivity(launchOpenFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getLaunchTargetOpenFile(@NonNull final String pkgName,
                                                  @NonNull String authority, @Nullable final String filePath) {
        try {
            Intent launchOpenFile = IntentCompat.getLaunchTargetOpenFile(AppCompat.getApp(), pkgName, authority, filePath);
            AppCompat.getApp().startActivity(launchOpenFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getLaunchTargetOpenFile(@NonNull final String pkgName,
                                                  @NonNull String authority, @Nullable final File file) {
        try {
            Intent launchOpenFile = IntentCompat.getLaunchTargetOpenFile(AppCompat.getApp(), pkgName, authority, file);
            AppCompat.getApp().startActivity(launchOpenFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getLaunchTargetOpenFile(@NonNull Context context, @NonNull final String pkgName,
                                                  @NonNull String authority, @Nullable final String filePath) {
        try {
            Intent launchOpenFile = IntentCompat.getLaunchTargetOpenFile(context, pkgName, authority, filePath);
            AppCompat.getApp().startActivity(launchOpenFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getLaunchTargetOpenFile(@NonNull Context context, @NonNull final String pkgName,
                                                  @NonNull String authority, @Nullable final File file) {
        try {
            Intent launchOpenFile = IntentCompat.getLaunchTargetOpenFile(context, pkgName, authority, file);
            AppCompat.getApp().startActivity(launchOpenFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getLaunchOpenFile(@Nullable String file) {
        try {
            Intent launchOpenFile = IntentCompat.getLaunchOpenFile(file);
            AppCompat.getApp().startActivity(launchOpenFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getLaunchOpenFile(@Nullable File file) {
        try {
            Intent launchOpenFile = IntentCompat.getLaunchOpenFile(file);
            AppCompat.getApp().startActivity(launchOpenFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getLaunchOpenFile(@NonNull String authority, @Nullable String filePath) {
        try {
            Intent launchOpenFile = IntentCompat.getLaunchOpenFile(authority, filePath);
            AppCompat.getApp().startActivity(launchOpenFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getLaunchOpenFile(@NonNull String authority, @NonNull File file) {
        try {
            Intent launchOpenFile = IntentCompat.getLaunchOpenFile(authority, file);
            AppCompat.getApp().startActivity(launchOpenFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getLaunchOpenFile(@NonNull Context context, @NonNull String authority, @Nullable String filePath) {
        try {
            Intent launchOpenFile = IntentCompat.getLaunchOpenFile(context, authority, filePath);
            context.startActivity(launchOpenFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openFile(@NonNull Context context, @NonNull String authority, @NonNull File file) {
        try {
            Intent launchOpenFile = IntentCompat.getLaunchOpenFile(context, authority, file);
            context.startActivity(launchOpenFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openWPS(@NonNull String filePath) {
        try {
            AppCompat.getApp().startActivity(IntentCompat.getLaunchOpenWPS(AppCompat.getApp(), filePath));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openWPS(@NonNull Context context, @NonNull String filePath) {
        try {
            AppCompat.getApp().startActivity(IntentCompat.getLaunchOpenWPS(context, filePath));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openWPS(@NonNull Context context, @NonNull String authority, @Nullable String filePath) {
        try {
            AppCompat.getApp().startActivity(IntentCompat.getLaunchOpenWPS(context, authority, filePath));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openWPS(@NonNull File file) {
        try {
            AppCompat.getApp().startActivity(IntentCompat.getLaunchOpenWPS(file));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openWPS(@NonNull Context context, @NonNull File file) {
        try {
            context.startActivity(IntentCompat.getLaunchOpenWPS(context,file));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openWPS(@NonNull Context context, @NonNull String authority, @NonNull File file) {
        try {
            context.startActivity(IntentCompat.getLaunchOpenWPS(context,authority,file));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
