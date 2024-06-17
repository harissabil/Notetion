package com.harissabil.notetion.di

import com.harissabil.notetion.data.local.NoteRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { NoteRepository(get()) }
}