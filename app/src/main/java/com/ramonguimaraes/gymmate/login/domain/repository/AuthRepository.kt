package com.ramonguimaraes.gymmate.login.domain.repository

import com.ramonguimaraes.gymmate.core.Result

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
}
