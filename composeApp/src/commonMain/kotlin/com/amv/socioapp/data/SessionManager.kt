package com.amv.socioapp.data

import com.amv.socioapp.model.Usuario
import com.amv.socioapp.network.NetworkUtils
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock

object SessionManager {
    // private const val SESION_USER_ID = "id_user_sesion"
    private const val BEARER_TOKEN = "bearer_token"
    private const val EXPIRATION_TIME = "token_expiration_time"

    private val settings: Settings = Settings()

    private val _sesion = MutableStateFlow(comprobarSesion())
    val sesion: StateFlow<Boolean> = _sesion.asStateFlow()

    fun nuevaSesion(usuarioId: Int, token: String, expiresIn: Long) {
        val milisegundosExpiracionToken = Clock.System.now().toEpochMilliseconds() + (expiresIn * 1000)
        settings.putString(BEARER_TOKEN, token)
        settings.putLong(EXPIRATION_TIME, milisegundosExpiracionToken)
        _sesion.value = true
    }

    fun cerrarSesion() {
        settings.remove(BEARER_TOKEN)
        settings.remove(EXPIRATION_TIME)
        _sesion.value = false
    }

    fun obtenerToken(): String? {
        val token = settings.getString(BEARER_TOKEN, "")
        return if (comprobarSesion() && token.isNotEmpty()) {
            token
        } else {
            null
        }
    }

    fun esSesionValida(): Boolean = _sesion.value

    private fun comprobarSesion(): Boolean {
        return try {
            if(settings.getString(BEARER_TOKEN, "").isEmpty() || settings.getLong(EXPIRATION_TIME, 0) == 0L){
                false
            }else{
                Clock.System.now().toEpochMilliseconds() < settings.getLong(EXPIRATION_TIME, 0)
            }
        }catch (_: Exception){
            false
        }
    }
}