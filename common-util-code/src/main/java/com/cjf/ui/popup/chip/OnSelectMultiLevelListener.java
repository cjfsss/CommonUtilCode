package com.cjf.ui.popup.chip;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * <p>Title: OnSelectMultLevelListener </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/9/28 22:05
 */
public interface OnSelectMultiLevelListener {

    void onSelect(@NonNull List<String> selectList, @NonNull List<Integer> selectPositionList, int level);
}
