package com.gratus.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gratus.core.data.cache.AppPreferencesHelper
import com.gratus.core.util.network.AppInterceptor
import com.gratus.core.util.networkManager.NetworkOnlineCheck
import javax.inject.Inject

abstract class BaseFragmentViewModel<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes
    private val layoutId: Int
) : Fragment() {

    @Inject
    lateinit var prefs: AppPreferencesHelper

    @Inject
    lateinit var mInterceptor: AppInterceptor

    @Inject
    lateinit var networkOnlineCheck: NetworkOnlineCheck

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var binding: B

    lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        onInitDataBinding(savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitDependencyInjection()
        onInitViewModel()
    }

    abstract fun onInitViewModel()
    abstract fun onInitDependencyInjection()
    abstract fun onInitDataBinding(savedInstanceState: Bundle?)

    fun isNetworkConnected(): Boolean {
        return networkOnlineCheck.isNetworkOnline
    }

    fun getPreferences(): AppPreferencesHelper {
        return prefs
    }
}
