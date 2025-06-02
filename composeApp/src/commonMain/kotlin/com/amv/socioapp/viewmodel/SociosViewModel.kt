package com.amv.socioapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.amv.socioapp.AppEnvironment
import com.amv.socioapp.data.SociosRepository
import com.amv.socioapp.model.Socio
import com.amv.socioapp.network.response.ResponseError
import com.amv.socioapp.network.response.ResponseSuccess
import kotlinx.coroutines.launch

sealed interface SociosUiState {
    data class Success(val socios: List<Socio>) : SociosUiState
    data class Error(val message: String?) : SociosUiState
    data class Exception(val e: Throwable) : SociosUiState
    object Loading : SociosUiState
}

class SociosViewModel(private val sociosRepository: SociosRepository) : ViewModel() {
    var sociosUiState: SociosUiState by mutableStateOf(SociosUiState.Loading)
        private set

    init {
        leerTodos()
    }

    fun leerTodos(){
        viewModelScope.launch {
            sociosUiState = try {
                when(val response = sociosRepository.obtenerSocios()) {
                    is ResponseSuccess -> SociosUiState.Success(response.data.result)
                    is ResponseError -> SociosUiState.Error(response.error.message)
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
                val sociosRepository = AppEnvironment.contenedor.sociosRepository
                SociosViewModel(sociosRepository = sociosRepository)
            }
        }
    }
}