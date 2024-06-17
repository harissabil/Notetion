package com.harissabil.notetion.di

import com.harissabil.notetion.ui.screen.home.HomeViewModel
import com.harissabil.notetion.ui.screen.note_detail.NoteDetailViewModel
import com.harissabil.notetion.ui.screen.notes.NotesViewModel
import com.harissabil.notetion.ui.screen.question_detail.QuestionDetailViewModel
import com.harissabil.notetion.ui.screen.questions.QuestionsViewModel
import com.harissabil.notetion.ui.screen.quiz.QuizViewModel
import com.harissabil.notetion.ui.screen.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::NoteDetailViewModel)
    viewModelOf(::NotesViewModel)
    viewModelOf(::QuestionsViewModel)
    viewModelOf(::QuestionDetailViewModel)
    viewModelOf(::QuizViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SettingsViewModel)
}