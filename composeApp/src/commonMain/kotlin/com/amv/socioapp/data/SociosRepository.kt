package com.amv.socioapp.data

import com.amv.socioapp.network.SocioServices
import com.amv.socioapp.network.BaseResponse

interface SociosRepository {
    suspend fun obtenerSocios(): BaseResponse

    suspend fun borraUno(id: Int): BaseResponse
}

class NetworkSociosRepository(
    private val socioServices: SocioServices
) : SociosRepository {
    override suspend fun obtenerSocios(): BaseResponse = socioServices.leerTodos()

    override suspend fun borraUno(id: Int): BaseResponse = socioServices.borraUno(id)
}