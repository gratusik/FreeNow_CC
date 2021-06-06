package com.gratus.browse.data.local

import com.gratus.core.data.local.dao.UserDao
import com.gratus.core.domain.local.UserEntity
import javax.inject.Inject

class CredentialLocalSourceImpl @Inject constructor(
    private val userDao: UserDao,
) : CredentialLocalSource {
    override suspend fun getFindUser(email: String, password: String): UserEntity? {
        return userDao.getFindUser(email, password)
    }

    override suspend fun insertCheck(userEntity: UserEntity): Boolean {
        return userDao.insertCheck(userEntity)
    }
}
