package com.amv.socioapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val id: Int,
    val avatarUrl: String?,
    val nombre: String,
    val apellidos: String?,
    val telefono: String?,
    val email: String,
    val password: String,
    val rol: Rol,
)

enum class Rol {
    @SerialName("administrador") ADMINISTRADOR,
    @SerialName("usuario") USUARIO
}