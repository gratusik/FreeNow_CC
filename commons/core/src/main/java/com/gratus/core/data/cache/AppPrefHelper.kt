package com.gratus.core.data.cache

interface AppPrefHelper {
    fun getUsername(): String?

    fun setUsername(username: String)

    fun getPassword(): String?

    fun setPassword(password: String)
}
