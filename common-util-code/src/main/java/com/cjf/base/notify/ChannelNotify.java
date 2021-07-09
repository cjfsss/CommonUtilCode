package com.cjf.base.notify;

/**
 * <p>Title: ChannelNotify </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/12 15:19
 */
public abstract class ChannelNotify<T extends BaseNotificationData>  extends BaseNotification<T> {

    public ChannelNotify(T data) {
        super(data);
    }

    @Override
    String getChannelName() {
        return "notify";
    }

    @Override
    String getChannelId() {
        return "notify";
    }
}
