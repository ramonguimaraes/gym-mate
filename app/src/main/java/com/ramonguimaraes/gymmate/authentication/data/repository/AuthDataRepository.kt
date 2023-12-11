package com.ramonguimaraes.gymmate.authentication.data.repository

import com.ramonguimaraes.gymmate.core.utils.Result
import com.ramonguimaraes.gymmate.authentication.data.dataSource.AuthDataSource
import com.ramonguimaraes.gymmate.authentication.domain.repository.AuthRepository

class AuthDataRepository(private val dataSource: AuthDataSource): AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> {
        return dataSource.login(email, password)
    }

    override suspend fun createAccount(email: String, password: String): Result<Unit> {
        return dataSource.createAccount(email, password)
    }

    override suspend fun resetPassword(email: String): Result<Unit> {
        return dataSource.resetPassword(email)
    }

    override suspend fun verifyCode(verificationId: String, code: String): Result<Unit> {
        return dataSource.verifyCode(verificationId, code)
    }

    override suspend fun sigInWithGoogle(token: String): Result<Unit> {
        return dataSource.sigInWithGoogle(token)
    }

    override fun currentUserId(): String? {
        return dataSource.currentUseId()
    }
}
