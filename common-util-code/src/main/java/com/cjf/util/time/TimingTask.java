package com.cjf.util.time;

import androidx.annotation.NonNull;


import com.cjf.util.log.LogX;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>Title: TimingTask </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2019/12/4 20:03
 */
public class TimingTask {

    private static final String TAG = "TimingTask";

    private Timer timer;
    private final long time;
    private final long delayTime;

    private TimerTask timerTask;
    // 开始时间
    private long mStartTime;
    // 当时执行的时间
    private long mUseTime;

    public TimingTask(long time) {
        this(time, 0);
    }

    public TimingTask(long time, long delayTime) {
        this.time = time;
        this.delayTime = delayTime;
        if (timer == null) {
            timer = new Timer();
        }
    }

    /**
     * 开启一个任务
     *
     * @param runable 任务
     */
    public void start(@NonNull final OnTimingTaskRunnable runable) {
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                if (mStartTime == 0) {
                    mStartTime = System.currentTimeMillis();
                }
                mUseTime += time;
                LogX.dTag(TAG, "startTime:" + mStartTime,
                        "UseTime:" + mUseTime, "time:" + time, "delayTime:" + delayTime);
                runable.onTimingTaskRunnable(mStartTime, mUseTime, time, delayTime);
            }
        };
        timer.schedule(timerTask, delayTime, time);//每隔time时间段就执行一次
    }

    /**
     * 停止任务
     */
    public void stop() {
        System.gc();
        if (timer != null) {
            timer.cancel();
            if (timerTask != null) {
                timerTask.cancel();  //将原任务从队列中移除
                timerTask = null;
            }
            timer = null;
        }
    }

}
