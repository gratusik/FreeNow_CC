package com.gratus.core

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.gratus.core.di.component.CoreComponent
import com.gratus.core.di.component.DaggerCoreComponent
import com.gratus.core.di.modules.ContextModule
import org.acra.ACRA
import org.acra.ReportField
import org.acra.annotation.AcraCore
import org.acra.annotation.AcraMailSender

@AcraMailSender(mailTo = "agratus@gmail.com")
@AcraCore(reportContent = [ReportField.STACK_TRACE, ReportField.LOGCAT])
class BaseApplication : Application() {
    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initCoreDependencyInjection()
        Fresco.initialize(this)
        ACRA.init(this)
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
