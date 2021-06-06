package com.gratus.credentials.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gratus.core.BaseApplication
import com.gratus.core.util.network.ResourceState
import com.gratus.credentials.R
import com.gratus.credentials.databinding.FragmentSignUpBinding
import com.gratus.credentials.di.DaggerCredentialsComponent
import com.gratus.ui.base.BaseFragmentViewModel
import com.pranavpandey.android.dynamic.toasts.DynamicToast

class FragmentSignUp : BaseFragmentViewModel<FragmentSignUpBinding, FragmentSignUpViewModel>(
    layoutId = R.layout.fragment_sign_up
) {
    override fun onInitDependencyInjection() {
        DaggerCredentialsComponent
            .builder()
            .coreComponent(BaseApplication.coreComponent)
            .build()
            .inject(this)
    }

    override fun onInitViewModel() {
        viewModel =
            ViewModelProvider(this, factory)
                .get(FragmentSignUpViewModel::class.java)
    }

    override fun onInitDataBinding(savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        binding.backImg.setOnClickListener(
            View.OnClickListener { view ->
                onBackStack(view)
            }
        )
        observeSignUp()
    }

    private fun onBackStack(view: View) {
        Navigation.findNavController(view).popBackStack()
    }

    private fun observeSignUp() {
        viewModel.signUpLiveData.observe(this) {
            when (it.status) {
                ResourceState.LOADING -> {
                }
                ResourceState.SUCCESS -> {
                    onBackStack(requireView())
                    DynamicToast.makeSuccess(
                        requireContext(),
                        resources.getString(R.string.sign_up_successful)
                    ).show()
                }
                ResourceState.ERROR -> {
                    DynamicToast.makeError(
                        requireContext(),
                        resources.getString(R.string.sign_up_unsuccessful)
                    ).show()
                }
                else -> {
                }
            }
        }
    }
}
