package com.amv.socioapp.network.service

import com.amv.socioapp.network.model.BaseResponse
import com.amv.socioapp.network.model.SocioBodyRequest
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

interface SocioServices {
//    @GET("socios")
//    suspend fun leerTodos(): BaseResponse
//
//    @GET("socios/{id}")
//    suspend fun leeUno(@Path("id") id: Int): BaseResponse

    @POST("socios")
    suspend fun creaUno(@Body socio: SocioBodyRequest): BaseResponse

    @PATCH("socios/{id}")
    suspend fun actualizaUno(@Path("id") id: Int, @Body socio: SocioBodyRequest): BaseResponse

    @PATCH("socios/reasignar")
    suspend fun reasignarNumeracion(): BaseResponse

    @DELETE("socios/{id}")
    suspend fun borraUno(@Path("id") id: Int): BaseResponse
}