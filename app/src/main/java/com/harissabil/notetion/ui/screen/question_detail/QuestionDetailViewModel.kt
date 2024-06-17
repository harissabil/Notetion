package com.harissabil.notetion.ui.screen.question_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harissabil.notetion.data.datastore.intro.IntroManager
import com.harissabil.notetion.data.local.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class QuestionDetailViewModel(
    private val noteRepository: NoteRepository,
    private val introManager: IntroManager
) : ViewModel() {

    private val _state = MutableStateFlow(QuestionDetailState())
    val state: StateFlow<QuestionDetailState> = _state.asStateFlow()

    var isShowIntroQuestionCompleted by mutableStateOf(false)
        private set

    init {
        getShowIntroQuestionCompleted()
    }

    private fun getShowIntroQuestionCompleted() = introManager.getShowIntroQuestionCompleted().onEach {
        isShowIntroQuestionCompleted = it
    }.launchIn(viewModelScope)

    fun onIntroQuestionCompleted() = viewModelScope.launch {
        introManager.setShowIntroQuestionCompleted(showIntroQuestionCompleted = true)
    }

    fun loadQuestionsWithAnswers(noteId: Int) = viewModelScope.launch {
        val noteWithQuestionsAndAnswers =
            noteRepository.getNoteWithQuestionsAndAnswersByNoteId(noteId)
        _state.value = _state.value.copy(noteWithQuestionsAndAnswers = noteWithQuestionsAndAnswers)
    }
}