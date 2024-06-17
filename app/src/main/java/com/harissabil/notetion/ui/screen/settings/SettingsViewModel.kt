package com.harissabil.notetion.ui.screen.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harissabil.notetion.data.datastore.settings.SettingsManager
import com.harissabil.notetion.data.datastore.settings.Theme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsManager: SettingsManager,
) : ViewModel() {

    var theme by mutableStateOf(Theme.SYSTEM_DEFAULT)
        private set

    init {
        getTheme()
    }

    private fun getTheme() = settingsManager.getTheme().onEach {
        theme = it
    }.launchIn(viewModelScope)

    fun setTheme(theme: Theme) = viewModelScope.launch {
        settingsManager.setTheme(theme)
    }
}