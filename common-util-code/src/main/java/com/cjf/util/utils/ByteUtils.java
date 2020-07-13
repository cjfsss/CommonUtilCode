package com.cjf.util.utils;

import android.os.Build;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * <p>Title: ByteUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/5/17 12:13
 */
public class ByteUtils {
    private final static String UTF_8 = "UTF-8";

    @NonNull
    public static String toString(@NonNull byte[] bytes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new String(bytes, StandardCharsets.UTF_8);
        }
        try {
            return new String(bytes, UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new String(bytes);
    }

    @NonNull
    public static byte[] getBytes(@NonNull String target) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return target.getBytes(StandardCharsets.UTF_8);
        }
        try {
            return target.getBytes(UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target.getBytes();
    }

    @NonNull
    public static byte[] toBytes(@NonNull Object object) {
        ByteArrayOutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        byte[] bytes = null;
        try {
            outputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
            bytes = outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

}
