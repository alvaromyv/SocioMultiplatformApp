package com.amv.socioapp.data

import com.amv.socioapp.network.model.AuthRequest
import com.amv.socioapp.network.model.BaseResponse
import com.amv.socioapp.network.model.UsuarioRequest
import com.amv.socioapp.network.service.AuthService

interface AuthRepository {
    suspend fun registrarUsuario(usuario: UsuarioRequest): BaseResponse
    suspend fun iniciarSesion(credenciales: AuthRequest): BaseResponse
}

class NetworkAuthRepository(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun registrarUsuario(usuario: UsuarioRequest): BaseResponse = authService.registrar(usuario)
    override suspend fun iniciarSesion(credenciales: AuthRequest): BaseResponse = authService.iniciarSesion(credenciales)
}