package com.gratus.home.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gratus.core.di.factory.ViewModelFactory
import com.gratus.core.di.key.ViewModelKey
import com.gratus.home.presentation.FragmentHomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeFeatureModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FragmentHomeViewModel::class)
    internal abstract fun bindFragmentHomeViewModel(viewModel: FragmentHomeViewModel): ViewModel
}
