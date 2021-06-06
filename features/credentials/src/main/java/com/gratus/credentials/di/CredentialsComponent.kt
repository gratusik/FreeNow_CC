package com.gratus.credentials.di

import com.gratus.core.di.component.CoreComponent
import com.gratus.core.di.scopes.FeatureScope
import com.gratus.credentials.presentation.CredentialsActivity
import com.gratus.credentials.presentation.FragmentSignIn
import com.gratus.credentials.presentation.FragmentSignUp
import dagger.Component

@FeatureScope
@Component(
    modules = [
        CredentialsModule::class
    ],
    dependencies = [CoreComponent::class]
)
interface CredentialsComponent {

    //    @Component.Factory
//    interface Factory {
//        // Takes an instance of AppComponent when creating
//        // an instance of LoginComponent
//        fun create(appComponent: AppComponent): LoginComponent
//    }
    fun inject(credentialsActivity: CredentialsActivity)
    fun inject(fragmentSignIn: FragmentSignIn)
    fun inject(fragmentSignUp: FragmentSignUp)
}
