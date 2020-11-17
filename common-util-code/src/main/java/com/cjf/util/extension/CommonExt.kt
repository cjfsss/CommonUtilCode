package com.cjf.util.extension

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.TimeUtils
import com.cjf.util.UtilX
import com.cjf.util.path.PathManager
import com.cjf.util.utils.ResUtils
import com.cjf.util.utils.ViewUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.Serializable
import java.util.*
import kotlin.concurrent.thread

// 反射获取
inline fun <reified T, R> Any.getField(filed: String): R {
    val fieldJava = T::class.java.getDeclaredField(filed)
    fieldJava.isAccessible = true
    @Suppress("UNCHECKED_CAST")
    return fieldJava.get(this) as R
}

fun Context.sp2px(size: Float): Int {
    return ViewUtils.sp2px(this, size).toInt()
}

fun Context.dp2px(size: Float): Int {
    return ViewUtils.dp2px(this, size).toInt()
}

fun Fragment.dp2px(dpValue: Float): Int {
    return context!!.dp2px(dpValue)
}

fun Fragment.sp2px(dpValue: Float): Int {
    return context!!.sp2px(dpValue)
}

fun RecyclerView.ViewHolder.dp2px(dpValue: Float): Int {
    return itemView.dp2px(dpValue)
}

fun RecyclerView.ViewHolder.sp2px(dpValue: Float): Int {
    return itemView.sp2px(dpValue)
}

fun View.sp2px(size: Float): Int {
    return context.sp2px(size)
}

fun View.dp2px(size: Float): Int {
    return context.dp2px(size)
}

fun Context.isTablet(): Boolean {
    return ResUtils.isTablet(this)
}

fun Fragment.isTablet(): Boolean {
    return ResUtils.isTablet(context)
}

fun View.isTablet(): Boolean {
    return ResUtils.isTablet(context)
}

/** 动态创建Drawable **/
fun Context.createDrawable(color: Int = Color.TRANSPARENT, radius: Float = 0f,
                           strokeColor: Int = Color.TRANSPARENT, strokeWidth: Int = 0,
                           enableRipple: Boolean = true,
                           rippleColor: Int = Color.parseColor("#88999999"),
                           gradientStartColor: Int = 0, gradientEndColor: Int = 0, gradientOrientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT): Drawable {
    val content = GradientDrawable().apply {
        cornerRadius = radius
        setStroke(strokeWidth, strokeColor)
        gradientType = GradientDrawable.LINEAR_GRADIENT
        if (gradientStartColor != 0 || gradientEndColor != 0) {
            if (Build.VERSION.SDK_INT >= 16) {
                orientation = gradientOrientation
                colors = intArrayOf(gradientStartColor, gradientEndColor)
            }
        } else {
            setColor(color)
        }
    }
    if (Build.VERSION.SDK_INT >= 21 && enableRipple) {
        return RippleDrawable(ColorStateList.valueOf(rippleColor), content, null)
    }
    return content
}

fun Fragment.createDrawable(color: Int = Color.TRANSPARENT, radius: Float = 0f,
                            strokeColor: Int = Color.TRANSPARENT, strokeWidth: Int = 0,
                            enableRipple: Boolean = true,
                            rippleColor: Int = Color.parseColor("#88999999"),
                            gradientStartColor: Int = 0, gradientEndColor: Int = 0, gradientOrientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT): Drawable {
    if (context == null) {
        return UtilX.getApplication().createDrawable(color, radius, strokeColor, strokeWidth, enableRipple, rippleColor, gradientStartColor = gradientStartColor,
                gradientEndColor = gradientEndColor, gradientOrientation = gradientOrientation)
    }
    return context!!.createDrawable(color, radius, strokeColor, strokeWidth, enableRipple, rippleColor, gradientStartColor = gradientStartColor,
            gradientEndColor = gradientEndColor, gradientOrientation = gradientOrientation)
}

fun View.createDrawable(color: Int = Color.TRANSPARENT, radius: Float = 0f,
                        strokeColor: Int = Color.TRANSPARENT, strokeWidth: Int = 0,
                        enableRipple: Boolean = true,
                        rippleColor: Int = Color.parseColor("#88999999"),
                        gradientStartColor: Int = 0, gradientEndColor: Int = 0, gradientOrientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT): Drawable {
    if (context == null) {
        return UtilX.getApplication().createDrawable(color, radius, strokeColor, strokeWidth, enableRipple, rippleColor, gradientStartColor = gradientStartColor,
                gradientEndColor = gradientEndColor, gradientOrientation = gradientOrientation)
    }
    return context!!.createDrawable(color, radius, strokeColor, strokeWidth, enableRipple, rippleColor, gradientStartColor = gradientStartColor,
            gradientEndColor = gradientEndColor, gradientOrientation = gradientOrientation)
}

/**
 * 数组转bundle
 */
@Suppress("UNCHECKED_CAST")
fun Array<out Pair<String, Any?>>.toBundle(): Bundle? {
    return Bundle().apply {
        forEach { it ->
            when (val value = it.second) {
                null -> putSerializable(it.first, null as Serializable?)
                is Int -> putInt(it.first, value)
                is Long -> putLong(it.first, value)
                is CharSequence -> putCharSequence(it.first, value)
                is String -> putString(it.first, value)
                is Float -> putFloat(it.first, value)
                is Double -> putDouble(it.first, value)
                is Char -> putChar(it.first, value)
                is Short -> putShort(it.first, value)
                is Boolean -> putBoolean(it.first, value)
                is Serializable -> putSerializable(it.first, value)
                is Parcelable -> putParcelable(it.first, value)

                is IntArray -> putIntArray(it.first, value)
                is LongArray -> putLongArray(it.first, value)
                is FloatArray -> putFloatArray(it.first, value)
                is DoubleArray -> putDoubleArray(it.first, value)
                is CharArray -> putCharArray(it.first, value)
                is ShortArray -> putShortArray(it.first, value)
                is BooleanArray -> putBooleanArray(it.first, value)

                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> putCharSequenceArray(it.first, value as Array<CharSequence>)
                    value.isArrayOf<String>() -> putStringArray(it.first, value as Array<String>)
                    value.isArrayOf<Parcelable>() -> putParcelableArray(it.first, value as Array<Parcelable>)
                }
            }
        }
    }

}


fun Any.runOnUIThread(action: () -> Unit) {
    ThreadUtils.runOnUiThread(action)
}

/**
 * 将Bitmap保存到相册
 */
fun Bitmap.saveToAlbum(format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG, quality: Int = 100, filename: String = "", callback: ((path: String?, uri: Uri?) -> Unit)? = null) {
    thread {
        try {
            //1. create path
            val dirPath = PathManager.getExternalPictureDir()
            if (dirPath.isNullOrEmpty()) {
                throw IOException("dirPath is ${dirPath}")
            }
            val dirFile = File(dirPath)
            if (!dirFile.exists()) dirFile.mkdirs()
            val ext = when (format) {
                Bitmap.CompressFormat.PNG -> ".png"
                Bitmap.CompressFormat.JPEG -> ".jpg"
                Bitmap.CompressFormat.WEBP -> ".webp"
            }
            val target = File(dirPath, (if (filename.isEmpty()) System.currentTimeMillis().toString() else filename) + ext)
            if (target.exists()) target.delete()
            target.createNewFile()
            //2. save
            compress(format, quality, FileOutputStream(target))
            //3. notify
            MediaScannerConnection.scanFile(UtilX.getApplication(), arrayOf(target.absolutePath),
                    arrayOf("image/$ext")) { path, uri ->
                runOnUIThread {
                    callback?.invoke(path, uri)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            runOnUIThread { callback?.invoke(null, null) }
        }
    }
}

//一天只做一次
fun Any.doOnceInDay(action: () -> Unit) {
    val key = "once_in_day_last_check"
    val today = Date()
    val todayFormat = TimeUtils.date2String(today, "yyyy-MM-dd")
    val last = SPUtils.getInstance().getString(key, "")
    if (last != null && last.isNotEmpty() && last == todayFormat) {
        //说明执行过
        return
    }
    SPUtils.getInstance().put(key, todayFormat)
    action()
}

//第一次启动的时候执行
fun Any.doWhenFirstLaunch(action: () -> Unit) {
    val key = "has_done_first_launch"
    val hasDone = SPUtils.getInstance().getBoolean(key, false)
    if (hasDone) {
        //说明执行过
        return
    }
    SPUtils.getInstance().put(key, true)
    action()
}