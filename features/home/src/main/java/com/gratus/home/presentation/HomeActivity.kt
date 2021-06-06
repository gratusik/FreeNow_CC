package com.gratus.home.presentation

import com.gratus.core.BaseApplication
import com.gratus.home.R
import com.gratus.home.databinding.ActivityHomeBinding
import com.gratus.home.di.DaggerHomeComponent
import com.gratus.ui.base.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding>(
    layoutId = R.layout.activity_home
) {
    override fun onInitDataBinding() {
        networkCheck(binding.parentHome, null)
    }

    override fun onInitDependencyInjection() {
        DaggerHomeComponent
            .builder()
            .coreComponent(BaseApplication.coreComponent)
            .build()
            .inject(this)
    }
}
