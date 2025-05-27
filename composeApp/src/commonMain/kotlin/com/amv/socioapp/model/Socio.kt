package com.amv.socioapp.model

import com.google.firebase.firestore.DocumentId

data class Socio(
    @DocumentId val dni: String = "",
    val nSocio: Int = 0,
    val nombre: String = "",
    val apellidos: String = "",
    val fechaNac: Long? = "",
    val categoria: Categoria = Categoria.SENIOR,
    val esAbonado: Boolean = false,
    val direccion: String? = null,
    val urlImagen: String? = null,
    val notas: String? = null
)

enum class Categoria(val nombre: String, val cuota: Double) {
    INFANTIL("Infantil", 10.0),
    SENIOR("Senior", 25.0),
    HONORIFICO("Socio Honorífico", 0.0);
}