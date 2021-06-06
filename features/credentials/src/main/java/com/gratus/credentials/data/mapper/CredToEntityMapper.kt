package com.gratus.credentials.data.mapper

import com.gratus.core.domain.local.UserEntity
import com.gratus.credentials.domain.CredValidModel

class CredToEntityMapper : BaseMapper<CredValidModel, UserEntity>() {
    override fun map(value: CredValidModel): UserEntity? {
        value.run {
            return email?.let {
                name?.let { it1 ->
                    phone?.let { it2 ->
                        password?.let { it3 ->
                            UserEntity(
                                name = it1,
                                password = it3,
                                email = it,
                                phone = it2,
                                loyaltyPoint = 0,
                                freeNowMoney = 0.0
                            )
                        }
                    }
                }
            }
        }
    }
}
