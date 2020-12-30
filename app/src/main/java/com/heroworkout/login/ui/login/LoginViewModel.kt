package com.heroworkout.login.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.heroworkout.login.data.LoginRepository
import com.heroworkout.login.data.Result

import com.heroworkout.login.R

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_email)
        } else if (!isLengthValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password_length)
        } else if (!containsCapitalLetter(password)) {
            _loginForm.value =
                LoginFormState(passwordError = R.string.invalid_password_capital_letter)
        } else if (!containsDigit(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password_digit)
        } else if (!containsSpecialCharacter(password)) {
            _loginForm.value =
                LoginFormState(passwordError = R.string.invalid_password_special_character)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            false
        }
    }

    private fun isLengthValid(password: String): Boolean {
        return password.length >= 12
    }

    private fun containsSpecialCharacter(password: String): Boolean {
        return password.contains(".*[^A-Za-z0-9\\s].*\$".toRegex())
    }

    private fun containsCapitalLetter(password: String): Boolean {
        return password.contains(".*[A-Z].*\$".toRegex())
    }

    private fun containsDigit(password: String): Boolean {
        return password.contains(".*[0-9].*\$".toRegex())
    }
}