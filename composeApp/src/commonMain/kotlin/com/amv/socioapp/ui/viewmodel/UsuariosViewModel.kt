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
import com.amv.socioapp.network.repository.UsuariosRepository
import com.amv.socioapp.model.Usuario
import com.amv.socioapp.network.model.ResponseError
import com.amv.socioapp.network.model.ResponseSuccess
import com.amv.socioapp.network.model.SimpleResponse
import com.amv.socioapp.network.model.UnSocioResponse
import com.amv.socioapp.network.model.UnUsuarioResponse
import com.amv.socioapp.network.model.UsuariosResponse
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
                            is UsuariosResponse -> UsuariosUiState.Success(content.result)
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

    fun leeUno(id: Int) {
        viewModelScope.launch {
            try {
                when (val response = usuariosRepository.leeUno(id)) {
                    is ResponseSuccess -> {
                        when (val content = response.data) {
                            is UnUsuarioResponse -> {
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
                            is UsuariosResponse -> buscados = content.result
                            else -> throw SerializationException()
                        }
                    }
                    is ResponseError -> UsuariosUiState.Error(response.error.message)
                    else -> throw SerializationException()
                }
            } catch (e: Throwable) {
                throw e
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
                                    leerTodos()
                                } else {
                                    throw SerializationException()
                                }
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val usuariosRepository = AppEnvironment.contenedor.usuariosRepository
                UsuariosViewModel(usuariosRepository = usuariosRepository)
            }
        }
    }
}