package com.gratus.freenow_cc.di

import com.gratus.core.di.component.CoreComponent
import com.gratus.core.di.scopes.FeatureScope
import com.gratus.freenow_cc.SplashActivity
import dagger.Component

@FeatureScope
@Component(
    dependencies = [CoreComponent::class]
)
interface SplashComponent {

    fun inject(splashActivity: SplashActivity)
}
