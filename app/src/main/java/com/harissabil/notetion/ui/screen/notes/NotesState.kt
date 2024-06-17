package com.harissabil.notetion.ui.screen.notes

import com.harissabil.notetion.data.local.entity.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
)
