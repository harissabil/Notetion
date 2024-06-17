package com.harissabil.notetion.ui.screen.questions

import com.harissabil.notetion.data.local.entity.NoteWithQuestionsAndAnswers

data class QuestionsState(
    val noteWithQuestionsAndAnswers: List<NoteWithQuestionsAndAnswers> = emptyList(),
)
