package com.amv.socioapp.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.amv.socioapp.util.cambiarIdioma
import com.amv.socioapp.util.getLanguageTag
import com.hyperether.resources.AppLocale
import com.hyperether.resources.currentLanguage

object AjustesManager {
    var tema by mutableStateOf(AppThemeMode.SYSTEM)
    var idioma by mutableStateOf(AppLanguage.ENGLISH)

    init {
        currentLanguage.value = AppLocale.findByCode(getLanguageTag())
        idioma = when (currentLanguage.value.code) {
            AppLanguage.SPANISH.tag -> AppLanguage.SPANISH
            else -> AppLanguage.ENGLISH
        }
    }

    fun onTemaChange(nuevo: AppThemeMode) {
        tema = nuevo
    }

    fun onIdiomaChange(nuevo: AppLanguage) {
        idioma = nuevo
        currentLanguage.value = AppLocale.findByCode(idioma.tag)
    }
}

enum class AppLanguage(val tag: String) {
    ENGLISH("en"),
    SPANISH("es")
}

enum class AppThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}