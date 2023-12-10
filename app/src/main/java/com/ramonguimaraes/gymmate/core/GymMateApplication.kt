package com.ramonguimaraes.gymmate.core

import android.app.Application
import com.ramonguimaraes.gymmate.core.di.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GymMateApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GymMateApplication)
            modules(KoinModules.modules())
        }
    }
}
