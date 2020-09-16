package com.cjf.ui.bounce;

/**
 * <p>Title: BounceAction </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/4/9 17:44
 */
public interface Bounce {

    void disableBounce();

    void openBothHorizontal();

    void openBothVertical();

    void openTop();

    void openBottom();

    void openLeft();

    void openRight();

}
