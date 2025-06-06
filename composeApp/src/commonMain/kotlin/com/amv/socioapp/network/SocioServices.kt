package com.amv.socioapp.network

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.internal.InternalKtorfitApi

interface SocioServices {
    @GET("socios")
    suspend fun leerTodos(): BaseResponse

    @POST("socios")
    suspend fun creaUno(@Body socio: SocioRequest): BaseResponse

    @DELETE("socios/{id}")
    suspend fun borraUno(@Path("id") id: Int): BaseResponse
}