package com.cjf.util.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.cjf.util.R;
import com.cjf.util.toast.ToastX;

import java.io.File;
import java.util.Locale;

/**
 * @Packname: com.mapuni.core.support.utils
 * @ClassName: FilePickerHelper
 * @Date: 2018/4/20 16:21
 * @Version: 1.0
 * @Author: CaiJunFeng on
 * @Description:
 */
public class FilePickerUtils {


    /**
     * 打开文件。
     *
     * @param context
     * @param filePath
     */
    public static void openFileAbsPath(Context context, String filePath) {
        if (isFileExist(filePath)) {
            /* 依扩展名的类型决定MimeType */
            File file = new File(filePath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = null;
            String type = getFileTypeString(filePath);
            String defaultCategory = "android.intent.category.DEFAULT";
            if (isFileType(filePath, FileType.AUDIO)) {
                type = FileType.AUDIO.getType();
                intent.addCategory(defaultCategory);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("oneshot", 0);
                intent.putExtra("configchange", 0);
            } else if (isFileType(filePath, FileType.VIDEO)) {
                type = FileType.VIDEO.getType();
                intent.addCategory(defaultCategory);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("oneshot", 0);
                intent.putExtra("configchange", 0);
            } else if (isFileType(filePath, FileType.IMAGE) || isFileType(filePath, FileType.PPT) || isFileType(filePath, FileType.EXCEL)
                       || isFileType(filePath, FileType.PPT) || isFileType(filePath, FileType.WORD) || isFileType(filePath, FileType.TXT)) {
                intent.addCategory(defaultCategory);

            } else if (isFileType(filePath, FileType.HTML)) {
                uri = Uri.parse(file.getAbsolutePath()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content")
                         .encodedPath(file.getAbsolutePath()).build();
            } else {
                intent.addCategory(defaultCategory);
            }
            if (Build.VERSION.SDK_INT >= 23) {
                // 加入读取权限
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    uri = FileProvider
                            .getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
                } catch (Exception e) {
                    ToastX.showShort("附件无法打开，请下载相关软件");
                    return;
                }
            } else {
                uri = Uri.fromFile(file);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, type);
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                ToastX.showShort("附件无法打开，请下载相关软件!");
            }
        } else {
            ToastX.showShort("附件不存在!");
        }
    }

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

    public static enum FileType {

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

        private FileType(String type) {
            this.type = type;
        }

    }

    /**
     * 获取附件的显示图标
     *
     * @param fileName
     * @return
     * @author CaiJunFeng
     */
    public static int getFileIcon(String fileName) {
        if (isFileType(fileName, FileType.IMAGE)) {
            return R.mipmap.attachment_picture;
        } else if (isFileType(fileName, FileType.PDF)) {
            return R.mipmap.attachment_pdf;
        } else if (isFileType(fileName, FileType.PPT)) {
            return R.mipmap.attachment_other;
        } else if (isFileType(fileName, FileType.TXT)) {
            return R.mipmap.attachment_textfile;
        } else if (isFileType(fileName, FileType.WORD)) {
            return R.mipmap.attachment_word;
        } else if (isFileType(fileName, FileType.EXCEL)) {
            return R.mipmap.attachment_excel;
        } else if (isFileType(fileName, FileType.VIDEO)) {
            return R.mipmap.attachment_vedio;
        } else if (isFileType(fileName, FileType.AUDIO)) {
            return R.mipmap.attachment_audio;
        } else if (isFileType(fileName, FileType.HTML)) {
            return R.mipmap.attachment_other;
        }
        return R.mipmap.attachment_other;
    }

    /**
     * 判断文件是否存在
     *
     * @param file
     * @return
     */
    public static boolean isFileExist(File file) {
        return file != null && file.exists() && file.length() > 0;
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        if (filePath == null) {
            return false;
        }
        File file = new File(filePath);
        return file.exists() && file.length() > 0;
    }

    public static String byte2FitMemorySize(final long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < 1024) {
            return String.format(Locale.getDefault(), "%.2fB", (double) byteNum);
        } else if (byteNum < 1048576) {
            return String.format(Locale.getDefault(), "%.2fKB", (double) byteNum / 1024);
        } else if (byteNum < 1073741824) {
            return String.format(Locale.getDefault(), "%.2fMB", (double) byteNum / 1048576);
        } else {
            return String.format(Locale.getDefault(), "%.2fGB", (double) byteNum / 1073741824);
        }
    }

}
