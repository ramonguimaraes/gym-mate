package com.ramonguimaraes.gymmate.login.data.repository

import com.ramonguimaraes.gymmate.core.Result
import com.ramonguimaraes.gymmate.login.data.dataSource.AuthDataSource
import com.ramonguimaraes.gymmate.login.domain.repository.AuthRepository

class AuthDataRepository(private val dataSource: AuthDataSource): AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> {
        return dataSource.login(email, password)
    }
}