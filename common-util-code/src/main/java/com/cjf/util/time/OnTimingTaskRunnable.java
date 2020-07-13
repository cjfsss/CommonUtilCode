package com.cjf.util.time;

/**
 * 时间监听
 * <p>Title: OnTimingTaskRunnable </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2019/12/11 9:33
 */
public interface OnTimingTaskRunnable {

    void onTimingTaskRunnable(long starTime, long useTime, long time, long delayTime);
}
