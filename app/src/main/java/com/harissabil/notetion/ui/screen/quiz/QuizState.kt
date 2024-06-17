package com.harissabil.notetion.ui.screen.quiz

import com.harissabil.notetion.data.local.entity.QuestionWithAnswers

data class QuizState(
    val quizTitle: String = "",
    val quizList: List<QuestionWithAnswers> = emptyList(),
    val selectedAnswers: List<Int> = emptyList(),
    val correctAnswers: Int = 0,
    val isSubmitted: Boolean = false,
)
