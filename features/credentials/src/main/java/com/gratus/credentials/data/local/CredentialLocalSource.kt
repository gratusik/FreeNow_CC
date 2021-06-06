package com.gratus.browse.data.local

import com.gratus.core.domain.local.UserEntity

interface CredentialLocalSource {
    suspend fun getFindUser(email: String, password: String): UserEntity?
    suspend fun insertCheck(userEntity: UserEntity): Boolean
}
