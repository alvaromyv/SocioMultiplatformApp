package com.amv.socioapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.amv.socioapp.AppEnvironment
import com.amv.socioapp.network.repository.AuthRepository
import com.amv.socioapp.data.SessionManager
import com.amv.socioapp.model.Usuario
import com.amv.socioapp.network.model.AuthDataResponse
import com.amv.socioapp.network.model.AuthRequest
import com.amv.socioapp.network.model.AuthResponse
import com.amv.socioapp.network.model.ResponseError
import com.amv.socioapp.network.model.ResponseSuccess
import com.amv.socioapp.network.model.UnUsuarioResponse
import com.amv.socioapp.network.model.UsuarioRequest
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException

sealed interface AuthUiState {
    data class Success(val sesion: AuthDataResponse) : AuthUiState
    data class Error(val message: String?) : AuthUiState
    data class Exception(val e: Throwable) : AuthUiState
    object Loading : AuthUiState
}

class AuthViewModel(private val authRepository: AuthRepository, private val sessionManager: SessionManager) : ViewModel() {
    var authUiState: AuthUiState by mutableStateOf(AuthUiState.Loading)
        private set

    val sesionValida = sessionManager.sesion

    fun registrarUsuario(usuario: UsuarioRequest){
        viewModelScope.launch {
            authUiState = try {
                when(val response = authRepository.registrarUsuario(usuario)) {
                    is ResponseSuccess -> {
                        when (val content = response.data) {
                            is AuthResponse -> AuthUiState.Success(content.result)
                            else -> throw SerializationException()
                        }
                    }
                    is ResponseError -> AuthUiState.Error(response.error.message)
                    else -> throw SerializationException()
                }
            } catch (e: Throwable) {
                AuthUiState.Exception(e)
            }
        }
    }

    fun iniciarSesion(credenciales: AuthRequest){
        viewModelScope.launch {
            authUiState = try {
                when(val response = authRepository.iniciarSesion(credenciales)) {
                    is ResponseSuccess -> {
                        when (val content = response.data) {
                            is AuthResponse -> {
                                val sesion = content.result
                                sessionManager.nuevaSesion(sesion.token, sesion.expiraEn)
                                AuthUiState.Success(content.result)
                            }
                            else -> throw SerializationException()
                        }
                    }
                    is ResponseError -> AuthUiState.Error(response.error.message)
                    else -> throw SerializationException()
                }
            } catch (e: Throwable) {
                AuthUiState.Exception(e)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val authRepository = AppEnvironment.contenedor.authRepository
                val sessionManager = AppEnvironment.contenedor.sessionManager
                AuthViewModel(authRepository = authRepository, sessionManager = sessionManager)
            }
        }
    }
}