package com.amv.socioapp.network.model

import com.amv.socioapp.model.Categoria
import com.amv.socioapp.model.Rol
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocioRequest(val id: Int?, val socio: SocioBodyRequest)

@Serializable
data class SocioBodyRequest(
    val categoria: Categoria,
    @SerialName("fecha_antiguedad") val fechaAntiguedad: String,
    val abonado: Boolean,
    @SerialName("usuario_id") val usuarioId: Int?
)

//@Serializable
data class UsuarioRequest(val id: Int?, val usuario: UsuarioBodyRequest, val avatar: PlatformFile? = null)

@Serializable
data class UsuarioBodyRequest(
    //@SerialName("avatar_url") val avatarUrl: String?,
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