package com.amv.socioapp.data

import com.amv.socioapp.model.Socio
import com.amv.socioapp.network.SocioServices
import com.amv.socioapp.network.response.BaseResponse

interface SociosRepository {
    suspend fun obtenerSocios(): BaseResponse
}

class NetworkSociosRepository(
    private val socioServices: SocioServices
) : SociosRepository {
    override suspend fun obtenerSocios(): BaseResponse = socioServices.leerTodos()
}