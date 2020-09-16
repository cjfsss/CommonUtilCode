package com.cjf.util.extension

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.lxj.xpopup.interfaces.XPopupImageLoader
import java.io.File


class GlideImageLoader(var placeholder: Int = 0) : XPopupImageLoader {
    override fun loadImage(
            position: Int,
            url: Any,
            imageView: ImageView
    ) {
        Glide.with(imageView).load(url)
                .apply(RequestOptions().placeholder(placeholder).override(Target.SIZE_ORIGINAL))
                .into(imageView)
    }

    override fun getImageFile(
            context: Context,
            uri: Any
    ): File? {
        try {
            return Glide.with(context).downloadOnly().load(uri).submit().get()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}