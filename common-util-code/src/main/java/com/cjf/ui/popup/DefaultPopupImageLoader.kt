package com.cjf.ui.popup

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.cjf.util.R
import com.lxj.xpopup.interfaces.XPopupImageLoader
import java.io.File

/**
 * <p>Title: PopupImageLoader </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/8/29 18:06
 * @version : 1.0
 */
class DefaultPopupImageLoader : XPopupImageLoader {

    override fun loadImage(position: Int, url: Any, imageView: ImageView) {
        //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
        Glide.with(imageView).load(url).apply(
            RequestOptions().placeholder(R.drawable.picture_image_placeholder)
                .override(Target.SIZE_ORIGINAL)
        ).into(imageView)
    }

    override fun getImageFile(context: Context, uri: Any): File? {
        try {
            return Glide.with(context).downloadOnly().load(uri).submit().get()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}