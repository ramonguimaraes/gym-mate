package com.ramonguimaraes.gymmate.login.data.dataSource

import com.ramonguimaraes.gymmate.core.Result

interface AuthDataSource {

    suspend fun login(email: String, password: String): Result<Unit>
}
