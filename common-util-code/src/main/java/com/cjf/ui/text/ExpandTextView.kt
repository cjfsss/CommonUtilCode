package com.cjf.ui.text

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.cjf.util.R
import com.cjf.util.UtilX
import com.cjf.util.utils.ResUtils

/**
 * <p>Title: ExpandTextView </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/18 9:51
 * @version : 1.0
 */
class ExpandTextView : AppCompatTextView {
    /**展开状态 true：展开，false：收起 */
    var expandState: Boolean = false

    /** 状态接口 */
    var mCallback: ExpandCallback? = null

    /** 源文字内容 */
    var mText: CharSequence? = ""

    /** 源文字所有内容 */
    var mTextAll: String? = ""

    /** 开头文字内容 */
    var mTextStart: CharSequence? = ""

    /** 最多展示的行数 */
    var maxLineCount = 3

    /** 省略文字 */
    var ellipsizeText = "..."

    /** 展开文案文字 */
    var expandText: CharSequence = "[全文]"

    /** 展开文案文字颜色 */
    var expandTextColor: Int = Color.parseColor("#1C7FFD")

    /** 收起文案文字 */
    var collapseText = "[收起]"

    /** 收起文案文字颜色 */
    var collapseTextColor: Int = Color.parseColor("#1C7FFD")

    /** 开始文字颜色 */
    var textStartColor: Int = ResUtils.getColor(R.color.design_txt_gray)

    /**是否支持收起功能*/
    var collapseEnable = true

    /** 是否添加下划线 */
    var underlineEnable = false

    constructor(context: Context) : this(context, null) {
    }

    constructor(context: Context, attributes: AttributeSet?) : this(context, attributes, 0) {
    }

    constructor(context: Context, attributes: AttributeSet?, defStyleAttr: Int) : super(context, attributes, defStyleAttr) {
        initTextView(context, attributes, defStyleAttr)
    }

    private fun initTextView(context: Context, attributes: AttributeSet?, defStyleAttr: Int) {
        movementMethod = LinkMovementMethod.getInstance()
        val array = context.obtainStyledAttributes(attributes, R.styleable.ExpandTextView, defStyleAttr, 0)
        try {
            if (array.hasValue(R.styleable.ExpandTextView_textExpand)) {
                expandText = array.getString(R.styleable.ExpandTextView_textExpand) ?: "[全文]"
            }
            if (array.hasValue(R.styleable.ExpandTextView_textCollapse)) {
                collapseText = array.getString(R.styleable.ExpandTextView_textCollapse) ?: "[收起]"
            }
            textStartColor = array.getColor(R.styleable.ExpandTextView_textStartColor, ResUtils.getColor(R.color.design_txt_gray))
            expandTextColor = array.getColor(R.styleable.ExpandTextView_textExpandColor, UtilX.getPrimaryColor())
            collapseTextColor = array.getColor(R.styleable.ExpandTextView_textCollapseColor, UtilX.getPrimaryColor())
            if (array.hasValue(R.styleable.ExpandTextView_maxLineCount)) {
                maxLineCount = array.getInt(R.styleable.ExpandTextView_maxLineCount, 3)
            }
        } finally {
            array.recycle()
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 文字计算辅助工具
        if (mTextAll.isNullOrEmpty()) {
            setMeasuredDimension(measuredWidth, measuredHeight)
        }
        //StaticLayout对象
        val sl = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(
                    mTextAll ?: "",
                    0,
                    mTextAll?.length ?: 0,
                    paint,
                    measuredWidth - paddingLeft - paddingRight
            ).apply {
                setAlignment(Layout.Alignment.ALIGN_CENTER)
            }.build()
        } else {
            StaticLayout(
                    mTextAll, paint, measuredWidth - paddingLeft - paddingRight,
                    Layout.Alignment.ALIGN_CENTER, 1f, 0f,
                    true
            )
        }

        // 总计行数
        var lineCount = sl.lineCount
        //总行数大于最大行数
        if (lineCount > maxLineCount) {
            if (expandState) {
                text = mTextAll
                //是否支持收起功能
                if (collapseEnable) {
                    // 收起文案和源文字组成的新的文字
                    val newEndLineText = mTextAll + collapseText
                    //收起文案和源文字组成的新的文字
                    val spannableString = SpannableStringBuilder(newEndLineText)
                            .apply {
                                //给开始文字设成蓝色
                                if (mTextStart != null) {
                                    setSpan(
                                            ForegroundColorSpan(textStartColor),
                                            0,
                                            mTextAll?.indexOf(mTextStart!!.toString())!! + mTextStart!!.length,
                                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                                    )
                                }
                                //给收起设成监听
                                setSpan(
                                        object : ClickableSpan() {
                                            override fun onClick(widget: View) {
                                                if (mCallback != null) {
                                                    mCallback!!.onCollapseClick()
                                                }
                                            }

                                            override fun updateDrawState(ds: TextPaint) {
                                                ds.color = collapseTextColor
                                                ds.isUnderlineText = false
                                            }
                                        },
                                        newEndLineText.length - collapseText.length,
                                        newEndLineText.length,
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                                if (underlineEnable) {
                                    //给收起添加下划线
                                    setSpan(
                                            UnderlineSpan(),
                                            newEndLineText.length - collapseText.length,
                                            newEndLineText.length,
                                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                    )
                                }
                            }
                    text = spannableString
                }
                mCallback?.onExpand()
            } else {
                lineCount = maxLineCount
                // 省略文字和展开文案的宽度
                val dotWidth = paint.measureText(ellipsizeText + expandText)
                // 找出显示最后一行的文字
                val start = sl.getLineStart(lineCount - 1)
                val end = sl.getLineEnd(lineCount - 1)
                val lineText = mTextAll?.substring(start, end) ?: ""
                // 将第最后一行最后的文字替换为 ellipsizeText和expandText
                var endIndex = 0
                for (i in lineText.length - 1 downTo 0) {
                    val str = lineText.substring(i, lineText.length)
                    // 找出文字宽度大于 ellipsizeText 的字符
                    if (paint.measureText(str) >= dotWidth) {
                        endIndex = i
                        break
                    }
                }
                // 新的文字
                val newEndLineText = (mTextAll?.substring(0, start) ?: "") + lineText.substring(
                        0,
                        endIndex
                ) + ellipsizeText + expandText
                //全部文字
                val spannableString = SpannableStringBuilder(newEndLineText).apply {
                    //给查看全部设成监听
                    //给开始文字设成蓝色
                    if (mTextStart != null) {
                        setSpan(
                                ForegroundColorSpan(textStartColor),
                                0,
                                mTextAll?.indexOf(mTextStart!!.toString())!! + mTextStart!!.length,
                                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                    }
                    setSpan(
                            object : ClickableSpan() {
                                override fun onClick(widget: View) {
                                    if (mCallback != null) {
                                        mCallback!!.onExpandClick()
                                    }
                                }

                                override fun updateDrawState(ds: TextPaint) {
                                    ds.color = expandTextColor
                                    ds.isUnderlineText = false
                                }
                            },
                            newEndLineText.length - expandText.length,
                            newEndLineText.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    if (underlineEnable) {
                        setSpan(
                                UnderlineSpan(),
                                newEndLineText.length - expandText.length,
                                newEndLineText.length,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
                // 最终显示的文字
                text = spannableString
                mCallback?.onCollapse()
            }
        } else {
            //收起文案和源文字组成的新的文字
            val spannableString = SpannableStringBuilder(mTextAll)
                    .apply {
                        //给开始文字设成蓝色
                        if (mTextStart != null) {
                            setSpan(
                                    ForegroundColorSpan(textStartColor),
                                    0,
                                    mTextAll?.indexOf(mTextStart!!.toString())!! + mTextStart!!.length,
                                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                            )
                        }
                    }
            text = spannableString
            mCallback?.onLoss()

        }
        // 重新计算高度
        var lineHeight = 0
        for (i in 0 until lineCount) {
            val lineBound = Rect()
            sl.getLineBounds(i, lineBound)
            lineHeight += lineBound.height()
        }
        lineHeight = (paddingTop + paddingBottom + lineHeight * lineSpacingMultiplier).toInt()
        setMeasuredDimension(measuredWidth, lineHeight)
    }

    /**
     * 设置要显示的文字以及状态
     * @param text
     * @param expanded true：展开，false：收起
     * @param callback
     */
    fun setContentText(textStart: CharSequence, text: CharSequence, expanded: Boolean = false, callback: ExpandCallback = DefaultExpandCallback(this)) {
        mTextStart = textStart;
        mText = text
        mTextAll = textStart.toString() + text.toString()
        expandState = expanded
        mCallback = callback
        // 设置要显示的文字，这一行必须要，否则 onMeasure 宽度测量不正确
        setText(mTextAll)
    }

    /**
     * 设置要显示的文字以及状态
     * @param text
     * @param expanded true：展开，false：收起
     * @param callback
     */
    fun setContentText(text: CharSequence, expanded: Boolean = false, callback: ExpandCallback = DefaultExpandCallback(this)) {
        setContentText("", text, expanded, callback)
    }

    /**
     * 展开收起状态变化
     * @param expanded
     */
    fun setChanged(expanded: Boolean) {
        expandState = expanded
        requestLayout()
    }

    interface ExpandCallback {
        /**
         * 展开状态
         */
        fun onExpand()

        /**
         * 收起状态
         */
        fun onCollapse()

        /**
         * 行数小于最小行数，不满足展开或者收起条件
         */
        fun onLoss()

        /**
         * 点击全文
         */
        fun onExpandClick()

        /**
         * 点击收起
         */
        fun onCollapseClick()
    }

    open class DefaultExpandCallback(private val textView: ExpandTextView) : ExpandCallback {

        override fun onExpand() {

        }

        override fun onCollapse() {
        }

        override fun onLoss() {
        }

        override fun onExpandClick() {
            textView.setChanged(true)
        }

        override fun onCollapseClick() {
            textView.setChanged(false)
        }

    }
}