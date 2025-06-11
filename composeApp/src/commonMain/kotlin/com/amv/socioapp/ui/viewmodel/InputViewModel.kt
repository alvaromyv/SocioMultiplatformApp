package com.amv.socioapp.ui.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.amv.socioapp.model.Categoria
import com.amv.socioapp.model.Rol
import com.amv.socioapp.model.Socio
import com.amv.socioapp.model.Usuario
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName

class InputViewModel : ViewModel() {
    /////////////////////////////////////// CONSTANTES /////////////////////////////////////////////
    companion object {
        private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        private val PHONE_REGEX = Regex("^[0-9]{9}$")
        private const val MIN_PASSWORD_LENGTH = 8
    }

    /////////////////////////////////////// ESTADOS ////////////////////////////////////////////////
    data class UsuarioFormState(
        val id: Int? = null,
        val avatar: PlatformFile? = null,
        val nombre: String = "",
        val apellidos: String? = "",
        val telefono: String? = "",
        val email: String = "",
        val password: String = "",
        val rol: Rol = Rol.USUARIO
    )

    data class SocioFormState(
        val id: Int? = null,
        val categoria: Categoria = Categoria.ADULTO,
        val fechaNacimiento: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        val fechaAntiguedad: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
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

    val esTelefonoErroneo by derivedStateOf {
        if (usuarioFormState.telefono.isNullOrEmpty()) {
            false
        } else {
            // El telefono se considera erroneo sí no encaja con la cadena de un telefono por defecto
            !PHONE_REGEX.matches(usuarioFormState.telefono!!)
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
    fun actualizarAvatar(avatar: PlatformFile?) {
        usuarioFormState = usuarioFormState.copy(avatar = avatar)
    }

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

    fun actualizarFechaNacimiento(fechaNacimiento: LocalDateTime) {
        socioFormState = socioFormState.copy(fechaNacimiento = fechaNacimiento)
    }

    fun actualizarFechaAntiguedad(fechaAntiguedad: LocalDateTime) {
        socioFormState = socioFormState.copy(fechaAntiguedad = fechaAntiguedad)
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

    fun actualizarUsuarioId(id: Int) {
        socioFormState = socioFormState.copy(usuarioId = id)
    }

    ////////////////////////////////////// FUNCIONES ///////////////////////////////////////////////
    fun cargarSocio(socio: Socio) {
        socioFormState = SocioFormState(
            categoria = socio.categoria,
            fechaNacimiento = socio.fechaNacimiento,
            fechaAntiguedad = socio.fechaAntiguedad,
            abonado = socio.esAbonado,
            usuarioId = socio.usuarioId
        )
        cargarUsuario(socio.usuario)
    }

    fun cargarUsuario(usuario: Usuario) {
        usuarioFormState = UsuarioFormState(
            id = usuario.id,
            //avatar = PlatformFile(usuario.avatarUrl),
            nombre = usuario.nombre,
            apellidos = usuario.apellidos,
            telefono = usuario.telefono,
            email = usuario.email,
            password = usuario.password,
            rol = usuario.rol
        )
    }

    fun crearSocio() {

    }

    fun crearUsuario() {

    }

    fun reiniciarValores() {
        usuarioFormState = UsuarioFormState()
        socioFormState = SocioFormState()
    }
}