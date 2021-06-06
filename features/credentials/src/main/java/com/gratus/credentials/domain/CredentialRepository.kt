package com.gratus.credentials.domain

import com.gratus.core.domain.local.UserEntity

interface CredentialRepository {
    suspend fun getFindUser(email: String, password: String): UserEntity?
    suspend fun insertCheck(userEntity: UserEntity): Boolean
}
