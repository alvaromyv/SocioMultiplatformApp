package com.amv.socioapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.amv.socioapp.util.MyParcelable
import com.amv.socioapp.util.MyParcelize
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@MyParcelize
@Serializable
data class Socio(
    val id: Int,
    @SerialName("n_socio") val nSocio: Int,
//    @SerialName("fecha_nacimiento") private val fechaNacimientoMillis: Long,
//    @SerialName("fecha_antiguedad") private val fechaAntiguedadMillis: Long,
    val categoria: Categoria,
    @SerialName("abonado") val esAbonado: Boolean,
    @SerialName("usuario_id") val usuarioId: Int?,
    val usuario: Usuario
) : MyParcelable {
//    val fechaNacimiento = Instant.fromEpochMilliseconds(fechaNacimientoMillis).toLocalDateTime(TimeZone.UTC)
//    val fechaAntiguedad = Instant.fromEpochMilliseconds(fechaAntiguedadMillis).toLocalDateTime(TimeZone.UTC)

}

@MyParcelize
enum class Categoria(val descripcion: String, val cuota: Double): MyParcelable {
    @SerialName("infantil") INFANTIL("Menor de 12 años", 10.0),
    @SerialName("juvenil") JUVENIL("Entre 12 y 17 años", 15.0),
    @SerialName("adulto") ADULTO("Entre 18 y 64 años", 20.0),
    @SerialName("senior") SENIOR("65 años o más", 12.0),
}

