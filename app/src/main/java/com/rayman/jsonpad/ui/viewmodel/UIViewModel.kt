package com.rayman.jsonpad.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayman.jsonpad.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UIViewModel @Inject constructor(private val repository: SettingsRepository) : ViewModel() {
    val isDarkMode = repository.isDarkModeFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun toggleDarkMode() {
        viewModelScope.launch {
            repository.saveDarkModePreference(!isDarkMode.value)
        }
    }

    val themeState: StateFlow<String> = repository.themeFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, "normal")

    fun changeTheme(theme: String) {
        viewModelScope.launch {
            repository.saveThemePreference(theme)
        }
    }
}