package com.cjf.ui.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.widget.AppCompatTextView;

import com.cjf.util.R;
import com.cjf.util.utils.ResUtils;

/**
 * <p>Title: ExpandTextView </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/9/10 12:02
 */
public class ExpandTextView extends AppCompatTextView {

    private String originText;// 原始内容文本
    private int initWidth = 0;// TextView可展示宽度
    private int mMaxLines = 3;// TextView最大行数
    private SpannableString SPAN_CLOSE = null;// 收起的文案(颜色处理)
    private SpannableString SPAN_EXPAND = null;// 展开的文案(颜色处理)
    private String mTextExpand = "  展开";
    @ColorRes
    private int mTextExpandColor = R.color.colorAccent;
    private String mTextClose = "  收起";
    private int mTextCloseColor = R.color.colorAccent;

    public ExpandTextView(Context context) {
        this(context, null);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ExpandTextView, defStyleAttr, 0);
        try {
            if (array.hasValue(R.styleable.ExpandTextView_textClose)) {
                mTextClose = array.getString(R.styleable.ExpandTextView_textClose);
            }
            if (array.hasValue(R.styleable.ExpandTextView_textCloseColor)) {
                mTextCloseColor = array.getInt(R.styleable.ExpandTextView_textCloseColor, R.color.colorAccent);
            }
            if (array.hasValue(R.styleable.ExpandTextView_textExpandColor)) {
                mTextExpandColor = array.getInt(R.styleable.ExpandTextView_textExpandColor, R.color.colorAccent);
            }
            if (array.hasValue(R.styleable.ExpandTextView_textExpand)) {
                mTextExpand = array.getString(R.styleable.ExpandTextView_textExpand);
            }
        } finally {
            array.recycle();
        }
        initCloseEnd();
    }

    /**
     * 设置TextView可显示的最大行数
     *
     * @param maxLines 最大行数
     */
    @Override
    public void setMaxLines(int maxLines) {
        this.mMaxLines = maxLines;
        super.setMaxLines(maxLines);
    }

    /**
     * 初始化TextView的可展示宽度
     *
     * @param width
     */
    public void initWidth(int width) {
        initWidth = width;
    }

    /**
     * 收起的文案(颜色处理)初始化
     */
    private void initCloseEnd() {
        String content = mTextExpand;
        SPAN_CLOSE = new SpannableString(content);
        ButtonSpan span = new ButtonSpan(getContext(), new OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpandTextView.super.setMaxLines(Integer.MAX_VALUE);
                setExpandText(originText);
            }
        }, mTextCloseColor);
        SPAN_CLOSE.setSpan(span, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    /**
     * 展开的文案(颜色处理)初始化
     */
    private void initExpandEnd() {
        String content = mTextClose;
        SPAN_EXPAND = new SpannableString(content);
        ButtonSpan span = new ButtonSpan(getContext(), new OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpandTextView.super.setMaxLines(mMaxLines);
                setCloseText(originText);
            }
        }, mTextExpandColor);
        SPAN_EXPAND.setSpan(span, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    public void setCloseText(CharSequence text) {

        if (SPAN_CLOSE == null) {
            initCloseEnd();
        }
        boolean appendShowAll = false;// true 不需要展开收起功能， false 需要展开收起功能
        originText = text.toString();

        // SDK >= 16 可以直接从xml属性获取最大行数
        int maxLines = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            maxLines = getMaxLines();
        } else {
            maxLines = mMaxLines;
        }
        String workingText = new StringBuilder(originText).toString();
        if (maxLines != -1) {
            Layout layout = createWorkingLayout(workingText);
            if (layout.getLineCount() > maxLines) {
                //获取一行显示字符个数，然后截取字符串数
                workingText = originText.substring(0, layout.getLineEnd(maxLines - 1)).trim();// 收起状态原始文本截取展示的部分
                String showText = originText.substring(0, layout.getLineEnd(maxLines - 1)).trim() + "..." + SPAN_CLOSE;
                Layout layout2 = createWorkingLayout(showText);
                // 对workingText进行-1截取，直到展示行数==最大行数，并且添加 SPAN_CLOSE 后刚好占满最后一行
                while (layout2.getLineCount() > maxLines) {
                    int lastSpace = workingText.length() - 1;
                    if (lastSpace == -1) {
                        break;
                    }
                    workingText = workingText.substring(0, lastSpace);
                    layout2 = createWorkingLayout(workingText + "..." + SPAN_CLOSE);
                }
                appendShowAll = true;
                workingText = workingText + "...";
            }
        }

        setText(workingText);
        if (appendShowAll) {
            // 必须使用append，不能在上面使用+连接，否则spannable会无效
            append(SPAN_CLOSE);
            setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @SuppressLint("SetTextI18n")
    public void setExpandText(String text) {
        if (SPAN_EXPAND == null) {
            initExpandEnd();
        }
        Layout layout1 = createWorkingLayout(text);
        Layout layout2 = createWorkingLayout(text + mTextClose);
        // 展示全部原始内容时 如果 TEXT_CLOSE 需要换行才能显示完整，则直接将TEXT_CLOSE展示在下一行
        if (layout2.getLineCount() > layout1.getLineCount()) {
            setText(originText + "\n");
        } else {
            setText(originText);
        }
        append(SPAN_EXPAND);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    //返回textview的显示区域的layout，该textview的layout并不会显示出来，只是用其宽度来比较要显示的文字是否过长
    private Layout createWorkingLayout(String workingText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return new StaticLayout(workingText, getPaint(), initWidth - getPaddingLeft() - getPaddingRight(),
                                    Layout.Alignment.ALIGN_NORMAL, getLineSpacingMultiplier(), getLineSpacingExtra(),
                                    false);
        } else {
            return new StaticLayout(workingText, getPaint(), initWidth - getPaddingLeft() - getPaddingRight(),
                                    Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        }
    }
}