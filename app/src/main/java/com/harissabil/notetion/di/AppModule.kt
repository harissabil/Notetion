package com.harissabil.notetion.di

import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    operator fun invoke(): Module = module {
        includes(
            listOf(
                databaseModule,
                repositoryModule,
                geminiModule,
                useCaseModule,
                datastoreModule,
                viewModelModule
            )
        )
    }
}