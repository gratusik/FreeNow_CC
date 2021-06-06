package com.gratus.credentials.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gratus.core.util.network.Resource
import com.gratus.credentials.data.mapper.CredToEntityMapper
import com.gratus.credentials.domain.CredValidModel
import com.gratus.credentials.interactors.LocalCacheUseCase
import com.gratus.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentSignUpViewModel @Inject constructor(
    private var localCacheUseCase: LocalCacheUseCase
) : BaseViewModel() {
    var credValidModel: CredValidModel = CredValidModel()
    var signUpLiveData: MutableLiveData<Resource<Boolean>> =
        MutableLiveData<Resource<Boolean>>()

    fun hitSignUp() {
        if (credValidModel.isEmailValid() &&
            credValidModel.isPasswordValid() &&
            credValidModel.isNameValid() &&
            credValidModel.isPhoneValid() &&
            credValidModel.isRetypeValid()
        ) {
            signUpLiveData.value = Resource.loading()
            viewModelScope.launch(Dispatchers.IO) {
                val insert: Boolean = localCacheUseCase.execute(
                    CredToEntityMapper().map(credValidModel)!!
                )
                if (insert) {
                    signUpLiveData.postValue(
                        Resource.success(insert)
                    )
                } else {
                    signUpLiveData.value = Resource.error("", null)
                }
            }
        } else {
            signUpLiveData.value = Resource.error("", null)
        }
    }
}
