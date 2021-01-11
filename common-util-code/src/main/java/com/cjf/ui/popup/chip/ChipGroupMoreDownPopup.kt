package com.cjf.ui.popup.chip

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.NestedScrollView
import com.blankj.utilcode.util.ScreenUtils
import com.cjf.ui.chip.ChipGroupDynamic
import com.cjf.util.R
import com.cjf.util.extension.height
import com.cjf.util.utils.ResUtils
import com.cjf.util.utils.ViewUtils
import com.google.android.material.chip.Chip
import com.lxj.xpopup.impl.PartShadowPopupView
import com.lxj.xpopup.interfaces.OnCancelListener

/**
 * <p>Title: MultiLevelDownPopup </p>
 * <p>Description: 使用ChipGroup进行选择 </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/28 10:01
 * @version : 1.0
 */
open class ChipGroupMoreDownPopup(context: Context) : PartShadowPopupView(context),
        View.OnClickListener {

    private var cancelListener: OnCancelListener? = null
    private var confirmListener: OnSelectMultiLevelListener? = null
    private var rootView: LinearLayoutCompat? = null
    private var tv_cancel: TextView? = null
    private var tv_confirm: TextView? = null
    private var chipGroup: ChipGroupDynamic? = null
    private var nestedScrollView: NestedScrollView? = null
    private var cancelText: CharSequence? = null
    private var confirmText: CharSequence? = null
    private var isHideCancel = false
    private var selectPositionList = arrayListOf<Int>()

    private val dataSourceList by lazy { ArrayList<String>() }
    private val dataList by lazy { HashMap<Int, List<Any>>() }

    // 屏幕的高度
    private val screenHeight by lazy { ScreenUtils.getScreenHeight() }

    override fun getImplLayoutId(): Int {
        return R.layout._xpopup_chip_group_down
    }

    override fun onCreate() {
        super.onCreate()
        nestedScrollView = findViewById(R.id.nestedScrollView)
        rootView = findViewById(R.id.rootView)
        tv_cancel = findViewById(R.id.tv_cancel)
        tv_confirm = findViewById(R.id.tv_confirm)
        chipGroup = findViewById(R.id.chipGroup)
        tv_cancel?.setOnClickListener(this)
        tv_confirm?.setOnClickListener(this)
        if (!TextUtils.isEmpty(cancelText)) {
            tv_cancel?.text = cancelText
        }
        if (!TextUtils.isEmpty(confirmText)) {
            tv_confirm?.text = confirmText
        }
        if (isHideCancel) {
            tv_cancel?.visibility = GONE
            val divider = findViewById<View>(R.id.xpopup_divider_h)
            if (divider != null) divider.visibility = GONE
        }
        if (popupInfo.isDarkTheme) {
            applyDarkTheme()
        }
        loadData(dataSourceList)
    }

    override fun applyDarkTheme() {
        super.applyDarkTheme()
        rootView?.setBackgroundResource(R.drawable._xpopup_round3_bottom_dark_bg)
        tv_cancel?.setTextColor(ResUtils.getColor(R.color._xpopup_white_color))
        tv_confirm?.setTextColor(ResUtils.getColor(R.color._xpopup_white_color))
        findViewById<View>(R.id.xpopup_divider).setBackgroundColor(ResUtils.getColor(R.color._xpopup_dark_color))
        findViewById<View>(R.id.xpopup_divider_h).setBackgroundColor(ResUtils.getColor(R.color._xpopup_dark_color))
    }

    fun setListener(
            confirmListener: OnSelectMultiLevelListener? = null, cancelListener: OnCancelListener? = null
    ): ChipGroupMoreDownPopup {
        this.cancelListener = cancelListener
        this.confirmListener = confirmListener
        return this
    }

    fun setCancelText(cancelText: CharSequence?): ChipGroupMoreDownPopup {
        this.cancelText = cancelText
        return this
    }

    fun setConfirmText(confirmText: CharSequence?): ChipGroupMoreDownPopup {
        this.confirmText = confirmText
        return this
    }

    override fun onClick(v: View?) {
        if (v === tv_cancel) {
            if (cancelListener != null) cancelListener!!.onCancel()
            dismiss()
        } else if (v === tv_confirm) {
            val nameList = arrayListOf<String>()
            val positionList = arrayListOf<Int>()
            val checkedChipIds = chipGroup?.checkedChipIds
            var position = 0
            var tag = ""
            if (checkedChipIds != null && !dataList.isNullOrEmpty()) {
                for (id in checkedChipIds) {
                    val list = dataList[id]
                    list?.let {
                        position = it[0] as Int
                        tag = it[1] as String
                        positionList.add(position)
                        nameList.add(tag)
                    }
                }
            }
            confirmListener?.onSelect(this, nameList, positionList, 0)
            if (popupInfo.autoDismiss) dismiss()
        }
    }

    fun checkPosition(positionList: List<Int>): ChipGroupMoreDownPopup {
        selectPositionList.clear()
        this.selectPositionList.addAll(positionList)
        return this
    }

    /**
     * 设置数据
     */
    fun setData(dataList: ArrayList<String>): ChipGroupMoreDownPopup {
        dataSourceList.clear()
        dataSourceList.addAll(dataList)
        return this
    }

    /**
     * 加载数据
     */
    private fun loadData(dataSourceList: List<String>) {
        chipGroup?.let { group ->
            group.removeAllViews()
            this.dataList.clear()
            val size = dataSourceList.size
            val checkId = arrayListOf<Int>()
            for (i in 0 until size) {
                val chip = createTagTextView(dataSourceList[i], i)
                group.addViewInLayout(chip)
                for (position in selectPositionList){
                    if (position == i) {
                        checkId.add(chip.id)
                    }
                }
            }
            group.requestLayout()
            group.invalidate()
            group.post {
                if (group.height > screenHeight * 0.5f) {
                    nestedScrollView?.height((screenHeight * 0.5f).toInt())
                } else {
                    nestedScrollView?.height(ViewGroup.LayoutParams.WRAP_CONTENT)
                }
            }
            for (id in checkId) {
                group.check(id)
            }
        }
    }

    /**
     * 创建每一个子项
     */
    private fun createTagTextView(tag: String, position: Int): Chip {
        val chipText = View.inflate(context, R.layout._xpopup_chip_filter, null) as Chip
        chipText.text = tag
        chipText.id = ViewUtils.generateViewId()
        dataList[chipText.id] = listOf(position, tag)
        return chipText
    }

    override fun destroy() {
        chipGroup?.setOnCheckedChangeListener(null)
        cancelListener = null
        confirmListener = null
        rootView = null
        tv_cancel = null
        tv_confirm = null
        chipGroup = null
        dataSourceList.clear()
        dataList.clear()
        super.destroy()
    }
}