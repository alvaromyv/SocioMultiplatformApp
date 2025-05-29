package com.amv.socioapp.model

import com.amv.socioapp.MiParcelable
import com.amv.socioapp.MiParcelize

@MiParcelize
data class Socio(
    val dni: String,
    val nSocio: Int,
    val nombre: String,
    val apellidos: String,
    val categoria: Categoria = Categoria.SENIOR,
    val esAbonado: Boolean,
    val direccion: String?,
    val notas: String?
) : MiParcelable {
    fun obtenerNombreCompleto(): String {
        return "$nombre $apellidos"
    }
}

enum class Categoria {
    INFANTIL, SENIOR
}