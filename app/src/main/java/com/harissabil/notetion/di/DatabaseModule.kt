package com.harissabil.notetion.di

import android.app.Application
import androidx.room.Room
import com.harissabil.notetion.data.local.room.NoteDatabase
import org.koin.dsl.module

fun provideNoteDatabase(application: Application): NoteDatabase = Room.databaseBuilder(
    context = application,
    klass = NoteDatabase::class.java,
    name = "notetion_db"
)
    .fallbackToDestructiveMigration()
    .build()

fun provideNoteDao(database: NoteDatabase) = database.noteDao()

val databaseModule = module {
    single { provideNoteDatabase(get()) }
    single { provideNoteDao(get()) }
}