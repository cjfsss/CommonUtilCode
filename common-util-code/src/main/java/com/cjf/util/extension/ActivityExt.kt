package com.cjf.util.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils
import com.cjf.util.livedata.LifecycleHandler

/**
 * Description: Activity相关
 * Create by lxj, at 2018/12/7
 */
// 获取Fragment 下标
fun Fragment.getPosition(): Int {
    return arguments?.getInt("position", 0) ?: 0
}

fun Fragment.positionArg(position: Int, vararg params: Pair<String, Any?>): Fragment {
    return position(position, params)
}

// 配置Fragment的下标
fun Fragment.position(position: Int, bundle: Array<out Pair<String, Any?>>? = null): Fragment {
    val mutableList: MutableList<Pair<String, Any?>> = arrayOf(Pair("position", position)).toMutableList()
    if (bundle != null) {
        mutableList.addAll(bundle.toMutableList())
    }
    return bundle(mutableList.toTypedArray())
}

fun Fragment.bundleArg(vararg params: Pair<String, Any?>): Fragment {
    return bundle(params)
}

// 配置Fragment的参数
fun Fragment.bundle(bundle: Array<out Pair<String, Any?>>? = null): Fragment {
    if (bundle != null) {
        arguments = Bundle(bundle.toBundle()!!)
    }
    return this
}

inline fun <reified T : Activity> Fragment.start(vararg params: Pair<String, Any?>) {
    start<T>(params)
}

// 跳转到下一个Activity
inline fun <reified T> Fragment.start(bundle: Array<out Pair<String, Any?>>? = null, flag: Int = -1) {
    var startActivity: Context? = activity
    if (startActivity == null) {
        startActivity = ActivityUtils.getTopActivity()
    }
    if (startActivity == null) {
        startActivity = Utils.getApp()
    }
    val intent = Intent(startActivity, T::class.java).apply {
        if (flag != -1) {
            this.addFlags(flag)
        }
        if (startActivity !is Activity) this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (bundle != null) putExtras(bundle.toBundle()!!)
    }
    if (host != null) {
        startActivity(intent)
    } else {
        startActivity?.startActivity(intent)
    }
}

inline fun <reified T : Activity> Fragment.startForResult(requestCode: Int = -1, vararg params: Pair<String, Any?>) {
    startForResult<T>(params, requestCode)
}

// 跳转到下一个Activity
inline fun <reified T> Fragment.startForResult(bundle: Array<out Pair<String, Any?>>? = null, requestCode: Int = -1, flag: Int = -1) {
    var startActivity: Context? = activity
    if (startActivity == null) {
        startActivity = ActivityUtils.getTopActivity()
    }
    if (startActivity == null) {
        startActivity = Utils.getApp()
    }
    val intent = Intent(startActivity, T::class.java).apply {
        if (flag != -1) {
            this.addFlags(flag)
        }
        if (startActivity !is Activity) this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (bundle != null) putExtras(bundle.toBundle()!!)
    }
    if (host != null) {
        startActivityForResult(intent, requestCode)
    } else {
        startActivity?.startActivity(intent)
    }
}

inline fun <reified T : Activity> Activity.start(vararg params: Pair<String, Any?>) {
    start<T>(params)
}

inline fun <reified T> Activity.start(bundle: Array<out Pair<String, Any?>>? = null, flag: Int = -1) {
    val intent = Intent(this, T::class.java).apply {
        if (flag != -1) {
            this.addFlags(flag)
        }
        if (this !is Activity) {
            this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (bundle != null) putExtras(bundle.toBundle()!!)
    }
    startActivity(intent)
}

inline fun <reified T : Activity> Activity.startForResult(requestCode: Int = -1, vararg params: Pair<String, Any?>) {
    startForResult<T>(params, requestCode)
}

inline fun <reified T> Activity.startForResult(bundle: Array<out Pair<String, Any?>>? = null, requestCode: Int = -1, flag: Int = -1) {
    val intent = Intent(this, T::class.java).apply {
        if (flag != -1) {
            this.addFlags(flag)
        }
        if (this !is Activity) {
            this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (bundle != null) putExtras(bundle.toBundle()!!)
    }
    startActivityForResult(intent, requestCode)
}

inline fun <reified T : Activity> Context.start(vararg params: Pair<String, Any?>) {
    start<T>(params)
}

inline fun <reified T> Context.start(bundle: Array<out Pair<String, Any?>>? = null, flag: Int = -1) {
    val intent = Intent(this, T::class.java).apply {
        if (flag != -1) {
            this.addFlags(flag)
        }
        if (this !is Activity) {
            this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (bundle != null) putExtras(bundle.toBundle()!!)
    }
    startActivity(intent)
}

inline fun <reified T : Activity> View.startActivity(vararg params: Pair<String, Any?>) {
    startActivity<T>(params)
}

inline fun <reified T> View.startActivity(bundle: Array<out Pair<String, Any?>>? = null, flag: Int = -1) {
    context.start<T>(bundle, flag)
}

inline fun <reified T> View.startForResult(bundle: Array<out Pair<String, Any?>>? = null, requestCode: Int = -1, flag: Int = -1) {
    (context as Activity).startForResult<T>(bundle, requestCode, flag)
}

fun Activity.finishResult(bundle: Array<out Pair<String, Any?>>? = null, resultCode: Int = -1) {
    val intent = Intent().apply {
        if (bundle != null) putExtras(bundle.toBundle()!!)
    }
    setResult(resultCode, intent)
    finish()
}

fun Fragment.finishResult(bundle: Array<out Pair<String, Any?>>? = null, resultCode: Int = -1) {
    val intent = Intent().apply {
        if (bundle != null) putExtras(bundle.toBundle()!!)
    }
    requireActivity().setResult(resultCode, intent)
    requireActivity().finish()
}

fun Fragment.finish() {
    requireActivity().finish()
}

fun FragmentActivity.finishDelay(delay: Long = 1) {
    LifecycleHandler(this).postDelayed({ finish() }, delay)
}

//post, postDelay
fun FragmentActivity.post(action: () -> Unit) {
    LifecycleHandler(this).post { action() }
}

fun FragmentActivity.postDelay(delay: Long = 0, action: () -> Unit) {
    LifecycleHandler(this).postDelayed({ action() }, delay)
}
