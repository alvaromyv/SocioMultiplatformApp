package com.amv.socioapp.data

import com.amv.socioapp.network.NetworkUtils.ktorfit
import com.amv.socioapp.network.createSocioServices
import com.amv.socioapp.network.SocioServices

interface AppContainer {
    val sociosRepository: SociosRepository
    val sessionManager: SessionManager
}

object DefaultAppContainer : AppContainer {
    private val servicioSocios: SocioServices by lazy {
        ktorfit.createSocioServices()
    }
    override val sessionManager: SessionManager = SessionManager

    override val sociosRepository: SociosRepository by lazy {
        NetworkSociosRepository(servicioSocios)
    }
}