package com.amv.socioapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.amv.socioapp.model.Categoria
import com.amv.socioapp.model.Rol

class InputViewModel : ViewModel() {
    /////////////////////////////////////// ESTADOS ////////////////////////////////////////////////
    private data class UsuarioFormState(
        val nombre: String = "",
        val apellidos: String? = "",
        val telefono: String? = "",
        val email: String = "",
        val password: String = "",
        val rol: Rol = Rol.USUARIO
    )

    private data class SocioFormState(
        val categoria: Categoria = Categoria.ADULTO,
        val abonado: Boolean = false,
        val usuarioId: Int? = null
    )

    private var usuarioFormState by mutableStateOf(UsuarioFormState())

    private var socioFormState by mutableStateOf(SocioFormState())

    ////////////////////////////////////// VALIDACIONES ///////////////////////////////////////////////

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

    ////////////////////////////////////// FUNCIONES ///////////////////////////////////////////////
    fun reiniciar() {
        usuarioFormState = UsuarioFormState()
        socioFormState = SocioFormState()
    }
}