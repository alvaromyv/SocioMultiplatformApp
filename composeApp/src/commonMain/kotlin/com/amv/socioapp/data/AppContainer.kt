package com.amv.socioapp.data

import com.amv.socioapp.network.NetworkUtils.ktorfit
import com.amv.socioapp.network.repository.AuthRepository
import com.amv.socioapp.network.repository.NetworkAuthRepository
import com.amv.socioapp.network.repository.NetworkSociosRepository
import com.amv.socioapp.network.repository.NetworkUsuariosRepository
import com.amv.socioapp.network.repository.SociosRepository
import com.amv.socioapp.network.repository.UsuariosRepository
import com.amv.socioapp.network.service.AuthService
import com.amv.socioapp.network.service.SocioServices
import com.amv.socioapp.network.service.UsuarioService
import com.amv.socioapp.network.service.createAuthService
import com.amv.socioapp.network.service.createSocioServices
import com.amv.socioapp.network.service.createUsuarioService

interface AppContainer {
    val sociosRepository: SociosRepository
    val usuariosRepository: UsuariosRepository
    val authRepository: AuthRepository
    val sessionManager: SessionManager
    val ajustesManager: AjustesManager
}

object DefaultAppContainer : AppContainer {
    private val servicioSocios: SocioServices by lazy { ktorfit.createSocioServices() }
    private val servicioUsuarios: UsuarioService by lazy { ktorfit.createUsuarioService() }
    private val servicioAuth: AuthService by lazy { ktorfit.createAuthService() }

    override val sessionManager: SessionManager = SessionManager
    override val ajustesManager: AjustesManager = AjustesManager

    override val sociosRepository: SociosRepository by lazy { NetworkSociosRepository(servicioSocios) }
    override val usuariosRepository: UsuariosRepository by lazy {
        NetworkUsuariosRepository(
            servicioUsuarios
        )
    }

    override val authRepository: AuthRepository by lazy { NetworkAuthRepository(servicioAuth) }
}

