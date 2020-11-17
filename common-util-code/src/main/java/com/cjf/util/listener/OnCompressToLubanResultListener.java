package com.cjf.util.listener;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * <p>Title: OnCompressToLubanResultListener </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/16 8:56
 */
public interface OnCompressToLubanResultListener {

    void onResult(List<LocalMedia> result);
}
