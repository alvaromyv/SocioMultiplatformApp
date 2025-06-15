package com.amv.socioapp.network.repository

import com.amv.socioapp.model.Rol
import com.amv.socioapp.network.model.BaseResponse
import com.amv.socioapp.network.model.UsuarioBodyRequest
import com.amv.socioapp.network.service.UsuarioService
import io.ktor.client.request.forms.MultiPartFormDataContent

interface UsuariosRepository {
    suspend fun leeTodos(): BaseResponse
    suspend fun leePerfil(): BaseResponse
    suspend fun leeUno(id: Int): BaseResponse
    suspend fun busca(texto: String): BaseResponse
    suspend fun subirAvatar(id: Int, multipart: MultiPartFormDataContent): BaseResponse
    suspend fun actualizarPerfil(usuario: UsuarioBodyRequest): BaseResponse
    suspend fun actualizaUno(id: Int, usuario: UsuarioBodyRequest): BaseResponse
    suspend fun cambiarRol(id: Int, rol: Rol): BaseResponse
    suspend fun borraUno(id: Int): BaseResponse
}

class NetworkUsuariosRepository(
    private val usuarioService: UsuarioService
) : UsuariosRepository {
    override suspend fun leeTodos(): BaseResponse = usuarioService.leerTodos()
    override suspend fun leePerfil(): BaseResponse = usuarioService.leePerfil()
    override suspend fun leeUno(id: Int): BaseResponse = usuarioService.leeUno(id)
    override suspend fun busca(texto: String): BaseResponse = usuarioService.busca(texto)
    override suspend fun subirAvatar(id: Int, multipart: MultiPartFormDataContent): BaseResponse = usuarioService.subirAvatar(id, multipart)
    override suspend fun actualizarPerfil(usuario: UsuarioBodyRequest): BaseResponse = usuarioService.actualizarPerfil(usuario)
    override suspend fun actualizaUno(id: Int, usuario: UsuarioBodyRequest): BaseResponse = usuarioService.modificaUno(id, usuario)
    override suspend fun cambiarRol(id: Int, rol: Rol): BaseResponse = usuarioService.cambiarRol(id, rol)
    override suspend fun borraUno(id: Int): BaseResponse = usuarioService.borraUno(id)

}