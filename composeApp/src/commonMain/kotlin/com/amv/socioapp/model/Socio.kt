package com.amv.socioapp.model

data class Socio(
    val dni: String,
    val nSocio: Int,
    val nombre: String,
    val apellidos: String,
    val categoria: Categoria = Categoria.SENIOR,
    val esAbonado: Boolean,
    val direccion: String?,
    val notas: String?
) {
    fun obtenerNombreCompleto(): String {
        return "$nombre $apellidos"
    }

}

enum class Categoria {
    INFANTIL, SENIOR
}