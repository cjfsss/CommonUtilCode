package com.cjf.ui.bar

/**
 * Description:
 * Create by dance, at 2019/7/8
 */
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.cjf.ui.shape.ShapeLinearLayout
import com.cjf.ui.shape.ShapeTextView
import com.cjf.util.R
import com.cjf.util.extension.*

class TabBar @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : ShapeLinearLayout(context, attributeSet, defStyleAttr) {

    var iconSize = dp2px(20f)
    var mTextSize = sp2px(14f)
    var selectedColor = Color.RED
    var normalColor = Color.BLACK
    var tabHeight = 0
    var iconSpace = dp2px(2f) //图片和文字间距
    var mTabs = listOf<Tab>()
    var tabIndex = -1

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.TabBar)
        iconSize = ta.getDimensionPixelSize(R.styleable.TabBar_tb_iconSize, iconSize)
        mTextSize = ta.getDimensionPixelSize(R.styleable.TabBar_tb_textSize, mTextSize)
        iconSpace = ta.getDimensionPixelSize(R.styleable.TabBar_tb_iconSpace, iconSpace)
        selectedColor = ta.getColor(R.styleable.TabBar_tb_selectedColor, selectedColor)
        normalColor = ta.getColor(R.styleable.TabBar_tb_normalColor, normalColor)
        tabHeight = ta.getDimension(R.styleable.TabBar_tb_tabHeight, tabHeight.toFloat()).toInt()

        ta.recycle()
        orientation = HORIZONTAL
    }

     var mTabChangeListener: ((index: Int)->Boolean)? = null
    fun setTabs(tabs: List<Tab>, tabChangeListener: ((index: Int)->Boolean)? = null  ): TabBar {
        mTabs = tabs
        mTabChangeListener = tabChangeListener
        mTabs.forEachIndexed { index, it ->
            val lp = LayoutParams(0, LayoutParams.MATCH_PARENT)
            lp.weight = 1f
            lp.gravity = Gravity.CENTER_VERTICAL
            val wrapper = LinearLayout(context)
            addView(wrapper, lp)

            wrapper.addView(ShapeTextView(context).apply {
                gravity = Gravity.CENTER
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply { gravity = Gravity.CENTER_VERTICAL }
                sizeDrawable(size = iconSize, topDrawable = it.normalIconRes)
                compoundDrawablePadding = iconSpace
                text = it.text
                setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize.toFloat())
                setTextColor(normalColor)
                if(tabHeight!=0) this.height(tabHeight)
            })
            wrapper.apply {
                val typedValue =  TypedValue()
                context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
                setBackgroundResource(typedValue.resourceId)
                click {
                    selectTab(index)
                }
            }
        }
        selectTab(0)
        return this
    }

    var vp : ViewPager? = null
    fun setupWithViewPager(pager: ViewPager){
        vp = pager
        pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
            override fun onPageSelected(p: Int) {
                selectTab(p)
            }
        })
    }

    fun selectTab(index: Int) {
        if(mTabChangeListener!=null && !mTabChangeListener!!(index))return
        if(tabIndex==index)return
        tabIndex = index
        vp?.currentItem = tabIndex
        children.forEachIndexed { i, it ->
            val group = it as ViewGroup
            if(index==i){
                (group.getChildAt(0) as TextView).apply {
                    sizeDrawable(size = iconSize, topDrawable = mTabs[i].selectedIconRes)
                    setTextColor(selectedColor)
                }
            }else{
                (group.getChildAt(0) as TextView).apply {
                    sizeDrawable(size = iconSize, topDrawable = mTabs[i].normalIconRes)
                    setTextColor(normalColor)
                }
            }
        }
    }

    data class Tab(
            var text: String = "",
            var normalIconRes: Int = 0,
            var selectedIconRes: Int = 0
    )


}