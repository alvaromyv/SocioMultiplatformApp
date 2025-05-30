package com.amv.socioapp.model

import kotlinx.serialization.SerialName

data class Usuario(
    val id: Int,
    val avatarUrl: String?,
    val nombre: String,
    val apellidos: String?,
    val telefono: String?,
    @SerialName("fecha_nacimiento") var fechaNacimiento: String?,
    val correo: String,
    val rol: Rol,
)

enum class Rol {
    @SerialName("administrador") ADMININISTRADOR,
    @SerialName("usuario") USUARIO
}