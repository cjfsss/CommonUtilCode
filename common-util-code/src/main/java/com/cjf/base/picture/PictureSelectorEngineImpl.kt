package com.cjf.base.picture

import android.annotation.SuppressLint
import android.util.Log
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.engine.PictureSelectorEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener

/**
 * <p>Title: PictureSelectorEngineImpl </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/7/18 16:54
 * @version : 1.0
 */
class PictureSelectorEngineImpl : PictureSelectorEngine {

    companion object {
        private const val TAG: String = "PictureSelectorEngineImpl"
    }

    override fun createEngine(): ImageEngine? {
        // TODO 这种情况是内存极度不足的情况下，比如开启开发者选项中的不保留活动或后台进程限制，导致ImageEngine被回收
        // 重新创建图片加载引擎
        return GlideEngine.create()
    }

    override fun getResultCallbackListener(): OnResultCallbackListener<LocalMedia?>? {
        return object : OnResultCallbackListener<LocalMedia?> {
            @SuppressLint("LongLogTag")
            override fun onResult(result: List<LocalMedia?>) {
                // TODO 这种情况是内存极度不足的情况下，比如开启开发者选项中的不保留活动或后台进程限制，导致OnResultCallbackListener被回收
                // 可以在这里进行一些补救措施，通过广播或其他方式将结果推送到相应页面，防止结果丢失的情况
                Log.i(TAG, "onResult:" + result.size)
            }

            @SuppressLint("LongLogTag")
            override fun onCancel() {
                Log.i(TAG, "PictureSelector onCancel")
            }
        }
    }
}