package com.gratus.core

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.gratus.core.di.component.CoreComponent
import com.gratus.core.di.component.DaggerCoreComponent
import com.gratus.core.di.modules.ContextModule

class BaseApplication : Application() {
    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initCoreDependencyInjection()
        Fresco.initialize(this)
    }

    /**
     * Initialize core dependency injection component.
     */
    private fun initCoreDependencyInjection() {
        coreComponent = DaggerCoreComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
        coreComponent.inject(this)
    }
}
