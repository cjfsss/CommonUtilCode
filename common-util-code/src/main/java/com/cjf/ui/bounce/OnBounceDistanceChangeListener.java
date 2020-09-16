package com.cjf.ui.bounce;

/**
 * <p>Title: OnBounceDistanceChangeListener </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/4/9 17:46
 */
public interface OnBounceDistanceChangeListener {
    int DIRECTION_LEFT = 1;
    int DIRECTION_RIGHT = 2;
    int DIRECTION_UP = 3;
    int DIRECTION_DOWN = 4;

    void onDistanceChange(int distance, int direction);

    void onFingerUp(int distance, int direction);
}
