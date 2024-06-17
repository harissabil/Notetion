package com.harissabil.notetion.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class NoteWithQuestionsAndAnswers(
    @Embedded val note: Note,
    @Relation(
        entity = Question::class,
        parentColumn = "noteId",
        entityColumn = "noteOriginId"
    )
    val questions: List<QuestionWithAnswers>,
)
