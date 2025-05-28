package com.amv.socioapp.model

data class Socio(
    val dni: String,
    val nSocio: Int,
    val nombre: String,
    val apellidos: String,
    val fechaNac: Long,
    val categoria: Categoria = Categoria.SENIOR,
    val esAbonado: Boolean,
    val direccion: String?,
    val urlImagen: String?,
    val notas: String?
)

enum class Categoria(val nombre: String, val cuota: Double) {
    INFANTIL("Infantil", 10.0),
    SENIOR("Senior", 25.0),
    HONORIFICO("Socio Honorífico", 0.0);
}