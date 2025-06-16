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
import com.amv.socioapp.model.Socio
import com.amv.socioapp.network.model.ResponseError
import com.amv.socioapp.network.model.ResponseSuccess
import com.amv.socioapp.network.model.SimpleResponse
import com.amv.socioapp.network.model.SocioBodyRequest
import com.amv.socioapp.network.model.UnSocioResponse
import com.amv.socioapp.network.repository.SociosRepository
import com.amv.socioapp.ui.events.SnackbarController
import com.amv.socioapp.ui.events.SnackbarEvent
import com.amv.socioapp.ui.events.SnackbarType
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException

sealed interface SociosUiState {
    data class Success(val socios: List<Socio>) : SociosUiState
    data class Error(val message: String?) : SociosUiState
    data class Exception(val e: Throwable) : SociosUiState
    object Loading : SociosUiState
}

class SociosViewModel(private val sociosRepository: SociosRepository) : ViewModel() {
    var sociosUiState: SociosUiState by mutableStateOf(SociosUiState.Loading)
        private set

//    fun leerTodos() {
//        viewModelScope.launch {
//            sociosUiState = SociosUiState.Loading
//            sociosUiState = try {
//                when (val response = sociosRepository.leerTodos()) {
//                    is ResponseSuccess -> {
//                        when (val content = response.data) {
//                            is SociosResponse -> {
//                                mostrarSnackbar(content.info.message, SnackbarType.INFO)
//                                SociosUiState.Success(content.result)
//                            }
//                            else -> throw SerializationException()
//                        }
//                    }
//                    is ResponseError -> {
//                        mostrarSnackbar(response.error.message)
//                        SociosUiState.Error(response.error.message)
//                    }
//                    else -> throw SerializationException()
//                }
//            } catch (e: Throwable) {
//                SociosUiState.Exception(e)
//            }
//        }
//    }

    fun creaUno(socio: SocioBodyRequest, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                when (val response = sociosRepository.creaUno(socio)) {is ResponseSuccess -> {
                        when (val content = response.data) {
                            is UnSocioResponse -> {
                                mostrarSnackbar(content.info.message, SnackbarType.SUCCESS)
                                onSuccess()
                            }
                            else -> throw SerializationException()
                        }
                    }
                    is ResponseError -> {
                        mostrarSnackbar(response.error.message)
                        SociosUiState.Error(response.error.message)
                    }
                    else -> throw SerializationException()
                }
            } catch (e: Throwable) {
                SociosUiState.Exception(e)
            }
        }
    }

    fun modificaUno(id: Int, socio: SocioBodyRequest) {
        viewModelScope.launch {
            try {
                when (val response = sociosRepository.actualizaUno(id, socio)) {
                    is ResponseSuccess -> {
                        when (val content = response.data) {
                            is UnSocioResponse -> {
                                mostrarSnackbar(content.info.message, SnackbarType.SUCCESS)
                                // leerTodos()
                            }
                            else -> throw SerializationException()
                        }
                    }
                    is ResponseError -> {
                        mostrarSnackbar(response.error.message)
                        SociosUiState.Error(response.error.message)
                    }
                    else -> throw SerializationException()
                }
            } catch (e: Throwable) {
                SociosUiState.Exception(e)
            }
        }
    }

    fun reasignarNumeracion(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                when (val response = sociosRepository.reasignarNumeracion()) {
                    is ResponseSuccess -> {
                        when (val content = response.data) {
                            is SimpleResponse -> {
                                mostrarSnackbar(content.info.message, SnackbarType.SUCCESS)
                                onSuccess()
                                // leerTodos()
                            }
                            else -> throw SerializationException()
                        }
                    }
                    is ResponseError -> {
                        mostrarSnackbar(response.error.message)
                        SociosUiState.Error(response.error.message)
                    }
                }
            } catch (e: Throwable) {
                SociosUiState.Exception(e)
            }
        }
    }

    fun borraUno(id: Int) {
        viewModelScope.launch {
            try {
                when (val response = sociosRepository.borraUno(id)) {
                    is ResponseSuccess -> {
                        when (val content = response.data) {
                            is SimpleResponse -> {
                                if (content.info.numberOfEntriesDeleted == 1) {
                                    mostrarSnackbar(content.info.message, SnackbarType.SUCCESS)
                                    // leerTodos()
                                } else {
                                    throw SerializationException()
                                }
                            }
                            else -> throw SerializationException()
                        }
                    }
                    is ResponseError -> {
                        mostrarSnackbar(response.error.message)
                        SociosUiState.Error(response.error.message)
                    }
                    else -> throw SerializationException()
                }
            } catch (e: Throwable) {
                SociosUiState.Exception(e)
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
                val sociosRepository = AppEnvironment.contenedor.sociosRepository
                SociosViewModel(sociosRepository = sociosRepository)
            }
        }
    }
}