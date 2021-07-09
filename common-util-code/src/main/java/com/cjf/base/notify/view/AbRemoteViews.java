package com.cjf.base.notify.view;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.cjf.base.app.AppCompatApplication;
import com.cjf.base.notify.BroadcastConstant;

/**
 * <p>Title </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/12 11:17
 */
public class AbRemoteViews extends RemoteViews implements BroadcastConstant {
    public AbRemoteViews(String packageName, int layoutId) {
        super(packageName, layoutId);
    }

    private AppCompatApplication getApplication() {
        return AppCompatApplication.getAppCompatApplication();
    }

    public AbRemoteViews setOnClickPendingIntent2(int notifyId, @IdRes int viewId) {
        Intent intent =
                new Intent(ACTION_NOTIFY_CLICK).setPackage(getApplication().getPackageName());
        intent.putExtra(ACTION_NOTIFY_CLICK_VIEW_ID, viewId);
        intent.putExtra(ACTION_NOTIFY_CLICK_NOTIFY_ID, notifyId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplication(),
                viewId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        super.setOnClickPendingIntent(viewId, pendingIntent);
        return this;
    }

    public AbRemoteViews setOnClickPendingIntent2(int notifyId, @IdRes int viewId, int flags) {
        Intent intent =
                new Intent(ACTION_NOTIFY_CLICK).setPackage(getApplication().getPackageName());
        intent.putExtra(ACTION_NOTIFY_CLICK_VIEW_ID, viewId);
        intent.putExtra(ACTION_NOTIFY_CLICK_NOTIFY_ID, notifyId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplication(),
                viewId,
                intent,
                flags
        );
        super.setOnClickPendingIntent(viewId, pendingIntent);
        return this;
    }


    public AbRemoteViews addView2(@IdRes int viewId, RemoteViews nestedView) {
        super.addView(viewId, nestedView);
        return this;
    }


    public AbRemoteViews setTextViewTextSize2(@IdRes int viewId, int units, float size) {
        super.setTextViewTextSize(viewId, units, size);
        return this;
    }

    public AbRemoteViews removeAllViews2(@IdRes int viewId) {
        super.removeAllViews(viewId);
        return this;
    }


    public AbRemoteViews setPendingIntentTemplate2(
            @IdRes int viewId,
            @Nullable PendingIntent pendingIntentTemplate
    ) {
        super.setPendingIntentTemplate(viewId, pendingIntentTemplate);
        return this;
    }


    public AbRemoteViews setEmptyView2(@IdRes int viewId, int emptyViewId) {
        super.setEmptyView(viewId, emptyViewId);
        return this;
    }


    public AbRemoteViews writeToParcel2(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        return this;
    }


    public AbRemoteViews setLong2(@IdRes int viewId, @Nullable String methodName, long value) {
        super.setLong(viewId, methodName, value);
        return this;
    }


    public AbRemoteViews setCharSequence2(
            @IdRes int viewId,
            @Nullable String methodName,
            @Nullable CharSequence value
    ) {
        super.setCharSequence(viewId, methodName, value);
        return this;
    }

    public AbRemoteViews setViewVisibility2(@IdRes int viewId, int visibility) {
        super.setViewVisibility(viewId, visibility);
        return this;
    }

    public AbRemoteViews setTextColor2(@IdRes int viewId, int color) {
        super.setTextColor(viewId, color);
        return this;
    }

    public AbRemoteViews setFloat2(@IdRes int viewId, @Nullable String methodName, float value) {
        super.setFloat(viewId, methodName, value);
        return this;
    }

    @RequiresApi(Build.VERSION_CODES.N)
    public AbRemoteViews setChronometerCountDown2(@IdRes int viewId, boolean isCountDown) {
        super.setChronometerCountDown(viewId, isCountDown);
        return this;
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public AbRemoteViews setLabelFor2(@IdRes int viewId, int labeledId) {
        super.setLabelFor(viewId, labeledId);
        return this;
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public AbRemoteViews setAccessibilityTraversalAfter2(@IdRes int viewId, int nextId) {
        super.setAccessibilityTraversalAfter(viewId, nextId);
        return this;
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    public AbRemoteViews setOnClickResponse2(@IdRes int viewId, RemoteResponse response) {
        super.setOnClickResponse(viewId, response);
        return this;
    }

    public View apply2(@Nullable Context context, @Nullable ViewGroup parent) {
        return super.apply(context, parent);
    }

    public boolean onLoadClass2(@Nullable Class<?> clazz) {
        return super.onLoadClass(clazz);
    }

    public AbRemoteViews setInt2(@IdRes int viewId, @Nullable String methodName, int value) {
        super.setInt(viewId, methodName, value);
        return this;
    }

    public AbRemoteViews setBundle2(@IdRes int viewId, @Nullable String methodName, Bundle value) {
        super.setBundle(viewId, methodName, value);
        return this;
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    public AbRemoteViews setLightBackgroundLayoutId2(@LayoutRes int layoutId) {
        super.setLightBackgroundLayoutId(layoutId);
        return this;
    }

    public AbRemoteViews setDouble2(@IdRes int viewId, @Nullable String methodName, double value) {
        super.setDouble(viewId, methodName, value);
        return this;
    }

    public int describeContents2() {
        return super.describeContents();
    }

    public AbRemoteViews setOnClickFillInIntent2(@IdRes int viewId, Intent fillInIntent) {
        super.setOnClickFillInIntent(viewId, fillInIntent);
        return this;
    }

    public AbRemoteViews setDisplayedChild2(@IdRes int viewId, int childIndex) {
        super.setDisplayedChild(viewId, childIndex);
        return this;
    }

    public AbRemoteViews showPrevious2(@IdRes int viewId) {
        super.showPrevious(viewId);
        return this;
    }

    public AbRemoteViews reapply2(@NonNull Context context, @NonNull View v) {
        super.reapply(context, v);
        return this;
    }

    public AbRemoteViews setUri2(@IdRes int viewId, @Nullable String methodName, @Nullable Uri value) {
        super.setUri(viewId, methodName, value);
        return this;
    }

    public AbRemoteViews setIcon2(@IdRes int viewId, @Nullable String methodName, @Nullable Icon value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            super.setIcon(viewId, methodName, value);
        }
        return this;
    }

    public AbRemoteViews setImageViewResource2(@IdRes int viewId, int srcId) {
        super.setImageViewResource(viewId, srcId);
        return this;
    }

    public AbRemoteViews setRelativeScrollPosition2(@IdRes int viewId, int offset) {
        super.setRelativeScrollPosition(viewId, offset);
        return this;
    }

    public AbRemoteViews setBitmap2(@IdRes int viewId, @Nullable String methodName, @Nullable Bitmap value) {
        super.setBitmap(viewId, methodName, value);
        return this;
    }

    public AbRemoteViews setIntent2(@IdRes int viewId, @Nullable String methodName, Intent value) {
        super.setIntent(viewId, methodName, value);
        return this;
    }

    public AbRemoteViews setString2(@IdRes int viewId, @Nullable String methodName, @Nullable String value) {
        super.setString(viewId, methodName, value);
        return this;
    }

    public AbRemoteViews setImageViewBitmap2(@IdRes int viewId, @Nullable Bitmap bitmap) {
        super.setImageViewBitmap(viewId, bitmap);
        return this;
    }

    public AbRemoteViews setTextViewCompoundDrawables2(
            @IdRes int viewId,
            int left,
            int top,
            int right,
            int bottom
    ) {
        super.setTextViewCompoundDrawables(viewId, left, top, right, bottom);
        return this;
    }

    public AbRemoteViews setRemoteAdapter2(@IdRes int viewId, Intent intent) {
        super.setRemoteAdapter(viewId, intent);
        return this;
    }

    public AbRemoteViews setAccessibilityTraversalBefore2(@IdRes int viewId, int nextId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            super.setAccessibilityTraversalBefore(viewId, nextId);
        }
        return this;
    }

    public AbRemoteViews setProgressBar2(
            @IdRes int viewId,
            int max,
            int progress,
            boolean indeterminate
    ) {
        super.setProgressBar(viewId, max, progress, indeterminate);
        return this;
    }

    public AbRemoteViews setImageViewIcon2(@IdRes int viewId, @Nullable Icon icon) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            super.setImageViewIcon(viewId, icon);
        }
        return this;
    }

    public AbRemoteViews showNext2(@IdRes int viewId) {
        super.showNext(viewId);
        return this;

    }

    public AbRemoteViews setChar2(@IdRes int viewId, @Nullable String methodName, char value) {
        super.setChar(viewId, methodName, value);
        return this;
    }

    public AbRemoteViews setContentDescription2(@IdRes int viewId, @Nullable CharSequence contentDescription) {
        super.setContentDescription(viewId, contentDescription);
        return this;
    }

    public String getPackage2() {
        return super.getPackage();
    }

    public AbRemoteViews setChronometer2(
            @IdRes int viewId,
            long base,
            @Nullable String format,
            boolean started
    ) {
        super.setChronometer(viewId, base, format, started);
        return this;
    }

    public AbRemoteViews setTextViewText2(@IdRes int viewId, @Nullable CharSequence text) {
        super.setTextViewText(viewId, text);
        return this;
    }

    public AbRemoteViews setShort2(@IdRes int viewId, @Nullable String methodName, short value) {
        super.setShort(viewId, methodName, value);
        return this;
    }

    public AbRemoteViews setViewPadding2(
            @IdRes int viewId,
            int left,
            int top,
            int right,
            int bottom
    ) {
        super.setViewPadding(viewId, left, top, right, bottom);
        return this;
    }

    public AbRemoteViews setTextViewCompoundDrawablesRelative2(
            @IdRes int viewId,
            int start,
            int top,
            int end,
            int bottom
    ) {
        super.setTextViewCompoundDrawablesRelative(viewId, start, top, end, bottom);
        return this;
    }

    public AbRemoteViews setBoolean2(@IdRes int viewId, @Nullable String methodName, boolean value) {
        super.setBoolean(viewId, methodName, value);
        return this;
    }

    public AbRemoteViews setImageViewUri2(@IdRes int viewId, @Nullable Uri uri) {
        super.setImageViewUri(viewId, uri);
        return this;
    }

    public AbRemoteViews setScrollPosition2(@IdRes int viewId, int position) {
        super.setScrollPosition(viewId, position);
        return this;
    }

    public AbRemoteViews setByte2(@IdRes int viewId, @Nullable String methodName, Byte value) {
        super.setByte(viewId, methodName, value);
        return this;
    }
}
