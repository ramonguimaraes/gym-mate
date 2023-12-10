package com.ramonguimaraes.gymmate.core.di

import com.ramonguimaraes.gymmate.login.di.authModule

object KoinModules {

    fun modules() = listOf(
        firebaseModule(),
        authModule()
    )
}
