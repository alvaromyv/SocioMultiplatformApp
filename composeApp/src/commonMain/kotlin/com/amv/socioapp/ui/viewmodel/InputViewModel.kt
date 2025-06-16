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
import com.amv.socioapp.network.model.SocioBodyRequest
import com.amv.socioapp.network.model.SocioRequest
import com.amv.socioapp.network.model.UsuarioBodyRequest
import com.amv.socioapp.network.model.UsuarioRequest
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

class InputViewModel : ViewModel() {
    /////////////////////////////////////// CONSTANTES /////////////////////////////////////////////
    companion object {
        val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        val PHONE_REGEX = Regex("^[0-9]{9}$")
        const val MAX_STRING_LENGTH = 50
        const val MIN_PASSWORD_LENGTH = 8
    }

    /////////////////////////////////////// ESTADOS ////////////////////////////////////////////////
    data class UsuarioFormState(
        val id: Int? = null,
        val avatarUri: PlatformFile? = null,
        val avatarUrl: String? = null,
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
        val fechaAntiguedad: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        val abonado: Boolean = false,
        val usuarioId: Int? = null
    )

    var usuarioFormState by mutableStateOf(UsuarioFormState())

    var socioFormState by mutableStateOf(SocioFormState())

    var haySocio by mutableStateOf(false)

    var esPasswordVisible by mutableStateOf(false)
        private set

    ////////////////////////////////////// VALIDACIONES ///////////////////////////////////////////////
    val esNombreErroneo by derivedStateOf {
        if (usuarioFormState.nombre.isNotEmpty()) {
            // El nombre se considera erroneo sí excede los 50 caracteres
            usuarioFormState.nombre.length > MAX_STRING_LENGTH
        } else {
            false
        }
    }

    val esApellidosErroneo by derivedStateOf {
        if (usuarioFormState.apellidos.isNullOrEmpty()) {
            false
        } else {
            // El apellido se considera erroneo sí excede los 50 caracteres
            usuarioFormState.apellidos!!.length > MAX_STRING_LENGTH
        }
    }

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

    val esPasswordErroneo by derivedStateOf {
        if (usuarioFormState.password.isNotEmpty()) {
            // La contraseña se considera erroneo sí tiene menos de 8 caracteres
            usuarioFormState.password.length < MIN_PASSWORD_LENGTH
        } else {
            false
        }
    }

    val esFechaAntiguedadErroneo by derivedStateOf {
        esFechaFutura(socioFormState.fechaAntiguedad)
    }

    val esFormularioValidado by derivedStateOf {
        if (usuarioFormState.nombre.isEmpty() || usuarioFormState.email.isEmpty() || usuarioFormState.password.isEmpty()) {
            false
        } else {
            // Si todos los campos están correctos, el formulario es válido
            !esNombreErroneo && !esApellidosErroneo && !esTelefonoErroneo && !esEmailErroneo && !esFechaAntiguedadErroneo && !esPasswordErroneo
        }
    }

    val esFormularioLoginValido by derivedStateOf {
        if (usuarioFormState.email.isEmpty() || usuarioFormState.password.isEmpty()){
            false
        }else{
            !esEmailErroneo// Comprobamos que el correo sea válido, la contraseña ya se comprueba si está vacía o no
        }
    }

    fun esFechaFutura(fecha: LocalDateTime): Boolean {
       return fecha > Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    ////////////////////////////////////// ACTUALIZAR //////////////////////////////////////////////
    fun actualizarAvatarUri(uri: PlatformFile?) {
        usuarioFormState = usuarioFormState.copy(avatarUri = uri)
    }

    fun actualizarAvatarUrl(url: String?) {
        usuarioFormState = usuarioFormState.copy(avatarUrl = url)
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

    fun actualizarHaySocio(hay: Boolean) {
        if(!hay) reiniciarValoresSocio()
        haySocio = hay
    }
    fun actualizarUsuarioId(id: Int) {
        socioFormState = socioFormState.copy(usuarioId = id)
    }

    ////////////////////////////////////// FUNCIONES ///////////////////////////////////////////////
    fun cargarSocio(socio: Socio) {
        haySocio = true
        socioFormState = SocioFormState(
            id = socio.id,
            categoria = socio.categoria,
            fechaAntiguedad = socio.fechaAntiguedad,
            abonado = socio.esAbonado,
            usuarioId = socio.usuarioId
        )
    }

    fun cargarUsuario(usuario: Usuario) {
        usuarioFormState = UsuarioFormState(
            id = usuario.id,
            avatarUrl = usuario.avatarUrl,
            nombre = usuario.nombre,
            apellidos = usuario.apellidos,
            telefono = usuario.telefono,
            email = usuario.email,
            password = usuario.password,
            rol = usuario.rol
        )
        actualizarHaySocio(usuario.socio != null)
        if(haySocio) { cargarSocio(usuario.socio!!) }
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    fun construirSocio(): SocioRequest? {
        return if(haySocio) SocioRequest(
            id = socioFormState.id,
            socio = SocioBodyRequest(
                categoria = socioFormState.categoria,
                fechaAntiguedad = socioFormState.fechaAntiguedad.date.toString()/*.format( LocalDate.Format { byUnicodePattern("yyyy-MM-dd") })*/,
                abonado = socioFormState.abonado,
                usuarioId = socioFormState.usuarioId ?: usuarioFormState.id
            )
        ) else null
    }

    fun construirUsuario(): UsuarioRequest {
        return UsuarioRequest(
            id = usuarioFormState.id,
            usuario = UsuarioBodyRequest(
                nombre = usuarioFormState.nombre,
                apellidos = usuarioFormState.apellidos,
                telefono = usuarioFormState.telefono,
                email = usuarioFormState.email,
                password = usuarioFormState.password,
                rol = usuarioFormState.rol,
            ),
            avatar = usuarioFormState.avatarUri
        )
    }
    fun construirUsuarioVacio(): Usuario {
        return Usuario(
            id = 0,
            avatarUrl = "uploads/default-avatar.webp",
            nombre = "",
            apellidos = "",
            telefono = "",
            email = "",
            password = "",
            rol = Rol.USUARIO,
            socio = null
        )
    }
    fun reiniciarValores() {
        usuarioFormState = UsuarioFormState()
        socioFormState = SocioFormState()
    }

    fun reiniciarValoresSocio() {
        socioFormState = SocioFormState()
    }
}