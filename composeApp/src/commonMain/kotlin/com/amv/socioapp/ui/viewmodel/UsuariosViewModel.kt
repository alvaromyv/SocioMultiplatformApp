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
import com.amv.socioapp.model.Usuario
import com.amv.socioapp.network.model.ResponseError
import com.amv.socioapp.network.model.ResponseSuccess
import com.amv.socioapp.network.model.SimpleResponse
import com.amv.socioapp.network.model.UnUsuarioResponse
import com.amv.socioapp.network.model.UsuarioBodyRequest
import com.amv.socioapp.network.model.UsuariosResponse
import com.amv.socioapp.network.repository.UsuariosRepository
import com.amv.socioapp.ui.events.SnackbarController
import com.amv.socioapp.ui.events.SnackbarEvent
import com.amv.socioapp.ui.events.SnackbarType
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException

sealed interface UsuariosUiState {
    data class Success(val usuarios: List<Usuario>) : UsuariosUiState
    data class Error(val message: String?) : UsuariosUiState
    data class Exception(val e: Throwable) : UsuariosUiState
    object Loading : UsuariosUiState
}

class UsuariosViewModel(private val usuariosRepository: UsuariosRepository) : ViewModel() {
    var usuariosUiState: UsuariosUiState by mutableStateOf(UsuariosUiState.Loading)
        private set

    var seleccionado by mutableStateOf<Usuario?>(null)
        internal set

    var searchQuery by mutableStateOf<String>("")

    var buscados by mutableStateOf<List<Usuario>>(emptyList())
        private set

    init {
        leerTodos()
    }

    fun leerTodos(){
        viewModelScope.launch {
            usuariosUiState = UsuariosUiState.Loading
            usuariosUiState = try {
                when(val response = usuariosRepository.leeTodos()) {
                    is ResponseSuccess -> {
                        when (val content = response.data) {
                            is UsuariosResponse -> {
                                mostrarSnackbar(content.info.message, SnackbarType.INFO)
                                UsuariosUiState.Success(content.result)
                            }
                            else -> throw SerializationException()
                        }
                    }
                    is ResponseError -> {
                        mostrarSnackbar(response.error.message)
                        UsuariosUiState.Error(response.error.message)
                    }
                    else -> throw SerializationException()
                }
            } catch (e: Throwable) {
                UsuariosUiState.Exception(e)
            }
        }
    }

    fun leeUno(id: Int) {
        viewModelScope.launch {
            try {
                when (val response = usuariosRepository.leeUno(id)) {
                    is ResponseSuccess -> {
                        when (val content = response.data) {
                            is UnUsuarioResponse -> {
                                mostrarSnackbar(content.info.message, SnackbarType.SUCCESS)
                                seleccionado = content.result
                                leerTodos()
                            }
                            else -> throw SerializationException()
                        }
                    }
                    is ResponseError -> UsuariosUiState.Error(response.error.message)
                    else -> throw SerializationException()
                }
            } catch (e: Throwable) {
                UsuariosUiState.Exception(e)
            }
        }
    }

    fun buscarUsuario(query: String) {
        viewModelScope.launch {
            try {
                when(val response = usuariosRepository.busca(query)) {
                    is ResponseSuccess -> {
                        when (val content = response.data) {
                            is UsuariosResponse -> {
                                mostrarSnackbar(content.info.message, SnackbarType.SUCCESS)
                                buscados = content.result
                            }
                            else -> throw SerializationException()
                        }
                    }
                    is ResponseError -> {
                        mostrarSnackbar(response.error.message)
                        UsuariosUiState.Error(response.error.message)
                    }
                    else -> throw SerializationException()
                }
            } catch (e: Throwable) {
                throw e
                UsuariosUiState.Exception(e)
            }
        }
    }

    fun creaUno(usuario: UsuarioBodyRequest) {

    }

    fun modificaUno(id: Int, usuario: UsuarioBodyRequest, avatar: PlatformFile? = null) {
        viewModelScope.launch {
            try {
                when (val response = usuariosRepository.actualizaUno(id, usuario)) {
                    is ResponseSuccess -> {
                        when (val content = response.data) {
                            is UnUsuarioResponse -> {
                                mostrarSnackbar(content.info.message, SnackbarType.SUCCESS)
                                if(avatar != null) subirAvatar(id, avatar)
                                leerTodos()
                            }
                            else -> throw SerializationException()
                        }
                    }
                    is ResponseError -> {
                        mostrarSnackbar(response.error.message)
                        UsuariosUiState.Error(response.error.message)
                    }
                    else -> throw SerializationException()
                }
            } catch (e: Throwable) {
                throw e
                UsuariosUiState.Exception(e)
            }
        }
    }

    fun subirAvatar(id: Int, avatar: PlatformFile) {
        viewModelScope.launch {
            val avatarBytes = avatar.readBytes()
            val parts = formData {
                append("avatar", avatarBytes, Headers.build {
                    append(HttpHeaders.ContentType, "image/png")
                    append(HttpHeaders.ContentDisposition, "filename=${avatar.name}")
                })
            }

            try {
                when(val response = usuariosRepository.subirAvatar(id, MultiPartFormDataContent(parts))) {
                    is ResponseSuccess -> {
                        when (val content = response.data) {
                            is SimpleResponse -> {
                            //leerTodos()
                            }
                            else -> throw SerializationException()
                        }
                    }
                    is ResponseError -> {
                        UsuariosUiState.Error(response.error.message)
                    }
                    else -> throw SerializationException()
                }
            } catch (e: Throwable) {
                UsuariosUiState.Exception(e)
            }
        }
    }

    fun borraUno(id: Int) {
        viewModelScope.launch {
            try {
                when (val response = usuariosRepository.borraUno(id)) {
                    is ResponseSuccess -> {
                        when (val content = response.data) {
                            is SimpleResponse -> {
                                if (content.info.numberOfEntriesDeleted == 1) {
                                    mostrarSnackbar(content.info.message, SnackbarType.SUCCESS)
                                    leerTodos()
                                } else {
                                    throw SerializationException()
                                }
                            }
                            else -> throw SerializationException()
                        }
                    }
                    is ResponseError -> {
                        mostrarSnackbar(response.error.message)
                        UsuariosUiState.Error(response.error.message)
                    }
                    else -> throw SerializationException()
                }
            } catch (e: Throwable) {
                UsuariosUiState.Exception(e)
            }
        }
    }

    private fun mostrarSnackbar(mensaje: String, tipo: SnackbarType = SnackbarType.ERROR) {
        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = mensaje,
                    type = tipo
                )
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val usuariosRepository = AppEnvironment.contenedor.usuariosRepository
                UsuariosViewModel(usuariosRepository = usuariosRepository)
            }
        }
    }
}