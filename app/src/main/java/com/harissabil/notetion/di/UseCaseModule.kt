package com.harissabil.notetion.di

import com.harissabil.notetion.domain.SaveGeneratedQuestion
import org.koin.dsl.module

val useCaseModule = module {
     single { SaveGeneratedQuestion(get()) }
}