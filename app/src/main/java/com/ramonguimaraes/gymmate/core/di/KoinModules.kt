package com.ramonguimaraes.gymmate.core.di

import com.ramonguimaraes.gymmate.authentication.di.authModule

object KoinModules {

    fun modules() = listOf(
        firebaseModule(),
        authModule()
    )
}
