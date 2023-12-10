package com.ramonguimaraes.gymmate.core.di

import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

fun firebaseModule() = module {
    single { FirebaseAuth.getInstance() }
}