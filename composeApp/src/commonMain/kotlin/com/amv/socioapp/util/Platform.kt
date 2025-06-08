package com.amv.socioapp.util

// commonMain/Platform.kt
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class MyParcelize()

expect interface MyParcelable

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
expect annotation class MyIgnoredOnParcel()

expect fun getBaseUrl(): String