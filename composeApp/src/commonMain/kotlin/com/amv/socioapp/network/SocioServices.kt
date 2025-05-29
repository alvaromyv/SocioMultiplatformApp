package com.amv.socioapp.network

import com.amv.socioapp.model.Socio
import com.amv.socioapp.network.response.ApiResponse
import de.jensklingenberg.ktorfit.http.GET

interface SocioServices {
    @GET("socios")
    suspend fun leerTodos(): ApiResponse<List<Socio>>
}