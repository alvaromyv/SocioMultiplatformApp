package com.amv.socioapp.util

import android.os.Parcel
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.TypeParceler
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalDateTime.Companion
import kotlinx.datetime.toLocalDateTime

// androidMain/Platform.kt
actual typealias MyParcelable = android.os.Parcelable
actual typealias MyIgnoredOnParcel = kotlinx.parcelize.IgnoredOnParcel
