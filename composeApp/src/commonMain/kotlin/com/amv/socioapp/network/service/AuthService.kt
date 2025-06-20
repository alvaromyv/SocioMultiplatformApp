package com.amv.socioapp.network.service

import com.amv.socioapp.network.model.AuthRequest
import com.amv.socioapp.network.model.BaseResponse
import com.amv.socioapp.network.model.UsuarioBodyRequest
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST

interface AuthService {
    @POST("signup")
    suspend fun registrar(@Body usuario: UsuarioBodyRequest): BaseResponse

    @POST("login")
    suspend fun iniciarSesion(@Body credenciales: AuthRequest): BaseResponse
}