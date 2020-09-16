package com.cjf.base.fragment

import com.cjf.base.picture.GlideEngine
import com.cjf.util.R
import com.cjf.util.toast.ToastX
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.yanzhenjie.permission.runtime.Permission

/**
 * <p>Title: OrcActivity </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/7/22 21:34
 * @version : 1.0
 */
abstract class PictureSelectorFragment : AndPermissionFragment(), OnResultCallbackListener<LocalMedia> {

    open fun gotoPictureSelector() {
        gotoPictureSelectorWithPermissionCheck()
    }

    private fun gotoPictureSelectorWithPermissionCheck() {
        val permission = Permission.Group.CAMERA + Permission.Group.STORAGE
        requestPermission(permission.asList().toTypedArray())
    }

    override fun onGrantedSuccess() {
        gotoCamera()
    }

    open fun gotoCamera(selectionMode:Int = PictureConfig.SINGLE) {
        PictureSelector.create(this)
            .themeStyle(R.style.picture_QQ_style)
            .imageEngine(GlideEngine.create()) // Please refer to the Demo GlideEngine.java
            .selectionMode(selectionMode)// 多选 or 单选
            .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
            .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
            .imageSpanCount(4)// 每行显示个数
            .isPreviewImage(true)// 是否可预览图片
            .isCamera(true)// 是否显示拍照按钮
            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
            .isCompress(true)// 是否压缩
            .synOrAsy(false)//同步true或异步false 压缩 默认同步
            .cutOutQuality(90)// 裁剪输出质量 默认100
            .minimumCompressSize(100)// 小于多少kb的图片不压缩
            .forResult(this)
    }

    override fun onCancel() {
        ToastX.showShort("已取消")
    }

}