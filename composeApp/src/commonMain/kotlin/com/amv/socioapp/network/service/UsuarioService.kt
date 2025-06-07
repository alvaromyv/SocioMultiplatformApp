package com.amv.socioapp.network.service

import com.amv.socioapp.model.Rol
import com.amv.socioapp.network.model.BaseResponse
import com.amv.socioapp.network.model.UsuarioRequest
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

interface UsuarioService {
    @GET("usuarios/todos")
    suspend fun leerTodos(): BaseResponse

    @GET("usuarios")
    suspend fun obtenerPerfil(): BaseResponse

    @POST("usuarios/subir-avatar/{id}")
    suspend fun subirAvatar(@Path("id") id: Int): BaseResponse

    @PATCH("usuarios")
    suspend fun actualizarPerfil(@Body usuario: UsuarioRequest): BaseResponse

    @PATCH("usuarios/cambiar-rol/{id}")
    suspend fun cambiarRol(@Path("id") id: Int, @Body rol: Rol): BaseResponse

    @DELETE("usuarios/{id}")
    suspend fun borraUno(@Path("id") id: Int): BaseResponse
}