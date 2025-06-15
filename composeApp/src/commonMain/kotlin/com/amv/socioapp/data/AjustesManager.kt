package com.amv.socioapp.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AjustesManager {
    var theme by mutableStateOf(AppThemeMode.SYSTEM)
    var language by mutableStateOf(AppLanguage.ENGLISH)

    fun onChangeTheme(nuevo: AppThemeMode) {
        theme = nuevo
    }

    fun onLanguageChange(nuevo: AppLanguage) {
        language = nuevo
    }
}

enum class AppLanguage {
    ENGLISH,
    SPANISH
}

enum class AppThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}