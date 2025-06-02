package com.amv.socioapp.network

import com.amv.socioapp.network.response.BaseResponse
import de.jensklingenberg.ktorfit.http.GET

interface SocioServices {
    @GET("socios")
    suspend fun leerTodos(): BaseResponse
}