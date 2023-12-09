package com.ramonguimaraes.gymmate.login.data.dataSource

import com.google.firebase.auth.FirebaseAuth
import com.ramonguimaraes.gymmate.core.Result
import kotlinx.coroutines.tasks.await

class AuthDataSourceImpl(private val firebaseAuth: FirebaseAuth) : AuthDataSource {

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, e.message.toString())
        }
    }
}
