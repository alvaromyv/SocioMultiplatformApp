package com.amv.socioapp

// commonMain/Platform.kt
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class MiParcelize()

expect interface MiParcelable

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
expect annotation class MiIgnoredOnParcel()
