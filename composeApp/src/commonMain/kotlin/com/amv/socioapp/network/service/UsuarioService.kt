package com.amv.socioapp.network.service

import com.amv.socioapp.model.Rol
import com.amv.socioapp.network.model.BaseResponse
import com.amv.socioapp.network.model.UsuarioBodyRequest
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import io.ktor.client.request.forms.MultiPartFormDataContent

interface UsuarioService {
    @GET("usuarios/todos")
    suspend fun leerTodos(): BaseResponse

    @GET("usuarios")
    suspend fun leePerfil(): BaseResponse

    @GET("usuarios/{id}")
    suspend fun leeUno(@Path("id") id: Int): BaseResponse

    @GET("usuarios/buscar")
    suspend fun busca(@Query("q") texto: String): BaseResponse

    @POST("usuarios/subir-avatar/{id}")
    suspend fun subirAvatar(@Path("id") id: Int, @Body avatar: MultiPartFormDataContent): BaseResponse

    @PATCH("usuarios")
    suspend fun actualizarPerfil(@Body usuario: UsuarioBodyRequest): BaseResponse

    @PATCH("usuarios/{id}")
    suspend fun modificaUno(@Path("id") id: Int, @Body usuario: UsuarioBodyRequest): BaseResponse

    @PATCH("usuarios/cambiar-rol/{id}")
    suspend fun cambiarRol(@Path("id") id: Int, @Body rol: Rol): BaseResponse

    @DELETE("usuarios/{id}")
    suspend fun borraUno(@Path("id") id: Int): BaseResponse
}