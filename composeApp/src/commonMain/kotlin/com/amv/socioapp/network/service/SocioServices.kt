package com.amv.socioapp.network.service

import com.amv.socioapp.network.model.BaseResponse
import com.amv.socioapp.network.model.SocioRequest
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

interface SocioServices {
    @GET("socios")
    suspend fun leerTodos(): BaseResponse

    @GET("socios/{id}")
    suspend fun leeUno(@Path("id") id: Int): BaseResponse

    @POST("socios")
    suspend fun creaUno(@Body socio: SocioRequest): BaseResponse

    @PATCH("socios/{id}")
    suspend fun actualizaUno(@Path("id") id: Int, @Body socio: SocioRequest): BaseResponse

    @PATCH("reasignar")
    suspend fun reasignarNumeracion(): BaseResponse

    @DELETE("socios/{id}")
    suspend fun borraUno(@Path("id") id: Int): BaseResponse
}