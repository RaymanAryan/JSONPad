package com.rayman.jsonpad.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private object PreferencesKeys {
        val UI_MODE = stringPreferencesKey("ui_mode")
        val DARK_MODE_STATUS = booleanPreferencesKey("dark_mode")
    }

    val themeFlow: Flow<String> = dataStore.data.map { it[PreferencesKeys.UI_MODE] ?: "Normal" }
    val isDarkModeFlow: Flow<Boolean> = dataStore.data.map { it[PreferencesKeys.DARK_MODE_STATUS] ?: false }

    suspend fun saveThemePreference(mode: String) {
        dataStore.edit { it[PreferencesKeys.UI_MODE] = mode }
    }

    suspend fun saveDarkModePreference(isDarkMode: Boolean) {
        dataStore.edit { it[PreferencesKeys.DARK_MODE_STATUS] = isDarkMode }
    }
}
