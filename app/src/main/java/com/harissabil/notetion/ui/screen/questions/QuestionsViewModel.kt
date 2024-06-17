package com.harissabil.notetion.ui.screen.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harissabil.notetion.data.local.NoteRepository
import com.harissabil.notetion.data.local.entity.NoteWithQuestionsAndAnswers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuestionsViewModel(
    private val noteRepository: NoteRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(QuestionsState())
    val state: StateFlow<QuestionsState> = _state.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() = viewModelScope.launch {
        noteRepository.getNotesWithQuestionsAndAnswer().collect { notes ->
            _state.value = _state.value.copy(noteWithQuestionsAndAnswers = notes)
        }
    }

    fun deleteQuestionsWithAnswers(noteWithQuestionsAndAnswers: NoteWithQuestionsAndAnswers) =
        viewModelScope.launch {
            noteRepository.deleteQuestionsByNoteId(noteWithQuestionsAndAnswers.note.noteId)
        }
}