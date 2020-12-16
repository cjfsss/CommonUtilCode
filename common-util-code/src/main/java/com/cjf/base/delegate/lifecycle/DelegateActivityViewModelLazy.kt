package com.cjf.base.delegate.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.cjf.base.delegate.Delegate

/**
 * <p>Title: DelegateViewModelLazy </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/12/15 16:05
 * @version : 1.0
 */

@MainThread
inline fun <reified VM : ViewModel> Delegate.viewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val activity = getActivity()
    val factoryPromise = factoryProducer ?: {
        activity.defaultViewModelProviderFactory
    }
    return ViewModelLazy(VM::class, { activity.viewModelStore }, factoryPromise)
}

