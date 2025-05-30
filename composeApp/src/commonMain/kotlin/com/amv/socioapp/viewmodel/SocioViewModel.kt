package com.amv.socioapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.amv.socioapp.AppEnvironment
import com.amv.socioapp.data.SociosRepository
import com.amv.socioapp.model.Socio
import kotlinx.coroutines.launch

sealed interface SociosUiState {
    data class Success(val socios: List<Socio>) : SociosUiState
    data class Error(val code: Int, val message: String?) : SociosUiState
    data class Exception(val e: Throwable) : SociosUiState
    object Loading : SociosUiState
}

class SociosViewModel(private val sociosRepository: SociosRepository) : ViewModel() {
    val sociosUiState: SociosUiState by mutableStateOf(SociosUiState.Loading)
        private set

    fun leerTodos(){
        viewModelScope.launch {
            val response = sociosRepository.obtenerSocios()
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