package com.amv.socioapp.model

import com.amv.socioapp.util.MyParcelable
import com.amv.socioapp.util.MyParcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@MyParcelize
@Serializable
data class Usuario(
    val id: Int,
    @SerialName("avatar_url") val avatarUrl: String,
    val nombre: String,
    val apellidos: String?,
    val telefono: String?,
    val email: String,
    val password: String,
    val rol: Rol,
) : MyParcelable {
    fun obtenerNombreCompleto(): String {
        apellidos.let {
            return "$nombre $apellidos"
        }
        return nombre
    }
}

@MyParcelize
enum class Rol: MyParcelable {
    @SerialName("administrador") ADMINISTRADOR,
    @SerialName("usuario") USUARIO
}