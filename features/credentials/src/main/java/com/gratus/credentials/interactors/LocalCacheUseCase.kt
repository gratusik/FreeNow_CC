package com.gratus.credentials.interactors

import com.gratus.core.domain.local.UserEntity
import com.gratus.credentials.domain.CredentialRepository

class LocalCacheUseCase(private val repo: CredentialRepository) {
    suspend fun execute(email: String, password: String): UserEntity? {
        return repo.getFindUser(email, password)
    }

    suspend fun execute(userEntity: UserEntity): Boolean {
        return repo.insertCheck(userEntity)
    }
}
