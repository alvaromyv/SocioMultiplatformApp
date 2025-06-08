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
import com.amv.socioapp.network.repository.UsuariosRepository
import com.amv.socioapp.model.Usuario
import com.amv.socioapp.network.model.ResponseError
import com.amv.socioapp.network.model.ResponseSuccess
import com.amv.socioapp.network.model.UsuariosResponse
import kotlinx.coroutines.launch

sealed interface UsuariosUiState {
    data class Success(val usuarios: List<Usuario>) : UsuariosUiState
    data class Error(val message: String?) : UsuariosUiState
    data class Exception(val e: Throwable) : UsuariosUiState
    object Loading : UsuariosUiState
}

class UsuariosViewModel(private val usuariosRepository: UsuariosRepository) : ViewModel() {
    var usuariosUiState: UsuariosUiState by mutableStateOf(UsuariosUiState.Loading)
        private set

    init {
        leerTodos()
    }

    fun leerTodos(){
        viewModelScope.launch {
            usuariosUiState = try {
                when(val response = usuariosRepository.obtenerUsuarios()) {
                    is ResponseSuccess -> {
                        when (val content = response.data) {
                            is UsuariosResponse -> UsuariosUiState.Success(content.result)
                            else -> return@launch
                        }
                    }
                    is ResponseError -> UsuariosUiState.Error(response.error.message)
                    else -> return@launch
                }
            } catch (e: Throwable) {
                throw e
                // SociosUiState.Exception(e)
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