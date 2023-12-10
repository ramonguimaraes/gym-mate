package com.ramonguimaraes.gymmate.login.presenter.viewModel

import androidx.lifecycle.ViewModel

class PhoneAuthViewModel : ViewModel() {

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex("^\\+[0-9]+\$"))
    }
}
