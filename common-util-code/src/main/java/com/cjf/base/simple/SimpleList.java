package com.cjf.base.simple;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cjf.util.R;
import com.cjf.util.utils.ResUtils;

import java.util.List;

/**
 * <p>Title: SimpleList </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/30 12:04
 */
public class SimpleList<ITEM> extends Simple<List<ITEM>> {

    public SimpleList(int code, @NonNull String message) {
        super(code, message);
    }

    public SimpleList(int code, @NonNull String message, boolean isUpdate) {
        super(code, message, isUpdate);
    }

    public SimpleList(int code, @NonNull String message, @Nullable List<ITEM> items) {
        super(code, message, items);
    }

    public SimpleList(int code, @NonNull String message, @Nullable List<ITEM> items, boolean isUpdate) {
        super(code, message, items, isUpdate);
    }

    public SimpleList(int code, @NonNull String message, @Nullable List<ITEM> items, int pageSize) {
        super(code, message, items, pageSize);
    }

    public SimpleList(int code, @NonNull String message, @Nullable List<ITEM> items, int pageSize, boolean isUpdate) {
        super(code, message, items, pageSize, isUpdate);
    }

    public long count() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public static <ITEM> SimpleList<ITEM> successList(@NonNull String message, @NonNull List<ITEM> data) {
        return new SimpleList<>(200, message, data);
    }

    public static <ITEM> SimpleList<ITEM> successList(@NonNull String message, @NonNull List<ITEM> data, boolean isUpdate) {
        return new SimpleList<>(200, message, data, isUpdate);
    }

    public static <ITEM> SimpleList<ITEM> successList(@NonNull String message, @NonNull List<ITEM> data, int pageSize) {
        return new SimpleList<>(200, message, data, pageSize);
    }

    public static <ITEM> SimpleList<ITEM> successList(@NonNull String message, @NonNull List<ITEM> data, int pageSize, boolean isUpdate) {
        return new SimpleList<>(200, message, data, pageSize, isUpdate);
    }

    public static <ITEM> SimpleList<ITEM> emptyList(@NonNull String message) {
        return new SimpleList<>(300, message);
    }

    public static <ITEM> SimpleList<ITEM> emptyList(@NonNull String message, boolean isUpdate) {
        return new SimpleList<>(300, message, isUpdate);
    }

    public static <ITEM> SimpleList<ITEM> errorList(@NonNull String message) {
        return new SimpleList<>(404, message);
    }

    public static <ITEM> SimpleList<ITEM> errorList(@NonNull String message, boolean isUpdate) {
        return new SimpleList<>(404, message, isUpdate);
    }

    public static <ITEM> SimpleList<ITEM> errorList() {
        return new SimpleList<>(404, ResUtils.getString(R.string.http_server_error));
    }

    public static <ITEM> SimpleList<ITEM> errorList(boolean isUpdate) {
        return new SimpleList<>(404, ResUtils.getString(R.string.http_server_error), isUpdate);
    }

    public static <ITEM> SimpleList<ITEM> errorList(Throwable throwable) {
        return new SimpleList<>(404, throwable.getMessage());
    }

    public static <ITEM> SimpleList<ITEM> errorList(Throwable throwable, boolean isUpdate) {
        return new SimpleList<>(404, throwable.getMessage(), isUpdate);
    }

    public static <ITEM> SimpleList<ITEM> netList(Throwable throwable) {
        return new SimpleList<>(100, throwable.getMessage());
    }

    public static <ITEM> SimpleList<ITEM> netList(Throwable throwable, boolean isUpdate) {
        return new SimpleList<>(100, throwable.getMessage(), isUpdate);
    }

    public static <ITEM> SimpleList<ITEM> netList() {
        return new SimpleList<>(100, ResUtils.getString(R.string.http_network_error));
    }

    public static <ITEM> SimpleList<ITEM> netList(boolean isUpdate) {
        return new SimpleList<>(100, ResUtils.getString(R.string.http_network_error), isUpdate);
    }
}
