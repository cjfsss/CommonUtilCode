package com.cjf.util.listener;

import android.widget.AdapterView;

/**
 * <p>Title: SimpleOnItemSelectedListener </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/1 22:34
 */
public interface SimpleOnItemSelectedListener extends AdapterView.OnItemSelectedListener {

    @Override
    default void onNothingSelected(AdapterView<?> parent){

    }
}
