package com.harissabil.notetion.data.datastore.intro

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IntroManager(
    private val context: Context
) {
    suspend fun setShowIntroHomeCompleted(showIntroHomeCompleted: Boolean) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.SHOW_INTRO_HOME_COMPLETED] = showIntroHomeCompleted
        }
    }

    fun getShowIntroHomeCompleted(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.SHOW_INTRO_HOME_COMPLETED] ?: false
        }
    }

    suspend fun setShowIntroNoteCompleted(showIntroNoteCompleted: Boolean) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.SHOW_INTRO_NOTE_COMPLETED] = showIntroNoteCompleted
        }
    }

    fun getShowIntroNoteCompleted(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.SHOW_INTRO_NOTE_COMPLETED] ?: false
        }
    }

    suspend fun setShowIntroQuestionCompleted(showIntroQuestionCompleted: Boolean) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.SHOW_INTRO_QUESTION_COMPLETED] = showIntroQuestionCompleted
        }
    }

    fun getShowIntroQuestionCompleted(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.SHOW_INTRO_QUESTION_COMPLETED] ?: false
        }
    }
}

private val readOnlyProperty = preferencesDataStore(name = "intro")

val Context.dataStore: DataStore<Preferences> by readOnlyProperty

private object PreferenceKeys {
    val SHOW_INTRO_HOME_COMPLETED = booleanPreferencesKey("show_intro_home_completed")
    val SHOW_INTRO_NOTE_COMPLETED = booleanPreferencesKey("show_intro_note_completed")
    val SHOW_INTRO_QUESTION_COMPLETED = booleanPreferencesKey("show_intro_question_completed")
}