package com.amv.socioapp.model

import com.amv.socioapp.util.MyParcelable
import com.amv.socioapp.util.MyParcelize
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@MyParcelize
@Serializable
data class Socio(
    val id: Int,
    @SerialName("n_socio") val nSocio: Int,
    @SerialName("fecha_nacimiento") private val _fechaNacimiento: String,
    @SerialName("fecha_antiguedad") private val _fechaAntiguedad: String,
    val categoria: Categoria,
    @SerialName("abonado") val esAbonado: Boolean,
    @SerialName("usuario_id") private val usuarioId: Int,
    val usuario: Usuario,
) : MyParcelable {
    val fechaNacimiento: LocalDateTime
        get() = Instant.parse(_fechaNacimiento).toLocalDateTime(TimeZone.UTC)

    val fechaAntiguedad: LocalDateTime
        get() = Instant.parse(_fechaAntiguedad).toLocalDateTime(TimeZone.UTC)

    companion object{
        @OptIn(FormatStringsInDatetimeFormats::class)
        fun formatearFecha(fecha: LocalDateTime): String {
            return try {
                fecha.format(LocalDateTime.Format { byUnicodePattern("dd/MM/yyyy") })
            } catch (e: Exception) {
                "Desconocida"
            }
        }
    }

}

@MyParcelize
enum class Categoria(val descripcion: String, val cuota: Double): MyParcelable {
    @SerialName("infantil") INFANTIL("Menor de 12 años", 10.0),
    @SerialName("juvenil") JUVENIL("Entre 12 y 17 años", 15.0),
    @SerialName("adulto") ADULTO("Entre 18 y 64 años", 20.0),
    @SerialName("senior") SENIOR("65 años o más", 12.0),
}

