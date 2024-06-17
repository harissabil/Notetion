package com.harissabil.notetion

import android.app.Application
import com.harissabil.notetion.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class Notetion : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@Notetion)
            modules(AppModules())
        }
    }
}