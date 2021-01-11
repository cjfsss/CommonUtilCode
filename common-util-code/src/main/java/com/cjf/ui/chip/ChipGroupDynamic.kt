package com.cjf.ui.chip

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

/**
 * <p>Title: ChipGroupDynamic </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/10/10 17:00
 * @version : 1.0
 */
class ChipGroupDynamic @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ChipGroup(context, attrs, defStyleAttr) {

    fun addViewInLayout(child: View?) {
        addViewInLayout(child, -1)
    }

    fun addViewInLayout(child: View?, index: Int) {
        requireNotNull(child) { "Cannot add a null child view to a ViewGroup" }
        var params = child.layoutParams
        if (params == null) {
            params = generateDefaultLayoutParams()
        }
        addView(child, index, params)
//        addViewInLayout(child, index, params)
    }


//    open override fun addViewInLayout(child: View?, index: Int, params: ViewGroup.LayoutParams?, preventRequestLayout: Boolean): Boolean {
//        if (child is Chip) {
//            if (child.isChecked) {
//                check(child.id)
//            }
//        }
//        addView(child, index, params)
//        return super.addViewInLayout(child, index, params, preventRequestLayout)
//    }
}