package com.harissabil.notetion.ui.screen.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harissabil.notetion.data.local.NoteRepository
import com.harissabil.notetion.data.local.entity.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteRepository: NoteRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state: StateFlow<NotesState> = _state.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() = viewModelScope.launch {
        noteRepository.getAllNotes().collect { notes ->
            _state.value = _state.value.copy(notes = notes)
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }
}