package com.amv.socioapp.util

import java.util.Locale

// androidMain/Platform.kt
actual typealias MyParcelable = android.os.Parcelable
actual typealias MyIgnoredOnParcel = kotlinx.parcelize.IgnoredOnParcel

actual fun getBaseUrl(): String = "http://10.0.2.2:3000/"

actual fun getLanguageTag(): String =  Locale.getDefault().toLanguageTag()