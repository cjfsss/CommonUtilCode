package com.cjf.util.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @Packname: com.mapuni.android.core.weight.textview
 * @ClassName: SimpleAfterTextWatcher
 * @Version: 1.0
 * @Author: CaiJunFeng on 2018-8-7 10:01
 * @Description:
 */
public interface SimpleTextChangeWatcher extends TextWatcher {
    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    default void afterTextChanged(Editable s){

    }

}
