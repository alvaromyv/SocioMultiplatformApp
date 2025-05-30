package com.amv.socioapp.model

import com.amv.socioapp.util.MiParcelable
import com.amv.socioapp.util.MiParcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@MiParcelize
@Serializable
data class Socio(
    val id: Int,
    val nSocio: Int,
    @SerialName("fecha_nacimiento")val fechaNacimiento: String,
    @SerialName("fecha_antiguedad") val fechaAntiguedad: String,
    val categoria: Categoria,
    @SerialName("abonado") val esAbonado: Boolean,
    val direccion: String?,
    val urlImagen: String?,
    @SerialName("invitado_por") val invitadoPor: Int?,
    @SerialName("usuario_id") val idUsuario: Int?
) : MiParcelable {

}

enum class Categoria(private val descripcion: String, private val cuota: Double) {
    @SerialName("infantil") INFANTIL("Menor de 12 años", 10.0),
    @SerialName("juvenil") JUVENIL("Entre 12 y 17 años", 15.0),
    @SerialName("adulto") ADULTO("Entre 18 y 64 años", 20.0),
    @SerialName("senior") SENIOR("65 años o más", 12.0),
}