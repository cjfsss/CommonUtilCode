package com.cjf.base.simple;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cjf.util.R;
import com.cjf.util.utils.AppCompat;
import com.cjf.util.utils.ResUtils;
import com.cjf.util.utils.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * <p>Title: Simple </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/30 11:40
 */
public class Simple<DATA> implements Serializable {

    public int code;
    @NonNull
    public String message;
    @Nullable
    public DATA data;

    private final int pageSize;

    private boolean isUpdate;

    public Simple(int code, @NonNull String message) {
        this(code, message, null, AppCompat.getApp().getPageSize());
    }

    public Simple(int code, @NonNull String message, boolean isUpdate) {
        this(code, message, null, AppCompat.getApp().getPageSize(), isUpdate);
    }

    public Simple(int code, @NonNull String message, @Nullable DATA data) {
        this(code, message, data, AppCompat.getApp().getPageSize());
    }

    public Simple(int code, @NonNull String message, @Nullable DATA data, boolean isUpdate) {
        this(code, message, data, AppCompat.getApp().getPageSize(), isUpdate);
    }

    public Simple(int code, @NonNull String message, @Nullable DATA data, int pageSize) {
        this(code, message, data, pageSize, true);
    }

    public Simple(int code, @NonNull String message, @Nullable DATA data, int pageSize, boolean isUpdate) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.pageSize = pageSize;
        this.isUpdate = isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public long count() {
        if (data == null) {
            return 0;
        }
        if (data instanceof List) {
            //noinspection rawtypes
            return ((List) data).size();
        }
        return 0;
    }

    public int pageSize() {
        return pageSize;
    }

    public boolean hasMore() {
        return count() >= pageSize();
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public boolean isEmpty() {
        if (data == null) {
            return true;
        }
        if (data instanceof String) {
            return StringUtils.toNULL((String) data) == null;
        }
        if (data instanceof List) {
            //noinspection rawtypes
            return ((List) data).isEmpty();
        }
        return code == 300;
    }

    /**
     * 成功
     */
    public boolean isSuccessFul() {
        return code == 200;
    }

    /**
     * 网络异常
     */
    public boolean isNetworkError() {
        return code == 100;
    }

    /**
     * 服务端异常
     */
    public boolean isError() {
        return code == 404;
    }

    @Override
    public String toString() {
        return "Simple{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", pageSize=" + pageSize +
                ", isUpdate=" + isUpdate +
                '}';
    }

    public static <DATA> Simple<DATA> success(@NonNull String message, @NonNull DATA data) {
        return new Simple<>(200, message, data);
    }

    public static <DATA> Simple<DATA> success(@NonNull String message, @NonNull DATA data, boolean isUpdate) {
        return new Simple<>(200, message, data, isUpdate);
    }

    public static <DATA> Simple<DATA> success(@NonNull String message, @NonNull DATA data, int pageSize) {
        return new Simple<>(200, message, data, pageSize);
    }

    public static <DATA> Simple<DATA> success(@NonNull String message, @NonNull DATA data, int pageSize, boolean isUpdate) {
        return new Simple<>(200, message, data, pageSize, isUpdate);
    }

    public static <DATA> Simple<DATA> empty(@NonNull String message) {
        return new Simple<>(300, message);
    }

    public static <DATA> Simple<DATA> empty(@NonNull String message, boolean isUpdate) {
        return new Simple<>(300, message, isUpdate);
    }

    public static <DATA> Simple<DATA> error(@NonNull String message) {
        return new Simple<>(404, message);
    }

    public static <DATA> Simple<DATA> error(@NonNull String message, boolean isUpdate) {
        return new Simple<>(404, message, isUpdate);
    }

    public static <DATA> Simple<DATA> error() {
        return new Simple<>(404, ResUtils.getString(R.string.http_server_error));
    }

    public static <DATA> Simple<DATA> error(boolean isUpdate) {
        return new Simple<>(404, ResUtils.getString(R.string.http_server_error), isUpdate);
    }

    public static <DATA> Simple<DATA> error(Throwable throwable) {
        String message = throwable.getMessage();
        if (TextUtils.isEmpty(message)) {
            return Simple.error();
        }
        return new Simple<>(404, Objects.requireNonNull(message));
    }

    public static <DATA> Simple<DATA> error(Throwable throwable, boolean isUpdate) {
        String message = throwable.getMessage();
        if (TextUtils.isEmpty(message)) {
            return Simple.error();
        }
        return new Simple<>(404, Objects.requireNonNull(message),isUpdate);
    }

    public static <DATA> Simple<DATA> net(Throwable throwable) {
        String message = throwable.getMessage();
        if (TextUtils.isEmpty(message)) {
            return Simple.net();
        }
        return new Simple<>(100, Objects.requireNonNull(message));
    }

    public static <DATA> Simple<DATA> net(Throwable throwable, boolean isUpdate) {
        String message = throwable.getMessage();
        if (TextUtils.isEmpty(message)) {
            return Simple.net();
        }
        return new Simple<>(100, Objects.requireNonNull(message),isUpdate);
    }

    public static <DATA> Simple<DATA> net() {
        return new Simple<>(100, ResUtils.getString(R.string.http_network_error));
    }

    public static <DATA> Simple<DATA> net(boolean isUpdate) {
        return new Simple<>(100, ResUtils.getString(R.string.http_network_error), isUpdate);
    }
}
