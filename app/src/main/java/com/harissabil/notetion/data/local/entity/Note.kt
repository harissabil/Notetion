package com.harissabil.notetion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val noteId: Int = 0,
    val title: String? = null,
    val content: String,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant? = null,
)
