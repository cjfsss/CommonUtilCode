package com.cjf.util.listener;

/**
 * <p>Title: OnProgressUpdateListener </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/4/22 14:16
 */
public interface OnProgressUpdateListener {
    void onProgressUpdate(double progress, int writeSize, double totalSize);
}
