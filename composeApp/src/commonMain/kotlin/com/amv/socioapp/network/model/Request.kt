package com.amv.socioapp.network.model

import com.amv.socioapp.model.Categoria
import com.amv.socioapp.model.Rol
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocioRequest(
    val categoria: Categoria,
    val abonado: Boolean,
    @SerialName("usuario_id") val usuarioId: Int?
)

@Serializable
data class UsuarioRequest(
    val nombre: String,
    val apellidos: String?,
    val telefono: String?,
    val email: String,
    val password: String,
    val rol: Rol,
)

@Serializable
data class AuthRequest(
    val email: String,
    val password: String
)