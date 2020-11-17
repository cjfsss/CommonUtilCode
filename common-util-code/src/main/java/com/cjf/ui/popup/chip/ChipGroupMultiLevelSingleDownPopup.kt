package com.cjf.ui.popup.chip

import android.annotation.SuppressLint
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
import com.cjf.util.extension.click
import com.cjf.util.extension.empty
import com.cjf.util.extension.getTextString
import com.cjf.util.extension.height
import com.cjf.util.utils.ResUtils
import com.cjf.util.utils.ViewUtils
import com.google.android.material.chip.Chip
import com.lxj.xpopup.impl.PartShadowPopupView
import com.lxj.xpopup.interfaces.OnCancelListener

/**
 * <p>Title: MultiLevelDownPopup </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/9/28 10:01
 * @version : 1.0
 */
open class ChipGroupMultiLevelSingleDownPopup(context: Context) : PartShadowPopupView(context),
    View.OnClickListener {

    private var cancelListener: OnCancelListener? = null
    private var confirmListener: OnSelectMultiLevelListener? = null
    private var rootView: LinearLayoutCompat? = null
    private var tv_cancel: TextView? = null
    private var tv_confirm: TextView? = null
    private var tv_back: TextView? = null
    private var tv_title: TextView? = null
    private var chipGroup: ChipGroupDynamic? = null
    private var nestedScrollView: NestedScrollView? = null
    private var cancelText: CharSequence? = null
    private var confirmText: CharSequence? = null
    private var isHideCancel = false

    // 多级的等级 0 ，1 ，2, 3 ，4, 5
    private var currentLevel = 0

    private var currentOptions0Position = -1
    private var currentOptions1Position = -1
    private var currentOptions2Position = -1
    private var currentOptions3Position = -1
    private var currentOptions4Position = -1

    private val options0Items = ArrayList<String>()
    private val options1Items = ArrayList<ArrayList<String>>()
    private val options2Items = ArrayList<ArrayList<ArrayList<String>>>()
    private val options3Items = ArrayList<ArrayList<ArrayList<ArrayList<String>>>>()
    private val options4Items = ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<String>>>>>()

    private val dataList by lazy { HashMap<Int, List<Any>>() }

    // 屏幕的高度
    private val screenHeight by lazy { ScreenUtils.getScreenHeight() }

    init {
        addInnerContent()
    }

    override fun getImplLayoutId(): Int {
        return R.layout._xpopup_chip_group_multilevel_down
    }

    @SuppressLint("SetTextI18n")
    override fun initPopupContent() {
        super.initPopupContent()
        rootView = findViewById(R.id.rootView)
        nestedScrollView = findViewById(R.id.nestedScrollView)
        tv_cancel = findViewById(R.id.tv_cancel)
        tv_confirm = findViewById(R.id.tv_confirm)
        tv_back = findViewById(R.id.tv_back)
        tv_title = findViewById(R.id.tv_title)
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
        loadData(options0Items)
        onCheckedChanged()
        tv_back?.click {
            // 退回到上一级
            if (currentLevel == 0) {
                currentOptions0Position = -1
                tv_title?.text = "请选择"
            } else if (currentLevel == 1) {
                currentLevel = 0
                currentOptions1Position = -1
                tv_title?.text = options0Items[currentOptions0Position]
            } else if (currentLevel == 2) {
                currentLevel = 1
                currentOptions2Position = -1
                tv_title?.text = options0Items[currentOptions0Position] + "/" +
                        options1Items[currentOptions0Position][currentOptions1Position]
            } else if (currentLevel == 3) {
                currentLevel = 2
                currentOptions3Position = -1
                tv_title?.text = options0Items[currentOptions0Position] + "/" +
                        options1Items[currentOptions0Position][currentOptions1Position] + "/" +
                        options2Items[currentOptions0Position][currentOptions1Position][currentOptions2Position]
            } else if (currentLevel == 4) {
                currentLevel = 3
                currentOptions4Position = -1
                tv_title?.text = options0Items[currentOptions0Position] + "/" +
                        options1Items[currentOptions0Position][currentOptions1Position] + "/" +
                        options2Items[currentOptions0Position][currentOptions1Position][currentOptions2Position] + "/" +
                        options3Items[currentOptions0Position][currentOptions1Position][currentOptions2Position][currentOptions3Position]
            }
            chipGroup?.setOnCheckedChangeListener(null)
            gotoPreviousNode()
            onCheckedChanged()
            onBackChanged()
        }
    }

    // 返回键发生变化
    private fun onBackChanged() {
        val title = tv_title?.getTextString()
        if (title == "请选择") {
            tv_back?.visibility = GONE
        } else {
            tv_back?.visibility = View.VISIBLE
        }
    }

    /**
     * 选择发生变化监听
     */
    @SuppressLint("SetTextI18n")
    private fun onCheckedChanged() {
        chipGroup?.setOnCheckedChangeListener { _, checkedId ->
            var position = -1
            var tag: String? = "请选择"
            if (!dataList.isNullOrEmpty()) {
                val list = dataList[checkedId]
                list?.let {
                    position = it[0] as Int
                    tag = (it[1] as String).empty()
                }
            }
            if (currentLevel == 0) {
                currentOptions0Position = position
                tv_title?.text = tag
            } else if (currentLevel == 1) {
                currentOptions1Position = position
                tv_title?.text = options0Items[currentOptions0Position] + "/" + tag
            } else if (currentLevel == 2) {
                currentOptions2Position = position
                tv_title?.text = options0Items[currentOptions0Position] + "/" +
                        options1Items[currentOptions0Position][currentOptions1Position] + "/" + tag
            } else if (currentLevel == 3) {
                currentOptions3Position = position
                tv_title?.text = options0Items[currentOptions0Position] + "/" +
                        options1Items[currentOptions0Position][currentOptions1Position] + "/" +
                        options2Items[currentOptions0Position][currentOptions1Position][currentOptions2Position] + "/" + tag
            } else if (currentLevel == 4) {
                currentOptions4Position = position
                tv_title?.text = options0Items[currentOptions0Position] + "/" +
                        options1Items[currentOptions0Position][currentOptions1Position] + "/" +
                        options2Items[currentOptions0Position][currentOptions1Position][currentOptions2Position] + "/" +
                        options3Items[currentOptions0Position][currentOptions1Position][currentOptions2Position][currentOptions3Position] + "/" + tag
            }
            // 进入下一个节点
            gotoNextNode()
            onBackChanged()
        }
    }

    /**
     * 进入上一个节点
     */
    private fun gotoPreviousNode() {
        if (currentLevel == 0) {
            if (currentOptions0Position == 0) {
                return
            }
            // 切换到第二等级
            if (options0Items.isNotEmpty() && currentOptions0Position < options0Items.size) {
                loadData(options0Items, currentOptions0Position)
            }
        } else if (currentLevel == 1) {
            if (currentOptions1Position == 0) {
                return
            }
            // 切换到第三等级
            if (options1Items.isNotEmpty() && currentOptions0Position < options1Items.size
                && options1Items[currentOptions0Position].isNotEmpty()
                && currentOptions1Position < options1Items[currentOptions0Position].size
                && options1Items[currentOptions0Position][currentOptions1Position].isNotEmpty()
            ) {
                loadData(
                        options1Items[currentOptions0Position],
                        currentOptions1Position
                )
            }
        } else if (currentLevel == 2) {
            if (currentOptions2Position == 0) {
                return
            }
            // 切换到第四等级
            if (options2Items.isNotEmpty() && currentOptions0Position < options2Items.size
                && options2Items[currentOptions0Position].isNotEmpty()
                && currentOptions1Position < options2Items[currentOptions0Position].size
                && options2Items[currentOptions0Position][currentOptions1Position].isNotEmpty()
                && currentOptions2Position < options2Items[currentOptions0Position][currentOptions1Position].size
                && options2Items[currentOptions0Position][currentOptions1Position][currentOptions2Position].isNotEmpty()
            ) {
                loadData(
                        options2Items[currentOptions0Position][currentOptions1Position],
                        currentOptions2Position
                )
            }
        } else if (currentLevel == 3) {
            if (currentOptions3Position == 0) {
                return
            }
            // 切换到第四等级
            if (options3Items.isNotEmpty() && currentOptions0Position < options3Items.size
                && options3Items[currentOptions0Position].isNotEmpty()
                && currentOptions1Position < options3Items[currentOptions0Position].size
                && options3Items[currentOptions0Position][currentOptions1Position].isNotEmpty()
                && currentOptions2Position < options3Items[currentOptions0Position][currentOptions1Position].size
                && options3Items[currentOptions0Position][currentOptions1Position][currentOptions2Position].isNotEmpty()
                && currentOptions3Position < options3Items[currentOptions0Position][currentOptions1Position][currentOptions2Position].size
                && options3Items[currentOptions0Position][currentOptions1Position][currentOptions2Position][currentOptions3Position].isNotEmpty()
            ) {
                loadData(
                        options3Items[currentOptions0Position][currentOptions1Position][currentOptions2Position],
                        currentOptions3Position
                )
            }
        }
    }

    /**
     * 进入下一个节点
     */
    private fun gotoNextNode() {
        if (currentLevel == 0) {
            // 没有选择，或者选择全部的时候,不进入下一级
            if (currentOptions0Position == -1 ) {
                return
            }
            // 切换到第二等级
            if (options1Items.isNotEmpty() && currentOptions0Position < options1Items.size
                && options1Items[currentOptions0Position].isNotEmpty()
            ) {
                currentLevel = 1
                loadData(options1Items[currentOptions0Position], currentOptions1Position)
            }
        } else if (currentLevel == 1) {
            // 没有选择，或者选择全部的时候,不进入下一级
            if (currentOptions1Position == -1) {
                return
            }
            // 切换到第三等级
            if (options2Items.isNotEmpty() && currentOptions0Position < options2Items.size
                && options2Items[currentOptions0Position].isNotEmpty()
                && currentOptions1Position < options2Items[currentOptions0Position].size
                && options2Items[currentOptions0Position][currentOptions1Position].isNotEmpty()
            ) {
                currentLevel = 2
                loadData(
                        options2Items[currentOptions0Position][currentOptions1Position],
                        currentOptions2Position
                )
            }
        } else if (currentLevel == 2) {
            // 没有选择，或者选择全部的时候,不进入下一级
            if (currentOptions2Position == -1 ) {
                return
            }
            // 切换到第四等级
            if (options3Items.isNotEmpty() && currentOptions0Position < options3Items.size
                && options3Items[currentOptions0Position].isNotEmpty()
                && currentOptions1Position < options3Items[currentOptions0Position].size
                && options3Items[currentOptions0Position][currentOptions1Position].isNotEmpty()
                && currentOptions2Position < options3Items[currentOptions0Position][currentOptions1Position].size
                && options3Items[currentOptions0Position][currentOptions1Position][currentOptions2Position].isNotEmpty()
            ) {
                currentLevel = 3
                loadData(
                        options3Items[currentOptions0Position][currentOptions1Position][currentOptions2Position],
                        currentOptions3Position
                )
            }
        } else if (currentLevel == 3) {
            // 没有选择，或者选择全部的时候,不进入下一级
            if (currentOptions3Position == -1 ) {
                return
            }
            // 切换到第四等级
            if (options4Items.isNotEmpty() && currentOptions0Position < options4Items.size
                && options4Items[currentOptions0Position].isNotEmpty()
                && currentOptions1Position < options4Items[currentOptions0Position].size
                && options4Items[currentOptions0Position][currentOptions1Position].isNotEmpty()
                && currentOptions2Position < options4Items[currentOptions0Position][currentOptions1Position].size
                && options4Items[currentOptions0Position][currentOptions1Position][currentOptions2Position].isNotEmpty()
                && currentOptions3Position < options4Items[currentOptions0Position][currentOptions1Position][currentOptions2Position].size
                && options4Items[currentOptions0Position][currentOptions1Position][currentOptions2Position][currentOptions3Position].isNotEmpty()
            ) {
                currentLevel = 4
                loadData(
                        options4Items[currentOptions0Position][currentOptions1Position][currentOptions2Position][currentOptions3Position],
                        currentOptions4Position
                )
            }
        }
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
            confirmListener: OnSelectMultiLevelListener? = null,
            cancelListener: OnCancelListener? = null
    ): ChipGroupMultiLevelSingleDownPopup {
        this.cancelListener = cancelListener
        this.confirmListener = confirmListener
        return this
    }

    fun setCancelText(cancelText: CharSequence?): ChipGroupMultiLevelSingleDownPopup {
        this.cancelText = cancelText
        return this
    }

    fun setConfirmText(confirmText: CharSequence?): ChipGroupMultiLevelSingleDownPopup {
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
            // 添加选中的下标
            positionList.add(currentOptions0Position)
            positionList.add(currentOptions1Position)
            positionList.add(currentOptions2Position)
            positionList.add(currentOptions3Position)
            positionList.add(currentOptions4Position)
            // 添加选中的文字
            if (currentOptions0Position != -1 && currentOptions0Position < options0Items.size) {
                nameList.add(options0Items[currentOptions0Position])
            }
            if (currentOptions0Position != -1
                && currentOptions0Position < options1Items.size
                && currentOptions1Position != -1
                && currentOptions1Position < options1Items[currentOptions0Position].size
            ) {
                nameList.add(options1Items[currentOptions0Position][currentOptions1Position])
            }
            if (currentOptions0Position != -1
                && currentOptions0Position < options2Items.size
                && currentOptions1Position != -1
                && currentOptions1Position < options2Items[currentOptions0Position].size
                && currentOptions2Position != -1
                && currentOptions2Position < options2Items[currentOptions0Position][currentOptions1Position].size
            ) {
                nameList.add(options2Items[currentOptions0Position][currentOptions1Position][currentOptions2Position])
            }
            if (currentOptions0Position != -1
                && currentOptions0Position < options3Items.size
                && currentOptions1Position != -1
                && currentOptions1Position < options3Items[currentOptions0Position].size
                && currentOptions2Position != -1
                && currentOptions2Position < options3Items[currentOptions0Position][currentOptions1Position].size
                && currentOptions3Position != -1
                && currentOptions3Position < options3Items[currentOptions0Position][currentOptions1Position][currentOptions3Position].size
            ) {
                nameList.add(options3Items[currentOptions0Position][currentOptions1Position][currentOptions2Position][currentOptions3Position])
            }
            if (currentOptions0Position != -1
                && currentOptions0Position < options4Items.size
                && currentOptions1Position != -1
                && currentOptions1Position < options4Items[currentOptions0Position].size
                && currentOptions2Position != -1
                && currentOptions2Position < options4Items[currentOptions0Position][currentOptions1Position].size
                && currentOptions3Position != -1
                && currentOptions3Position < options4Items[currentOptions0Position][currentOptions1Position][currentOptions3Position].size
                && currentOptions4Position != -1
                && currentOptions4Position < options4Items[currentOptions0Position][currentOptions1Position][currentOptions3Position][currentOptions4Position].size
            ) {
                nameList.add(options4Items[currentOptions0Position][currentOptions1Position][currentOptions2Position][currentOptions3Position][currentOptions4Position])
            }
            confirmListener?.onSelect(nameList, positionList, currentLevel)
            if (popupInfo.autoDismiss) dismiss()
        }
    }

    /**
     * 设置数据
     */
    fun setData(
            options1Items: ArrayList<String>, options2Items: ArrayList<ArrayList<String>>? = null,
            options3Items: ArrayList<ArrayList<ArrayList<String>>>? = null,
            options4Items: ArrayList<ArrayList<ArrayList<ArrayList<String>>>>? = null,
            options5Items: ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<String>>>>>? = null
    ): ChipGroupMultiLevelSingleDownPopup {
        clearData()
        this.options0Items.addAll(options1Items)
        if (options2Items != null) {
            this.options1Items.addAll(options2Items)
        }
        if (options3Items != null) {
            this.options2Items.addAll(options3Items)
        }
        if (options4Items != null) {
            this.options3Items.addAll(options4Items)
        }
        if (options5Items != null) {
            this.options4Items.addAll(options5Items)
        }
        return this
    }

    private fun clearData() {
        this.options0Items.clear()
        this.options1Items.clear()
        this.options2Items.clear()
        this.options3Items.clear()
        this.options4Items.clear()
    }

    /**
     * 加载数据
     */
    private fun loadData(dataSourceList: ArrayList<String>, currentPosition: Int = -1) {
        chipGroup?.let { group ->
            group.removeAllViews()
            this.dataList.clear()
            val size = dataSourceList.size
            var checkId = -1
            for (i in 0 until size) {
                val chip = createTagTextView(dataSourceList[i], i)
                group.addViewInLayout(chip)
                if (currentPosition == i) {
                    checkId = chip.id
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
            group.isSingleSelection = true
            if (checkId != -1) {
                group.check(checkId)
            }
        }
    }

    /**
     * 创建每一个子项
     */
    private fun createTagTextView(tag: String, position: Int): Chip {
        val chipText = View.inflate(context, getChipLayout(), null) as Chip
        chipText.text = tag
        chipText.id = ViewUtils.generateViewId()
        dataList[chipText.id] = listOf(position, tag)
        return chipText
    }

    open fun getChipLayout() = R.layout._xpopup_chip_filter

    override fun destroy() {
        chipGroup?.setOnCheckedChangeListener(null)
        cancelListener = null
        confirmListener = null
        rootView = null
        tv_cancel = null
        tv_confirm = null
        chipGroup = null
        clearData()
        dataList.clear()
        super.destroy()
    }
}