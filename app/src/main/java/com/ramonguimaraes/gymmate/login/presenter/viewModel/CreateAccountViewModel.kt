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

class CreateAccountViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _createAccountResult = MutableLiveData<Result<Unit>>()
    val createAccountResult: LiveData<Result<Unit>> = _createAccountResult

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> = _emailError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> = _passwordError

    private val _repeatPasswordError = MutableLiveData<String>()
    val repeatPasswordError: LiveData<String> = _repeatPasswordError

    fun createAccount(email: String, password: String, repeatPassword: String) {
        if (validateFields(email, password, repeatPassword)) {
            viewModelScope.launch(Dispatchers.IO) {
                _createAccountResult.postValue(repository.createAccount(email, password))
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

    private fun validatePassword(password: String, repeatPassword: String): Boolean {
        return when {
            password.isEmpty() -> {
                _passwordError.value = "Campo obrigatório"
                false
            }

            repeatPassword != password -> {
                _repeatPasswordError.value = "As senhas devem ser iguais"
                false
            }

            else -> true
        }
    }

    private fun validateFields(email: String, password: String, repeatPassword: String): Boolean {
        return validateEmail(email) and validatePassword(password, repeatPassword)
    }
}
