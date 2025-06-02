package com.amv.socioapp.data

import com.amv.socioapp.network.NetworkUtils.httpClient
import com.amv.socioapp.network.createSocioServices
import com.amv.socioapp.network.SocioServices
import de.jensklingenberg.ktorfit.Ktorfit

interface AppContainer {
    val sociosRepository: SociosRepository
    val sessionManager: SessionManager
}

object DefaultAppContainer : AppContainer {
    private val baseUrl =
        "http://10.0.2.2:3000/"

    private val ktorfit: Ktorfit = Ktorfit.Builder()
        .baseUrl(baseUrl)
        .httpClient(httpClient)
        .build()

    private val servicioSocios: SocioServices by lazy {
        ktorfit.createSocioServices()
    }

    override val sessionManager: SessionManager = SessionManager

    override val sociosRepository: SociosRepository by lazy {
        NetworkSociosRepository(servicioSocios)
    }
}