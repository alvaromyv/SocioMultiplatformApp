package com.amv.socioapp.util

import androidx.compose.ui.text.intl.Locale
import kotlinx.browser.window

// wasmJs/Platform.kt
actual interface MyParcelable
actual annotation class MyIgnoredOnParcel

actual fun getBaseUrl(): String = "http://localhost:3000/"

actual fun getLanguageTag(): String = window.navigator.language

actual fun cambiarIdioma(languageTag: String) { }