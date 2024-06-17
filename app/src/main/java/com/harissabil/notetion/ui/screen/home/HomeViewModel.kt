package com.harissabil.notetion.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harissabil.notetion.data.datastore.intro.IntroManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val introManager: IntroManager,
) : ViewModel() {

    var isShowIntroHomeCompleted by mutableStateOf(false)
        private set

    init {
        getShowIntroHomeCompleted()
    }

    private fun getShowIntroHomeCompleted() = introManager.getShowIntroHomeCompleted().onEach {
        isShowIntroHomeCompleted = it
    }.launchIn(viewModelScope)

    fun onIntroHomeCompleted() = viewModelScope.launch {
        introManager.setShowIntroHomeCompleted(showIntroHomeCompleted = true)
    }
}