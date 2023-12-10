package com.ramonguimaraes.gymmate.authentication.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramonguimaraes.gymmate.core.utils.Result
import com.ramonguimaraes.gymmate.authentication.domain.repository.AuthRepository
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
