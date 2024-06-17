package com.harissabil.notetion.data.datastore.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsManager(
    private val context: Context,
) {
    suspend fun setTheme(theme: Theme) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.THEME] = theme.value
        }
    }

    fun getTheme(): Flow<Theme> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.THEME]?.let { themeValue ->
                Theme.entries.find { it.value == themeValue }
            } ?: Theme.SYSTEM_DEFAULT
        }
    }
}

private val readOnlyProperty = preferencesDataStore(name = "settings")

val Context.dataStore: DataStore<Preferences> by readOnlyProperty

private object PreferenceKeys {
    val THEME = stringPreferencesKey("theme")
}