package com.harissabil.notetion.di

import com.harissabil.notetion.data.datastore.intro.IntroManager
import com.harissabil.notetion.data.datastore.settings.SettingsManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val datastoreModule = module {
    single { SettingsManager(androidContext()) }
    single { IntroManager(androidContext()) }
}