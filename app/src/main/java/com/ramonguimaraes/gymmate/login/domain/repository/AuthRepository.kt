package com.ramonguimaraes.gymmate.login.domain.repository

import com.ramonguimaraes.gymmate.core.Result

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun createAccount(email: String, password: String): Result<Unit>
    suspend fun resetPassword(email: String): Result<Unit>
    suspend fun verifyCode(verificationId: String, code: String): Result<Unit>
    suspend fun sigInWithGoogle(token: String): Result<Unit>
}
