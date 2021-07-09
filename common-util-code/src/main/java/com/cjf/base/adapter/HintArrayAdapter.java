package com.cjf.base.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.cjf.util.R;

import java.util.List;

/**
 * <p>Title: FirstArrayAdapter </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/3/24 20:46
 */
public class HintArrayAdapter extends ArrayAdapter<String> {

    private final String mFirstHint;

    public HintArrayAdapter(@NonNull Context context, @NonNull List<String> dataList, String firstHint) {
        super(context, R.layout.support_simple_spinner_dropdown_item, dataList);
        setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mFirstHint = firstHint;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View textView = super.getView(position, convertView, parent);
        if (position == 0) {
            if (textView instanceof TextView && !TextUtils.isEmpty(mFirstHint)) {
                ((TextView) textView).setText(mFirstHint);
                return textView;
            }
        }
        return textView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View dropDownView = super.getDropDownView(position, convertView, parent);
        if (position == 0) {
            if (dropDownView instanceof TextView && !TextUtils.isEmpty(mFirstHint)) {
                ((TextView) dropDownView).setText("全部");
                return dropDownView;
            }
        }
        return dropDownView;
    }
}
