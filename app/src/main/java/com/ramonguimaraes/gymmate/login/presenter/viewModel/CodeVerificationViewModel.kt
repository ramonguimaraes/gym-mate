package com.ramonguimaraes.gymmate.login.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramonguimaraes.gymmate.core.Result
import com.ramonguimaraes.gymmate.login.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CodeVerificationViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _singUpWithPhoneResult = MutableLiveData<Result<Unit>>()
    val singUpWithPhoneResult: LiveData<Result<Unit>> = _singUpWithPhoneResult

    fun verifyCode(verificationId: String, code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _singUpWithPhoneResult.postValue(repository.verifyCode(verificationId, code))
        }
    }
}
