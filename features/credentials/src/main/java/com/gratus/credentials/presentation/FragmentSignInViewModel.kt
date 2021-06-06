package com.gratus.credentials.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gratus.core.domain.local.UserEntity
import com.gratus.core.util.network.Resource
import com.gratus.credentials.domain.CredValidModel
import com.gratus.credentials.interactors.LocalCacheUseCase
import com.gratus.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentSignInViewModel @Inject constructor(
    private var localCacheUseCase: LocalCacheUseCase
) : BaseViewModel() {
    var credValidModel: CredValidModel = CredValidModel()
    var userEntityLiveData: MutableLiveData<Resource<UserEntity>> =
        MutableLiveData<Resource<UserEntity>>()

    fun hitSignIn() {
        if (credValidModel.isEmailValid() && credValidModel.isPasswordValid()) {
            userEntityLiveData.value = Resource.loading()
            viewModelScope.launch(Dispatchers.IO) {
                val userEntity: UserEntity? = localCacheUseCase.execute(
                    credValidModel.email!!,
                    credValidModel.password!!
                )
                if (userEntity != null) {
                    userEntityLiveData.postValue(
                        Resource.success(userEntity)
                    )
                } else {
                    userEntityLiveData.value = Resource.error("", null)
                }
            }
        } else {
            userEntityLiveData.value = Resource.error("", null)
        }
    }
}
