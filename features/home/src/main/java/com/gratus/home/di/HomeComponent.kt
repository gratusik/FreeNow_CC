package com.gratus.home.di

import com.gratus.core.di.component.CoreComponent
import com.gratus.core.di.scopes.FeatureScope
import com.gratus.home.presentation.*
import dagger.Component

@FeatureScope
@Component(
    modules = [
        HomeModule::class
    ],
    dependencies = [CoreComponent::class]
)
interface HomeComponent {

    fun inject(homeActivity: HomeActivity)
    fun inject(fragmentHome: FragmentHome)
    fun inject(fragmentPlaces: FragmentPlaces)
    fun inject(fragmentPlaces: FragmentRide)
    fun inject(fragmentRiding: FragmentRiding)
}
