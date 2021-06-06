package com.gratus.core.data.cache

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.gratus.core.util.CoreConstants.PrefConstant.APP_PREF_NAME
import com.gratus.core.util.CoreConstants.PrefConstant.PASSWORD
import com.gratus.core.util.CoreConstants.PrefConstant.USERNAME
import javax.inject.Inject

// Shared Preferences
class AppPreferencesHelper @Inject constructor(context: Context) : AppPrefHelper {
    private var mPrefs: SharedPreferences =
        context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE)

    fun isClear(): Boolean {
        return false
    }

    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    fun setClear(clear: Boolean) {
        val editor = mPrefs.edit()
        editor.clear()
        editor.apply()
    }

    // to get username
    override fun getUsername(): String? {
        return mPrefs.getString(USERNAME, null)
    }

    // to set username
    override fun setUsername(username: String) {
        mPrefs.edit().putString(USERNAME, username).apply()
    }

    // to get password
    override fun getPassword(): String? {
        return mPrefs.getString(PASSWORD, null)
    }

    // to set password
    override fun setPassword(password: String) {
        mPrefs.edit().putString(PASSWORD, password).apply()
    }
}
