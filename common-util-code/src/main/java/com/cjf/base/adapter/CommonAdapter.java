package com.cjf.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected final Context mContext;
    protected List<T> mDataList;
    protected int mLayoutResId;

    public CommonAdapter(Context context, final int layoutId, List<T> data) {
        mLayoutResId = layoutId;
        this.mContext = context;
        this.mDataList = data;
    }

    public CommonAdapter(Context context,  List<T> data) {
        this.mContext = context;
        this.mDataList = data;
    }

    public @NonNull
    Context getContext() {
        return mContext;
    }

    /**
     * setting up a new instance to data;
     *
     * @param data
     */
    public void setNewData(@Nullable List<T> data) {
        this.mDataList = data == null ? new ArrayList<T>() : data;
        notifyDataSetChanged();
    }


    /**
     * add one new data in to certain location
     *
     * @param position
     */
    public void addData(@IntRange(from = 0) int position, @NonNull T data) {
        this.mDataList.add(position, data);
        notifyDataSetChanged();
    }

    /**
     * add one new data
     */
    public void addData(@NonNull T data) {
        this.mDataList.add(data);
        notifyDataSetChanged();
    }

    /**
     * remove the item associated with the specified position of adapter
     *
     * @param position
     */
    public void remove(@IntRange(from = 0) int position) {
        this.mDataList.remove(position);
        notifyDataSetChanged();
    }

    /**
     * change data
     */
    public void setData(@IntRange(from = 0) int index, @NonNull T data) {
        this.mDataList.set(index, data);
        notifyDataSetChanged();
    }

    /**
     * add new data in to certain location
     *
     * @param position the insert position
     * @param newData  the new data collection
     */
    public void addData(@IntRange(from = 0) int position, @NonNull Collection<? extends T> newData) {
        this.mDataList.addAll(position, newData);
        notifyDataSetChanged();
    }

    /**
     * add new data to the end of mDataList
     *
     * @param newData the new data collection
     */
    public void addData(@NonNull Collection<? extends T> newData) {
        this.mDataList.addAll(newData);
        notifyDataSetChanged();
    }

    /**
     * Get the data of list
     *
     * @return 列表数据
     */
    @NonNull
    public List<T> getDataList() {
        return mDataList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Nullable
    public T getItem(@IntRange(from = 0) int position) {
        if (mDataList == null || position >= mDataList.size()) {
            return null;
        }
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 不嵌套ListView的时候
     */
    @Deprecated
    public void initListViewParams(ListView listView) {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        listView.setLayoutParams(params);
    }

    /**
     * scroolView嵌套ListView时使用
     *
     * @param listView
     */
    @Deprecated
    public void setListViewHeightBasedOnChildren(AbsListView listView) {
        if (listView instanceof ListView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();

            params.height = totalHeight
                    + (((ListView) listView).getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            if (getView() == null) {
                viewHolder = ViewHolder.get(mContext, parent, mLayoutResId, position);
            } else {
                viewHolder = ViewHolder.get(mContext, parent, getView(), position);
            }
            onViewHolderCreated(viewHolder, viewHolder.getContentView());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }
        convert(viewHolder, getItem(position), position);
        return viewHolder.getContentView();
    }

    protected View getView() {
        return null;
    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);

    protected void onViewHolderCreated(ViewHolder holder, View itemView) {
    }

}
