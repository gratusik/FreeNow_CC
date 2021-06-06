package com.gratus.credentials.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gratus.core.di.factory.ViewModelFactory
import com.gratus.core.di.key.ViewModelKey
import com.gratus.credentials.presentation.FragmentSignInViewModel
import com.gratus.credentials.presentation.FragmentSignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CredentialsFeatureModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FragmentSignInViewModel::class)
    internal abstract fun bindFragmentSignInViewModel(viewModel: FragmentSignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FragmentSignUpViewModel::class)
    internal abstract fun bindFragmentSignUpViewModel(viewModel: FragmentSignUpViewModel): ViewModel
}
