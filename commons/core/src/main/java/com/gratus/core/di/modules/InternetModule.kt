package com.gratus.core.di.modules

import android.content.Context
import com.gratus.core.util.networkManager.NetworkOnlineCheck
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Check internet connection module
@Module
class InternetModule {
    @Provides
    @Singleton
    fun provideNetworkHelper(context: Context): NetworkOnlineCheck {
        return NetworkOnlineCheck(context)
    }
}
