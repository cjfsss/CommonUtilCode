package com.cjf.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

/**
 * <p>Title: Msg </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/7/31 8:58
 */
public class Msg implements Serializable {

    private Object[] array;

    private int what;

    private String type;

    public Msg(int what, @Nullable Object... array) {
        this.array = array;
        this.what = what;
    }

    public Msg(@NonNull String type, @Nullable Object... array) {
        this.array = array;
        this.type = type;
    }

    public Msg(@Nullable Object... array) {
        this.array = array;
    }

    @Nullable
    public <T> T get() {
        return (T) array[0];
    }

    @Nullable
    public Object getObj() {
        return array[0];
    }

    @Nullable
    public Object[] getArray() {
        return array;
    }

    public void setArray(@NonNull Object[] array) {
        this.array = array;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    @Nullable
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }
}
