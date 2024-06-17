package com.harissabil.notetion.ui.screen.note_detail

import com.harissabil.notetion.data.local.entity.Note
import com.harissabil.notetion.data.local.entity.QuestionWithAnswers
import com.mohamedrejeb.richeditor.model.RichTextState

data class NoteDetailState(
    val id: Int? = null,
    val title: String = "",
    val content: RichTextState = RichTextState(),
    val isTextRecognizing: Boolean = false,
    val isTextCorrecting: Boolean = false,
    val isGeneratingQuestion: Boolean = false,
    val savedNote: Note? = null,
    val questionsWithAnswers: List<QuestionWithAnswers>? = null,
) {
    fun isNoteBlank(): Boolean {
        return title.isBlank() && content.toMarkdown().isBlank()
    }

    fun isNoteModified(): Boolean {
        return title != savedNote?.title || content.toMarkdown() != savedNote.content
    }
}