package com.ramonguimaraes.gymmate.login.data.dataSource

import com.ramonguimaraes.gymmate.core.Result

interface AuthDataSource {

    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun createAccount(email: String, password: String): Result<Unit>
    suspend fun resetPassword(email: String): Result<Unit>
    suspend fun verifyCode(verificationId: String, code: String): Result<Unit>
}
