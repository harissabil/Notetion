package com.harissabil.notetion.ui.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data class NoteDetail(
        val noteId: Int = 0,
    ) : Screen()

    @Serializable
    data class QuestionDetail(
        val noteId: Int = 0,
    ) : Screen()

    @Serializable
    data class Quiz(
        val noteId: Int = 0,
    ) : Screen()

    @Serializable
    data object Settings : Screen()

    @Serializable
    data object About : Screen()
}