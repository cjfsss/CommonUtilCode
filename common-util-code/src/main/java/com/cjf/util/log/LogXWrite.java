package com.cjf.util.log;

import androidx.annotation.NonNull;


import com.cjf.util.path.PathManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <p>Title: PrintLogWrite </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/2/26 12:06
 */
public class LogXWrite {


    synchronized static void writeToLocal(@NonNull Throwable e, @NonNull String ClassName, final Object... objs) {
        try {
            @NonNull String logPath = PathManager.getLogDir();
            LogX.eTag(LogX.TAG, "logPath:" + logPath);
            File path = new File(logPath);
            createOrExistsDir(path);
            String nowTime = getNowString(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()));
            File file = new File(logPath + "/log_" + nowTime + ".log");
            FileWriter fw = new FileWriter(file, true);
            if (!isFileExists(file)) {
                createOrExistsFile(file);
            }
            fw.write(getNowString() + "     ClassName:" + ClassName + "\n");
            if (objs != null) {
                for (Object obj : objs) {
                    fw.write(obj + "");
                }
                fw.write("\n");
            }
            fw.write(e.getClass().getName() + "\n");
            for (StackTraceElement ste : e.getStackTrace()) {
                String line = ste.toString();
                fw.write("at ");
                fw.write(line);
                fw.write("\n");
            }
            fw.write("\n");
            fw.flush();
            fw.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e.printStackTrace();
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private static boolean createOrExistsFile(final File file) {
        if (file == null) {
            return false;
        }
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private static boolean createOrExistsDir(final File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 获取当前时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 时间字符串
     */
    private static String getNowString() {
        return millis2String(System.currentTimeMillis(),
                             new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()));
    }

    /**
     * 获取当前时间字符串
     * <p>格式为format</p>
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    private static String getNowString(final DateFormat format) {
        return millis2String(System.currentTimeMillis(), format);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为format</p>
     *
     * @param millis 毫秒时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    private static String millis2String(final long millis, final DateFormat format) {
        return format.format(new Date(millis));
    }

}
