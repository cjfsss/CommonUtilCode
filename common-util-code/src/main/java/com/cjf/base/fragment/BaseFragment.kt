package com.cjf.base.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ActivityUtils
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
abstract class BaseFragment : Fragment(), IFragmentActivity, IViewLoading {

    private var mProgressDialog: LoadingPopupView? = null

    private val mXPopupBuilder: XPopup.Builder by lazy {
        XPopup.Builder(getBaseActivity())
    }

    private val adapterList by lazy {
        ArrayList<RecyclerView.Adapter<*>>()
    }

    protected fun <T : RecyclerView.ViewHolder, A : RecyclerView.Adapter<T>> bindAdapter(adapter: A): A {
        adapterList.add(adapter)
        return adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
        initData(view, savedInstanceState)
    }

    /**
     * 获取Bundle
     *
     * @return 返回Intent中的Bundle
     */
    override fun getBundle(): Bundle? {
        return arguments
    }

    override fun getBaseActivity(): Activity? {
        if (activity == null) {
            return ActivityUtils.getTopActivity()
        }
        return activity
    }

    override fun showLoading(title: String, isDismissOnBackPressed: Boolean, isDismissOnTouchOutside: Boolean) {
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
                .compressImagePath(requireActivity(), pathList)
    }
}