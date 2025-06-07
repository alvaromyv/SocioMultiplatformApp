package com.amv.socioapp.data

import com.amv.socioapp.model.Rol
import com.amv.socioapp.network.model.BaseResponse
import com.amv.socioapp.network.model.UsuarioRequest
import com.amv.socioapp.network.service.UsuarioService

interface UsuariosRepository {
    suspend fun obtenerUsuarios(): BaseResponse
    suspend fun obtenerPerfil(): BaseResponse
    suspend fun subirAvatar(id: Int): BaseResponse
    suspend fun actualizarPerfil(usuario: UsuarioRequest): BaseResponse
    suspend fun cambiarRol(id: Int, rol: Rol): BaseResponse
    suspend fun borraUno(id: Int): BaseResponse
}

class NetworkUsuariosRepository(
    private val usuarioService: UsuarioService
) : UsuariosRepository {
    override suspend fun obtenerUsuarios(): BaseResponse = usuarioService.leerTodos()
    override suspend fun obtenerPerfil(): BaseResponse = usuarioService.obtenerPerfil()
    override suspend fun subirAvatar(id: Int): BaseResponse = usuarioService.subirAvatar(id)
    override suspend fun actualizarPerfil(usuario: UsuarioRequest): BaseResponse = usuarioService.actualizarPerfil(usuario)
    override suspend fun cambiarRol(id: Int, rol: Rol): BaseResponse = usuarioService.cambiarRol(id, rol)
    override suspend fun borraUno(id: Int): BaseResponse = usuarioService.borraUno(id)

}