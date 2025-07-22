package com.pedro.solutions.dialysisnotes

import android.app.Application
import com.pedro.solutions.dialysisnotes.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DialysisApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DialysisApplication)
            modules(AppModules)
        }
    }
}