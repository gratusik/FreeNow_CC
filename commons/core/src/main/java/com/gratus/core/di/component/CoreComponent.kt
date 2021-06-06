package com.gratus.core.di.component

import android.app.Application
import android.content.Context
import com.gratus.core.RoomModule
import com.gratus.core.data.local.dao.UserDao
import com.gratus.core.di.modules.AppPrefModule
import com.gratus.core.di.modules.ContextModule
import com.gratus.core.di.modules.InternetModule
import com.gratus.core.di.modules.NetworkModule
import com.gratus.core.util.network.AppInterceptor
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        RoomModule::class,
        AppPrefModule::class,
        InternetModule::class
    ]
)
interface CoreComponent {
    fun inject(application: Application)
    fun context(): Context
    fun retrofit(): Retrofit
    fun userDao(): UserDao
    fun interceptor(): AppInterceptor
}
