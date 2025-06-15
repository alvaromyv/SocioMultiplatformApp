package com.amv.socioapp.network.repository

import com.amv.socioapp.network.model.BaseResponse
import com.amv.socioapp.network.model.SocioBodyRequest
import com.amv.socioapp.network.service.SocioServices

interface SociosRepository {
//    suspend fun leerTodos(): BaseResponse
//    suspend fun leeUno(id: Int): BaseResponse
    suspend fun creaUno(socio: SocioBodyRequest) : BaseResponse
    suspend fun actualizaUno(id: Int, socio: SocioBodyRequest): BaseResponse
    suspend fun reasignarNumeracion(): BaseResponse
    suspend fun borraUno(id: Int): BaseResponse
}

class NetworkSociosRepository(
    private val socioServices: SocioServices
) : SociosRepository {
//    override suspend fun leerTodos(): BaseResponse = socioServices.leerTodos()
//    override suspend fun leeUno(id: Int): BaseResponse = socioServices.leeUno(id)
    override suspend fun creaUno(socio: SocioBodyRequest): BaseResponse = socioServices.creaUno(socio)
    override suspend fun actualizaUno(id: Int, socio: SocioBodyRequest): BaseResponse = socioServices.actualizaUno(id, socio)
    override suspend fun reasignarNumeracion(): BaseResponse = socioServices.reasignarNumeracion()
    override suspend fun borraUno(id: Int): BaseResponse = socioServices.borraUno(id)
}