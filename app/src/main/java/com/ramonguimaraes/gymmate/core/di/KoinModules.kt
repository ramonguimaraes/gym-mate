package com.ramonguimaraes.gymmate.core.di

import com.ramonguimaraes.gymmate.authentication.di.authModule
import com.ramonguimaraes.gymmate.workout.di.workoutModule

object KoinModules {

    fun modules() = listOf(
        firebaseModule(),
        authModule(),
        workoutModule()
    )
}
