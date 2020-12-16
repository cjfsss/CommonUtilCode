package com.cjf.base.delegate.lifecycle

import androidx.annotation.MainThread
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.cjf.base.delegate.fragment.FragmentDelegate

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
inline fun <reified VM : ViewModel> FragmentDelegate.viewModels(
        noinline ownerProducer: () -> ViewModelStoreOwner = { getFragment() },
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = getFragment().createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> FragmentDelegate.activityViewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = getFragment().createViewModelLazy(VM::class, { getActivity().viewModelStore },
        factoryProducer ?: { getActivity().defaultViewModelProviderFactory })
