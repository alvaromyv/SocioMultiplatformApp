package com.amv.socioapp.navigation

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

@Serializable
@SerialName("inicio")
object Inicio

@Serializable
@SerialName("contabilidad")
object Contabilidad

@Serializable
@SerialName("socios")
object Socios

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> getSerialName(): String {
    return serializer<T>().descriptor.serialName
}