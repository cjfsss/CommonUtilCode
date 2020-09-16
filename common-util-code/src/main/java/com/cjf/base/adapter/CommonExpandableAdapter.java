package com.cjf.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Packname: com.android.github.lib.weight.listview.adapter
 * @ClassName: CommonExpandableAdapter
 * @Version: 1.0
 * @Author: CaiJunFeng on 2018-7-5 9:23
 * @Description:
 */
public abstract class CommonExpandableAdapter<T, M> extends BaseExpandableListAdapter {

    protected Context mContext;
    protected List<T> mParentList;
    protected List<List<M>> mChildList;
    private int mParentLayout;
    private int mChildLayout;

    protected final List<ViewHolder> mChildContents = new ArrayList<>();
    protected final List<ViewHolder> mParentContents = new ArrayList<>();

    /**
     * 参数过多，使用构造者模式？
     */
    public CommonExpandableAdapter(Context mContext, List<T> mParentList, List<List<M>> mChildList, int parentLayout,
                                   int childLayout) {
        this.mContext = mContext;
        this.mParentList = mParentList;
        this.mChildList = mChildList;
        this.mParentLayout = parentLayout;
        this.mChildLayout = childLayout;
    }

    @Override
    public int getGroupCount() {
        return mParentList == null ? 0 : mParentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (mChildList == null || mChildList.size() <= groupPosition || mChildList.get(groupPosition) == null) ? 0
                : mChildList.get(groupPosition).size();
    }

    @Override
    public T getGroup(int groupPosition) {
        return mParentList == null ? null : mParentList.get(groupPosition);
    }

    @Override
    public M getChild(int groupPosition, int childPosition) {
        return (mChildList == null || mChildList.size() <= groupPosition || mChildList.get(groupPosition) == null)
                ? null : mChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, mParentLayout, groupPosition);
        if (convertView == null) {
            mParentContents.add(holder);
        }
        convertParent(holder, getGroup(groupPosition), groupPosition, isExpanded);
        return holder.getContentView();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, mChildLayout,groupPosition, childPosition);
        if (convertView == null) {
            mChildContents.add(holder);
        }
        convertChild(holder, getChild(groupPosition, childPosition), groupPosition, childPosition, isLastChild);
        return holder.getContentView();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // 子列表是否可以点击
        return true;
    }

    /**
     * setting up a new instance to data;
     *
     * @param data
     */
    public void setNewData(@Nullable List<T> data, @Nullable List<List<M>> childList) {
        this.mParentList = data == null ? new ArrayList<T>() : data;
        this.mChildList = childList == null ? new ArrayList<List<M>>() : childList;
        notifyDataSetChanged();
    }

    /**
     * add one new data
     */
    public void addData(@NonNull T data, @Nullable List<M> childList) {
        this.mParentList.add(data);
        this.mChildList.add(childList);
        notifyDataSetChanged();
    }

    /**
     * remove the item associated with the specified position of adapter
     *
     * @param position
     */
    public void remove(@IntRange(from = 0) int position) {
        this.mParentList.remove(position);
        this.mChildList.remove(position);
        notifyDataSetChanged();
    }

    /**
     * Get the data of list
     *
     * @return 列表数据
     */
    @NonNull
    public List<T> getParentList() {
        return mParentList;
    }

    /**
     * Get the data of list
     *
     * @return 列表数据
     */
    @NonNull
    public List<List<M>> getChildList() {
        return mChildList;
    }

    public abstract void convertChild(ViewHolder holder, M child, int groupPosition, int childPosition,
                                      boolean isLastChild);

    public abstract void convertParent(ViewHolder holder, T group, int groupPosition, boolean isExpanded);

}
