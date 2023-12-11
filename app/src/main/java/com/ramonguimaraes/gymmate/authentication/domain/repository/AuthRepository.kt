package com.ramonguimaraes.gymmate.authentication.domain.repository

import com.ramonguimaraes.gymmate.core.utils.Result

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun createAccount(email: String, password: String): Result<Unit>
    suspend fun resetPassword(email: String): Result<Unit>
    suspend fun verifyCode(verificationId: String, code: String): Result<Unit>
    suspend fun sigInWithGoogle(token: String): Result<Unit>
    fun currentUserId(): String?
}
