package com.gratus.freenow_cc

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.gratus.core.BaseApplication
import com.gratus.credentials.presentation.CredentialsActivity
import com.gratus.freenow_cc.databinding.ActivitySplashBinding
import com.gratus.freenow_cc.di.DaggerSplashComponent
import com.gratus.home.presentation.HomeActivity
import com.gratus.ui.base.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(
    layoutId = R.layout.activity_splash
) {
    private val splashDisplayLength: Long = 1000

    override fun onInitDataBinding() {
        networkCheck(binding.parentSplash, null)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                if (getPreferences().getPassword() != null &&
                    getPreferences().getUsername() != null
                ) {
                    intentHomeActivity()
                } else {
                    intentMainActivity()
                }
            },
            splashDisplayLength
        )
    }

    private fun intentHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    // move to Main page after 1 second delay
    private fun intentMainActivity() {
        val intent = Intent(this, CredentialsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onInitDependencyInjection() {
        DaggerSplashComponent
            .builder()
            .coreComponent(BaseApplication.coreComponent)
            .build()
            .inject(this)
    }
}
