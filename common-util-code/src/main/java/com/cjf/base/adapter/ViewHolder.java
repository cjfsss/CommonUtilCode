package com.cjf.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


/**
 * <p>Title: ViewHolder </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/4/11 15:15
 */
public class ViewHolder {

    protected int mPosition;
    protected int mChildPosition;
    protected int mLayoutId;

    private ViewDelegate mViewDelegate;

    private ViewHolder(Context context, int layoutId, ViewGroup parent, int position, int childPosition) {
        mPosition = position;
        mChildPosition = childPosition;
        ViewDelegate delegate = getViewHelper();
        View contentView = delegate.inflate(context, layoutId, parent);
        contentView.setTag(this);
    }

    private ViewHolder(Context context, View view, ViewGroup parent, int position, int childPosition) {
        mPosition = position;
        mChildPosition = childPosition;
        ViewDelegate delegate = getViewHelper();
        View contentView = delegate.inflate(context, view, parent);
        contentView.setTag(this);
    }

    public static ViewHolder get(Context context, ViewGroup parent, int layoutId, int position) {
        ViewHolder holder = new ViewHolder(context, layoutId, parent, position, position);
        holder.mLayoutId = layoutId;
        return holder;
    }

    public static ViewHolder get(Context context, ViewGroup parent, View view, int position) {
        return new ViewHolder(context, view, parent, position, position);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            ViewHolder holder = new ViewHolder(context, layoutId, parent, position, position);
            holder.mLayoutId = layoutId;
            return holder;
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            holder.mChildPosition = position;
            return holder;
        }
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position, int childPosition) {
        if (convertView == null) {
            ViewHolder holder = new ViewHolder(context, layoutId, parent, position, childPosition);
            holder.mLayoutId = layoutId;
            return holder;
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            holder.mChildPosition = childPosition;
            return holder;
        }
    }

    public void setViewHelper(ViewDelegate mViewDelegate) {
        this.mViewDelegate = mViewDelegate;
    }

    public ViewDelegate getViewHelper() {
        if (mViewDelegate == null) {
            return mViewDelegate = ViewDelegate.create();
        }
        return mViewDelegate;
    }

    public <T extends View> T getView(int viewId) {
        return getViewHelper().getView(viewId);
    }

    public View getContentView() {
        return getViewHelper().getContentView();
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public void updatePosition(int position) {
        mPosition = position;
    }

    public int getChildPosition() {
        return mChildPosition;
    }

    public int getItemPosition() {
        return mPosition;
    }


}
