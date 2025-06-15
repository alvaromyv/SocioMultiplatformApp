package com.amv.socioapp.util

import kotlinx.browser.window

// wasmJs/Platform.kt
actual interface MyParcelable
actual annotation class MyIgnoredOnParcel

actual fun getBaseUrl(): String = "http://localhost:3000/"

actual fun getLanguageTag(): String = window.navigator.language