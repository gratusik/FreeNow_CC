package com.gratus.credentials.presentation

import com.gratus.core.BaseApplication
import com.gratus.credentials.R
import com.gratus.credentials.databinding.ActivityCredentialsBinding
import com.gratus.credentials.di.DaggerCredentialsComponent
import com.gratus.ui.base.BaseActivity

class CredentialsActivity : BaseActivity<ActivityCredentialsBinding>(
    layoutId = R.layout.activity_credentials
) {
    override fun onInitDataBinding() {
        networkCheck(binding.parentMain, null)
    }

    override fun onInitDependencyInjection() {
        DaggerCredentialsComponent
            .builder()
            .coreComponent(BaseApplication.coreComponent)
            .build()
            .inject(this)
    }
}
