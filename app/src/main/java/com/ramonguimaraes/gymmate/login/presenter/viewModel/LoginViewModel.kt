package com.ramonguimaraes.gymmate.login.presenter.viewModel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramonguimaraes.gymmate.core.Result
import com.ramonguimaraes.gymmate.login.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<Unit>>()
    val loginResult: LiveData<Result<Unit>> = _loginResult

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> = _emailError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> = _passwordError

    fun login(email: String, password: String) {
        if (validateFields(email, password)) {
            viewModelScope.launch(Dispatchers.IO) {
                _loginResult.postValue(repository.login(email, password))
            }
        }
    }

    private fun validateEmail(email: String?): Boolean {
        return when {
            email.isNullOrBlank() -> {
                _emailError.value = "Campo obrigatório"
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _emailError.value = "Email inválido"
                false
            }

            else -> true
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.isEmpty()) {
            _passwordError.value = "Campo obrigatorio"
            false
        } else {
            true
        }
    }

    private fun validateFields(email: String, password: String): Boolean {
        return validateEmail(email) and validatePassword(password)
    }
}