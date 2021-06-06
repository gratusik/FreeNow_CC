package com.gratus.credentials.presentation

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gratus.core.BaseApplication
import com.gratus.core.util.network.ResourceState
import com.gratus.credentials.R
import com.gratus.credentials.databinding.FragmentSignInBinding
import com.gratus.credentials.di.DaggerCredentialsComponent
import com.gratus.home.presentation.HomeActivity
import com.gratus.ui.base.BaseFragmentViewModel
import com.pranavpandey.android.dynamic.toasts.DynamicToast

class FragmentSignIn : BaseFragmentViewModel<FragmentSignInBinding, FragmentSignInViewModel>(
    layoutId = R.layout.fragment_sign_in
) {
    override fun onInitDependencyInjection() {
        // DaggerLoginComponent.factory().create(appComponent).inject(this)
        DaggerCredentialsComponent
            .builder()
            .coreComponent(BaseApplication.coreComponent)
            .build()
            .inject(this)
    }

    override fun onInitViewModel() {
        viewModel =
            ViewModelProvider(this, factory)
                .get(FragmentSignInViewModel::class.java)
    }

    override fun onInitDataBinding(savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        binding.signUpTv.setOnClickListener {
            view?.let { view ->
                Navigation.findNavController(view)
                    .navigate(R.id.action_navigation_sign_in_to_navigation_sign_up)
            }
        }
        observeSignIn()
    }

    private fun observeSignIn() {
        viewModel.userEntityLiveData.observe(this) {
            when (it.status) {
                ResourceState.LOADING -> {
                }
                ResourceState.SUCCESS -> {
                    getPreferences().setUsername(it.data!!.email)
                    getPreferences().setPassword(it.data!!.password)
                    intentHomeActivity()
                }
                ResourceState.ERROR -> {
                    DynamicToast.makeError(
                        requireContext(),
                        resources.getString(R.string.login_unsuccessful)
                    ).show()
                }
                else -> {
                }
            }
        }
    }

    private fun intentHomeActivity() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}
