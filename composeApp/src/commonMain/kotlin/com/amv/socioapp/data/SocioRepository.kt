package com.amv.socioapp.data

import com.amv.socioapp.model.Socio
import com.amv.socioapp.network.SocioServices
import com.amv.socioapp.network.response.ApiResponse

interface SociosRepository {
    suspend fun obtenerSocios(): ApiResponse<List<Socio>>
}

class NetworkSociosRepository(
    private val socioServices: SocioServices
) : SociosRepository {
    override suspend fun obtenerSocios(): ApiResponse<List<Socio>> = socioServices.leerTodos()
}