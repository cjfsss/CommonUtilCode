package com.cjf.util.dialog.progress;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.cjf.util.R;

import java.text.NumberFormat;

/**
 * <p>Title: ProgressDialog </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/7/13 10:42
 */
public class ProgressDialogCompat extends AlertDialog implements LifecycleObserver {
    /**
     * Creates a ProgressDialog with a circular, spinning progress
     * bar. This is the default.
     */
    public static final int STYLE_SPINNER = 0;

    /**
     * Creates a ProgressDialog with a horizontal progress bar.
     */
    public static final int STYLE_HORIZONTAL = 1;

    private ContentLoadingProgressBar mProgress;
    private TextView mMessageView;
    private ImageView mCloseView;
    private int mProgressStyle = STYLE_SPINNER;
    private TextView mProgressNumber;
    private String mProgressNumberFormat;
    private TextView mProgressPercent;
    private NumberFormat mProgressPercentFormat;

    private int mMax;
    private int mProgressVal;
    private int mSecondaryProgressVal;
    private int mIncrementBy;
    private int mIncrementSecondaryBy;
    private Drawable mProgressDrawable;
    private Drawable mIndeterminateDrawable;
    private CharSequence mMessage;
    private boolean mIndeterminate;

    private boolean mHasStarted;
    private Handler mViewUpdateHandler;

    /**
     * Creates a Progress dialog.
     *
     * @param activity the parent context
     */
    public ProgressDialogCompat(Activity activity, LifecycleOwner lifecycleOwner) {
        super(activity);
        if (lifecycleOwner != null) {
            lifecycleOwner.getLifecycle().addObserver(this);
        }
        initFormats();
    }

    /**
     * Creates a Progress dialog.
     *
     * @param activity the parent context
     * @param theme    the resource ID of the theme against which to inflate
     *                 this dialog, or {@code 0} to use the parent
     *                 {@code context}'s default alert dialog theme
     */
    public ProgressDialogCompat(Activity activity, LifecycleOwner lifecycleOwner, int theme) {
        super(activity, theme);
        lifecycleOwner.getLifecycle().addObserver(this);
        initFormats();
    }

    private void initFormats() {
        mProgressNumberFormat = "%1d/%2d";
        mProgressPercentFormat = NumberFormat.getPercentInstance();
        mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    /**
     * Creates and shows a ProgressDialog.
     *
     * @param context the parent context
     * @param message the text to be displayed in the dialog
     * @return the ProgressDialog
     */
    public static ProgressDialogCompat show(Activity context, LifecycleOwner lifecycleOwner, CharSequence message) {
        return show(context, lifecycleOwner, message, false);
    }

    /**
     * Creates and shows a ProgressDialog.
     *
     * @param context       the parent context
     * @param message       the text to be displayed in the dialog
     * @param indeterminate true if the dialog should be {@link #setIndeterminate(boolean)
     *                      indeterminate}, false otherwise
     * @return the ProgressDialog
     */
    public static ProgressDialogCompat show(Activity context, LifecycleOwner lifecycleOwner, CharSequence message,
            boolean indeterminate) {
        return show(context, lifecycleOwner, message, indeterminate, false, null);
    }

    /**
     * Creates and shows a ProgressDialog.
     *
     * @param context       the parent context
     * @param message       the text to be displayed in the dialog
     * @param indeterminate true if the dialog should be {@link #setIndeterminate(boolean)
     *                      indeterminate}, false otherwise
     * @param cancelable    true if the dialog is {@link #setCancelable(boolean) cancelable},
     *                      false otherwise
     * @return the ProgressDialog
     */
    public static ProgressDialogCompat show(Activity context, LifecycleOwner lifecycleOwner, CharSequence message,
            boolean indeterminate, boolean cancelable) {
        return show(context, lifecycleOwner, message, indeterminate, cancelable, null);
    }

    /**
     * Creates and shows a ProgressDialog.
     *
     * @param context        the parent context
     * @param message        the text to be displayed in the dialog
     * @param indeterminate  true if the dialog should be {@link #setIndeterminate(boolean)
     *                       indeterminate}, false otherwise
     * @param cancelable     true if the dialog is {@link #setCancelable(boolean) cancelable},
     *                       false otherwise
     * @param cancelListener the {@link #setOnCancelListener(DialogInterface.OnCancelListener) listener}
     *                       to be invoked when the dialog is canceled
     * @return the ProgressDialog
     */
    public static ProgressDialogCompat show(Activity context, LifecycleOwner lifecycleOwner, CharSequence message,
            boolean indeterminate, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        ProgressDialogCompat dialog = new ProgressDialogCompat(context, lifecycleOwner);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = getContext();
        if (mProgressStyle == STYLE_HORIZONTAL) {

            /* Use a separate handler to update the text views as they
             * must be updated on the same thread that created them.
             */
            mViewUpdateHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    /* Update the number and percent */
                    int progress = mProgress.getProgress();
                    int max = mProgress.getMax();
                    if (mProgressNumberFormat != null) {
                        String format = mProgressNumberFormat;
                        mProgressNumber.setText(String.format(format, progress, max));
                    } else {
                        mProgressNumber.setText("");
                    }
                    if (mProgressPercentFormat != null) {
                        double percent = (double) progress / (double) max;
                        SpannableString tmp = new SpannableString(mProgressPercentFormat.format(percent));
                        tmp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, tmp.length(),
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mProgressPercent.setText(tmp);
                    } else {
                        mProgressPercent.setText("");
                    }
                }
            };

            View view = View.inflate(context, R.layout.progress_dialog_horizontal, null);
            mProgress = (ContentLoadingProgressBar) view.findViewById(R.id.progress);
            mProgressNumber = (TextView) view.findViewById(R.id.progress_number);
            mProgressPercent = (TextView) view.findViewById(R.id.progress_percent);
            setView(view);
        } else {
            View view = View.inflate(context, R.layout.progress_dialog, null);
            mProgress = (ContentLoadingProgressBar) view.findViewById(R.id.progress);
            mMessageView = (TextView) view.findViewById(R.id.message);
            mCloseView = (ImageView) view.findViewById(R.id.close);
            setView(view);
            setCloseShow(false);
        }
        if (mMax > 0) {
            setMax(mMax);
        }
        if (mProgressVal > 0) {
            setProgress(mProgressVal);
        }
        if (mSecondaryProgressVal > 0) {
            setSecondaryProgress(mSecondaryProgressVal);
        }
        if (mIncrementBy > 0) {
            incrementProgressBy(mIncrementBy);
        }
        if (mIncrementSecondaryBy > 0) {
            incrementSecondaryProgressBy(mIncrementSecondaryBy);
        }
        if (mProgressDrawable != null) {
            setProgressDrawable(mProgressDrawable);
        }
        if (mIndeterminateDrawable != null) {
            setIndeterminateDrawable(mIndeterminateDrawable);
        }
        if (mMessage != null) {
            setMessage(mMessage);
        }
        setIndeterminate(mIndeterminate);
        onProgressChanged();
        super.onCreate(savedInstanceState);
    }

    /**
     * 是否显示关闭按钮
     * @param closeShow true 显示关闭按钮
     */
    public void setCloseShow(boolean closeShow) {
        if (closeShow) {
            mCloseView.setVisibility(View.VISIBLE);
            mCloseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isShowing()) {
                        dismiss();
                    }
                }
            });
        } else  {
            mCloseView.setVisibility(View.GONE);
            mCloseView.setOnClickListener(null);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mHasStarted = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHasStarted = false;
    }

    /**
     * Sets the current progress.
     *
     * @param value the current progress, a value between 0 and {@link #getMax()}
     * @see ProgressBar#setProgress(int)
     */
    public void setProgress(int value) {
        if (mHasStarted) {
            mProgress.setProgress(value);
            onProgressChanged();
        } else {
            mProgressVal = value;
        }
    }

    /**
     * Sets the secondary progress.
     *
     * @param secondaryProgress the current secondary progress, a value between 0 and
     *                          {@link #getMax()}
     * @see ProgressBar#setSecondaryProgress(int)
     */
    public void setSecondaryProgress(int secondaryProgress) {
        if (mProgress != null) {
            mProgress.setSecondaryProgress(secondaryProgress);
            onProgressChanged();
        } else {
            mSecondaryProgressVal = secondaryProgress;
        }
    }

    /**
     * Gets the current progress.
     *
     * @return the current progress, a value between 0 and {@link #getMax()}
     */
    public int getProgress() {
        if (mProgress != null) {
            return mProgress.getProgress();
        }
        return mProgressVal;
    }

    /**
     * Gets the current secondary progress.
     *
     * @return the current secondary progress, a value between 0 and {@link #getMax()}
     */
    public int getSecondaryProgress() {
        if (mProgress != null) {
            return mProgress.getSecondaryProgress();
        }
        return mSecondaryProgressVal;
    }

    /**
     * Gets the maximum allowed progress value. The default value is 100.
     *
     * @return the maximum value
     */
    public int getMax() {
        if (mProgress != null) {
            return mProgress.getMax();
        }
        return mMax;
    }

    /**
     * Sets the maximum allowed progress value.
     */
    public void setMax(int max) {
        if (mProgress != null) {
            mProgress.setMax(max);
            onProgressChanged();
        } else {
            mMax = max;
        }
    }

    /**
     * Increments the current progress value.
     *
     * @param diff the amount by which the current progress will be incremented,
     *             up to {@link #getMax()}
     */
    public void incrementProgressBy(int diff) {
        if (mProgress != null) {
            mProgress.incrementProgressBy(diff);
            onProgressChanged();
        } else {
            mIncrementBy += diff;
        }
    }

    /**
     * Increments the current secondary progress value.
     *
     * @param diff the amount by which the current secondary progress will be incremented,
     *             up to {@link #getMax()}
     */
    public void incrementSecondaryProgressBy(int diff) {
        if (mProgress != null) {
            mProgress.incrementSecondaryProgressBy(diff);
            onProgressChanged();
        } else {
            mIncrementSecondaryBy += diff;
        }
    }

    /**
     * Sets the drawable to be used to display the progress value.
     *
     * @param d the drawable to be used
     * @see ProgressBar#setProgressDrawable(Drawable)
     */
    public void setProgressDrawable(Drawable d) {
        if (mProgress != null) {
            mProgress.setProgressDrawable(d);
        } else {
            mProgressDrawable = d;
        }
    }

    /**
     * Sets the drawable to be used to display the indeterminate progress value.
     *
     * @param d the drawable to be used
     * @see ProgressBar#setProgressDrawable(Drawable)
     * @see #setIndeterminate(boolean)
     */
    public void setIndeterminateDrawable(Drawable d) {
        if (mProgress != null) {
            mProgress.setIndeterminateDrawable(d);
        } else {
            mIndeterminateDrawable = d;
        }
    }

    /**
     * Change the indeterminate mode for this ProgressDialog. In indeterminate
     * mode, the progress is ignored and the dialog shows an infinite
     * animation instead.
     *
     * <p><strong>Note:</strong> A ProgressDialog with style {@link #STYLE_SPINNER}
     * is always indeterminate and will ignore this setting.</p>
     *
     * @param indeterminate true to enable indeterminate mode, false otherwise
     * @see #setProgressStyle(int)
     */
    public void setIndeterminate(boolean indeterminate) {
        if (mProgress != null) {
            mProgress.setIndeterminate(indeterminate);
        } else {
            mIndeterminate = indeterminate;
        }
    }

    /**
     * Whether this ProgressDialog is in indeterminate mode.
     *
     * @return true if the dialog is in indeterminate mode, false otherwise
     */
    public boolean isIndeterminate() {
        if (mProgress != null) {
            return mProgress.isIndeterminate();
        }
        return mIndeterminate;
    }

    @Override
    public void setMessage(CharSequence message) {
        if (mProgress != null) {
            if (mProgressStyle == STYLE_HORIZONTAL) {
                super.setMessage(message);
            } else {
                mMessageView.setText(message);
            }
        } else {
            mMessage = message;
        }
    }

    /**
     * Sets the style of this ProgressDialog, either {@link #STYLE_SPINNER} or
     * {@link #STYLE_HORIZONTAL}. The default is {@link #STYLE_SPINNER}.
     *
     * <p><strong>Note:</strong> A ProgressDialog with style {@link #STYLE_SPINNER}
     * is always indeterminate and will ignore the {@link #setIndeterminate(boolean)
     * indeterminate} setting.</p>
     *
     * @param style the style of this ProgressDialog, either {@link #STYLE_SPINNER} or
     *              {@link #STYLE_HORIZONTAL}
     */
    public void setProgressStyle(int style) {
        mProgressStyle = style;
    }

    /**
     * Change the format of the small text showing current and maximum units
     * of progress.  The default is "%1d/%2d".
     * Should not be called during the number is progressing.
     *
     * @param format A string passed to {@link String#format String.format()};
     *               use "%1d" for the current number and "%2d" for the maximum.  If null,
     *               nothing will be shown.
     */
    public void setProgressNumberFormat(String format) {
        mProgressNumberFormat = format;
        onProgressChanged();
    }

    /**
     * Change the format of the small text showing the percentage of progress.
     * The default is
     * {@link NumberFormat#getPercentInstance() NumberFormat.getPercentageInstnace().}
     * Should not be called during the number is progressing.
     *
     * @param format An instance of a {@link NumberFormat} to generate the
     *               percentage text.  If null, nothing will be shown.
     */
    public void setProgressPercentFormat(NumberFormat format) {
        mProgressPercentFormat = format;
        onProgressChanged();
    }

    private void onProgressChanged() {
        if (mProgressStyle == STYLE_HORIZONTAL) {
            if (mViewUpdateHandler != null && !mViewUpdateHandler.hasMessages(0)) {
                mViewUpdateHandler.sendEmptyMessage(0);
            }
        }
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(@NonNull LifecycleOwner owner) {
        if (isShowing()) {
            dismiss();
            setOnCancelListener(null);
            setOnDismissListener(null);
            setOnKeyListener(null);
            setOnShowListener(null);
        }
        owner.getLifecycle().removeObserver(this);
    }
}
