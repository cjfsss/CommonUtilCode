package com.cjf.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cjf.ui.text.ExpandTextView
import com.cjf.ui.text.ExpandTextView.DefaultExpandCallback
import com.cjf.util.extension.find
import com.cjf.util.extension.start

/**
 * <p>Title: MainActivity </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/10/28 13:41
 * @version : 1.0
 */
class MainActivity: AppCompatActivity() {
    private var mExpandTextView: ExpandTextView? = null

    private val binding1 by find<View>(R.id.mExpandTextView)
    private val binding2 by find<View>(window.decorView,R.id.mExpandTextView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2?.findViewById<View>(R.id.mExpandTextView)
        binding1?.findViewById<View>(R.id.mExpandTextView)
//        val binding = com.cjf.util.extension.binding<View>(window.decorView,R.id.mExpandTextView)
//        binding.value?.findViewById<View>(R.id.mExpandTextView)
        setContentView(R.layout.activity_main)
        mExpandTextView = findViewById<View>(R.id.mExpandTextView) as ExpandTextView
        mExpandTextView!!.setContentText("方案：", "分布式能力打通多设备互助的性能与数据壁垒，HarmonyOS 应用定义更灵活的原子服务，面向多设备的 IDE 支持一站式HarmonyOS 应用开发。",
                false, DefaultExpandCallback(mExpandTextView!!))
        start<NewActivity>(Pair("id",1))
    }
}