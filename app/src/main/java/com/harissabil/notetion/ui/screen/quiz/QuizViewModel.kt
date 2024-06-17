package com.harissabil.notetion.ui.screen.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harissabil.notetion.data.local.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val noteRepository: NoteRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(QuizState())
    val state: StateFlow<QuizState> = _state.asStateFlow()

    fun getQuizList(noteId: Int) = viewModelScope.launch {
        val quizList = noteRepository.getNoteWithQuestionsAndAnswersByNoteId(noteId)
        _state.value = state.value.copy(
            quizTitle = quizList.note.title ?: "",
            quizList = quizList.questions.map { questionsWithAnswers ->
                questionsWithAnswers.answers.shuffled().let { answers ->
                    questionsWithAnswers.copy(answers = answers)
                }
            }.shuffled(),
            selectedAnswers = List(quizList.questions.size) { -1 }
        )
    }

    fun onAnswerSelected(page: Int, answerIndex: Int) {
        val selectedAnswers = state.value.selectedAnswers.toMutableList()
        selectedAnswers[page] = answerIndex
        _state.value = state.value.copy(selectedAnswers = selectedAnswers)
    }

    fun onSubmit(page: Int) {
        if (state.value.selectedAnswers[page] == -1) {
            _state.value = state.value.copy(
                isSubmitted = true
            )
            return
        }
        val isCorrect =
            state.value.quizList[page].answers[state.value.selectedAnswers[page]].isCorrect
        val correctAnswers = state.value.correctAnswers + if (isCorrect) 1 else 0
        _state.value = state.value.copy(
            correctAnswers = correctAnswers,
            isSubmitted = true
        )
    }

    fun onNext() {
        _state.value = state.value.copy(
            isSubmitted = false
        )
    }

    fun retry() {
        _state.value = QuizState(
            quizTitle = state.value.quizTitle,
            quizList = state.value.quizList,
            selectedAnswers = List(state.value.quizList.size) { -1 },
            correctAnswers = 0,
            isSubmitted = false
        )
    }
}