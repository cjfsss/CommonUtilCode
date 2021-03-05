package com.cjf.base.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cjf.base.picture.GlideEngine
import com.cjf.base.view.IFragmentActivity
import com.cjf.base.view.IViewLoading
import com.cjf.thread.extension.postOnMain
import com.cjf.util.extension.clearAllClick
import com.cjf.util.listener.OnCompressToLubanResultListener
import com.cjf.util.utils.LubanCompressManager
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

/**
 * Description:
 * Create by dance, at 2019/5/16
 */
abstract class BaseActivity : AppCompatActivity(), IFragmentActivity, IViewLoading {

    private var mProgressDialog: LoadingPopupView? = null

    private val mXPopupBuilder: XPopup.Builder by lazy {
        XPopup.Builder(this)
    }

    private val adapterList by lazy {
        ArrayList<RecyclerView.Adapter<*>>()
    }

    protected fun <T : RecyclerView.ViewHolder, A : RecyclerView.Adapter<T>> bindAdapter(adapter: A): A {
        adapterList.add(adapter)
        return adapter
    }

//    protected fun <T, VH : BaseViewHolder, A : BaseQuickAdapter<T, VH>> bindAdapter(adapter: A): A {
//        adapterList.add(adapter)
//        return adapter
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        val decorView = window.decorView
        initView(decorView, savedInstanceState)
        initData(decorView, savedInstanceState)
    }

    /**
     * 获取Bundle
     *
     * @return 返回Intent中的Bundle
     */
    override fun getBundle(): Bundle? {
        val intent = intent ?: return null
        return intent.getBundleExtra("bundle")
    }

    override fun getBaseActivity(): Activity? {
        return this
    }

    override fun showLoading(title: String, isDismissOnBackPressed: Boolean,
                             isDismissOnTouchOutside: Boolean) {
        mProgressDialog = mXPopupBuilder.dismissOnBackPressed(isDismissOnBackPressed)
                .dismissOnTouchOutside(isDismissOnTouchOutside).asLoading()
        mProgressDialog?.setTitle(title)?.show()
    }

    override fun hideLoading() {
        postOnMain {
            mProgressDialog?.dismiss()
        }
    }

    override fun onDestroy() {
        for (adapter in adapterList) {
            if (adapter is BaseQuickAdapter<*, *>) {
                adapter.clearAllClick()
            }
        }
        adapterList.clear()
        hideLoading()
        super.onDestroy()
    }

    open fun compressImagePath(pathList: List<String>, listener: OnCompressToLubanResultListener) {
        LubanCompressManager.builder()
                .imageEngine(GlideEngine.create())
                .isCompress(true)// 是否压缩
                .cutOutQuality(80)// 裁剪输出质量 默认100
                .minimumCompressSize(500)// 小于多少kb的图片不压缩
                .build(listener)
                .compressImagePath(this, pathList)
    }
}