package com.gratus.core.di.modules

import com.gratus.core.data.cache.AppPrefHelper
import com.gratus.core.data.cache.AppPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppPrefModule {
    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): AppPrefHelper {
        return appPreferencesHelper
    }
}
