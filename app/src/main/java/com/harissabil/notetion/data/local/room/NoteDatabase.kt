package com.harissabil.notetion.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harissabil.notetion.data.local.entity.Answer
import com.harissabil.notetion.data.local.entity.Note
import com.harissabil.notetion.data.local.entity.Question

@Database(
    entities = [Note::class, Question::class, Answer::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}