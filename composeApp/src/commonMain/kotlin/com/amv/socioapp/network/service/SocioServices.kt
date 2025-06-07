package com.amv.socioapp.network.service

import com.amv.socioapp.network.model.BaseResponse
import com.amv.socioapp.network.model.SocioRequest
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

interface SocioServices {
    @GET("socios")
    suspend fun leerTodos(): BaseResponse

    @POST("socios")
    suspend fun creaUno(@Body socio: SocioRequest): BaseResponse

    @DELETE("socios/{id}")
    suspend fun borraUno(@Path("id") id: Int): BaseResponse
}