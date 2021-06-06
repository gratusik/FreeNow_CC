package com.gratus.credentials.domain

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.gratus.credentials.R
import java.io.Serializable

class CredValidModel() : BaseObservable(), Serializable {
    var name: String? = null
    var retypePassword: String? = null
    var password: String? = null
    var email: String? = null
    var phone: String? = null

    var nameError: Int? = null
    var retypePasswordError: Int? = null
    var passwordError: Int? = null
    var emailError: Int? = null
    var phoneError: Int? = null

    private val nameTextWatcher: TextWatcher? = null
    private val retypePasswordTextWatcher: TextWatcher? = null
    private val passwordTextWatcher: TextWatcher? = null
    private val emailTextWatcher: TextWatcher? = null
    private val phoneTextWatcher: TextWatcher? = null

    var nameChange: Boolean = false
    var retypePasswordChange: Boolean = false
    var passwordChange: Boolean = false
    var emailChange: Boolean = false
    var phoneChange: Boolean = false

    var enableButton: Boolean = false

    fun isEmailValid(): Boolean {
        return if (email != null) {
            if (Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()) {
                emailChange = false
                emailError = (
                    R.string.email_none
                    )
                notifyChange()
                return true
            } else {
                emailChange = true
                emailError = (R.string.email_empty)
                notifyChange()
                return false
            }
        } else {
            emailChange = true
            emailError = (R.string.email_empty)
            notifyChange()
            return false
        }
    }

    fun isNameValid(): Boolean {
        return if (name != null) {
            if (name!!.length > 3) {
                nameChange = false
                nameError = (R.string.name_none)
                notifyChange()
                return true
            } else {
                nameChange = true
                nameError = (R.string.name_empty)
                notifyChange()
                return false
            }
        } else {
            nameChange = true
            nameError = (R.string.name_empty)
            notifyChange()
            return false
        }
    }

    fun isRetypeValid(): Boolean {
        return if (retypePassword != null) {
            return if (retypePassword == password) {
                retypePasswordChange = false
                retypePasswordError = (R.string.password_none)
                notifyChange()
                true
            } else {
                retypePasswordChange = true
                retypePasswordError = (R.string.password_match)
                notifyChange()
                false
            }
        } else {
            retypePasswordChange = true
            retypePasswordError = (R.string.password_empty)
            notifyChange()
            return false
        }
    }

    fun isPasswordValid(): Boolean {
        return if (password != null) {
            if (password!!.length >= 8) {
                passwordChange = false
                passwordError = (R.string.password_none)
                notifyChange()
                return true
            } else {
                passwordChange = true
                passwordError = (R.string.password_empty)
                notifyChange()
                return false
            }
        } else {
            passwordChange = true
            passwordError = (R.string.password_empty)
            notifyChange()
            return false
        }
    }

    fun isPhoneValid(): Boolean {
        return if (phone != null) {
            if (phone!!.length == 10) {
                phoneChange = false
                phoneError = (R.string.phone_none)
                notifyChange()
                return true
            } else {
                phoneChange = true
                phoneError = (R.string.phone_empty)
                notifyChange()
                return false
            }
        } else {
            phoneChange = true
            phoneError = (R.string.phone_empty)
            notifyChange()
            return false
        }
    }

    @Bindable
    fun getNameTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                name = (s.toString())
                isNameValid()
            }
        }
    }

    @Bindable
    fun getEmailTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                email = (s.toString())
                isEmailValid()
            }
        }
    }

    @Bindable
    fun getPhoneTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                phone = (s.toString())
                isPhoneValid()
            }
        }
    }

    @Bindable
    fun getRetypePasswordTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                retypePassword = (s.toString())
                isRetypeValid()
            }
        }
    }

    @Bindable
    fun getPasswordTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                password = (s.toString())
                isPasswordValid()
            }
        }
    }

    fun getEnableButton(b: Boolean) {
        enableButton = b
        notifyChange()
    }
}
