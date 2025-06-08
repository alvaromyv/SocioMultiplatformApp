package com.amv.socioapp.ui.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.amv.socioapp.model.Categoria
import com.amv.socioapp.model.Rol

class InputViewModel : ViewModel() {
    /////////////////////////////////////// CONSTANTES /////////////////////////////////////////////
    companion object {
        private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    }

    /////////////////////////////////////// ESTADOS ////////////////////////////////////////////////
    data class UsuarioFormState(
        val nombre: String = "",
        val apellidos: String? = "",
        val telefono: String? = "",
        val email: String = "",
        val password: String = "",
        val rol: Rol = Rol.USUARIO
    )

    data class SocioFormState(
        val categoria: Categoria = Categoria.ADULTO,
        val abonado: Boolean = false,
        val usuarioId: Int? = null
    )

    var usuarioFormState by mutableStateOf(UsuarioFormState())

    var socioFormState by mutableStateOf(SocioFormState())

    var esPasswordVisible by mutableStateOf(false)
        private set

    ////////////////////////////////////// VALIDACIONES ///////////////////////////////////////////////
    val esEmailErroneo by derivedStateOf {
        if (usuarioFormState.email.isNotEmpty()) {
            // El email se considera erroneo sí no encaja con la cadena de un correo por defecto
            !EMAIL_REGEX.matches(usuarioFormState.email)
        } else {
            false
        }
    }

    val esFormularioLoginValido by derivedStateOf {
        if (usuarioFormState.email.isEmpty() || usuarioFormState.password.isEmpty()){
            false
        }else{
            !esEmailErroneo// Comprobamos que el correo sea válido, la contraseña ya se comprueba si está vacía o no
        }
    }

    ////////////////////////////////////// ACTUALIZAR //////////////////////////////////////////////
    fun actualizarNombre(nombre: String) {
        usuarioFormState = usuarioFormState.copy(nombre = nombre)
    }

    fun actualizarApellidos(apellidos: String?) {
        usuarioFormState = usuarioFormState.copy(apellidos = apellidos)
    }

    fun actualizarTelefono(telefono: String?) {
        usuarioFormState = usuarioFormState.copy(telefono = telefono)
    }

    fun actualizarEmail(email: String) {
        usuarioFormState = usuarioFormState.copy(email = email)
    }

    fun actualizarPassword(password: String) {
        usuarioFormState = usuarioFormState.copy(password = password)
    }

    fun actualizarRol(rol: Rol) {
        usuarioFormState = usuarioFormState.copy(rol = rol)
    }

    fun actualizarCategoria(categoria: Categoria) {
        socioFormState = socioFormState.copy(categoria = categoria)
    }

    fun actualizarAbonado(abonado: Boolean) {
        socioFormState = socioFormState.copy(abonado = abonado)
    }

    fun actualizarUsuarioId(id: Int?) {
        socioFormState = socioFormState.copy(usuarioId = id)
    }

    fun actualizarPasswordVisible() {
        esPasswordVisible = !esPasswordVisible
    }

    ////////////////////////////////////// FUNCIONES ///////////////////////////////////////////////
    fun reiniciar() {
        usuarioFormState = UsuarioFormState()
        socioFormState = SocioFormState()
    }
}