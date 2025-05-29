package com.amv.socioapp.model

import com.amv.socioapp.MiParcelable
import com.amv.socioapp.MiParcelize

@MiParcelize
data class Socio(
    val dni: String,
    val nSocio: Int,
    val nombre: String,
    val apellidos: String,
    val fechaNacimiento: String?,
    val categoria: Categoria,
    val esAbonado: Boolean,
    val direccion: String?,
    val urlImagen: String?
) : MiParcelable {
    fun obtenerNombreCompleto(): String {
        return "$nombre $apellidos"
    }
}

enum class Categoria(private val descripcion: String, private val cuota: Double) {
    INFANTIL("Infantil", 10.0),
    ADULTO("Adulto", 20.0),
    SENIOR("Senior", 15.0)
}