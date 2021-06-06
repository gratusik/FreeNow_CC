package com.gratus.browse.data

import com.gratus.browse.data.local.CredentialLocalSourceImpl
import com.gratus.core.domain.local.UserEntity
import com.gratus.credentials.domain.CredentialRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CredentialRepoImpl(
    private val credentialLocalSourceImpl: CredentialLocalSourceImpl,
) : CredentialRepository {

    override suspend fun getFindUser(email: String, password: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            credentialLocalSourceImpl.getFindUser(email, password)
        }
    }

    override suspend fun insertCheck(userEntity: UserEntity): Boolean {
        return withContext(Dispatchers.IO) {
            credentialLocalSourceImpl.insertCheck(userEntity)
        }
    }
}
