package com.harissabil.notetion.di

import com.harissabil.notetion.data.gemini.GeminiClient
import org.koin.dsl.module

val geminiModule = module {
    single { GeminiClient() }
}