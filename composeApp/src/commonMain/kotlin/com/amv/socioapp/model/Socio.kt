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
    @SerialName("fecha_antiguedad") private val _fechaAntiguedad: String,
    val categoria: Categoria,
    @SerialName("abonado") val esAbonado: Boolean,
    @SerialName("usuario_id") internal val usuarioId: Int,
    val usuario: Usuario? = null,
) : MyParcelable {
    val fechaAntiguedad: LocalDateTime
        get() = Instant.parse(_fechaAntiguedad).toLocalDateTime(TimeZone.UTC)

    fun obtenerNumeracionFormateada(): String {
        return "Nº: ${nSocio.toString().padStart(3, '0')}"
    }

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
enum class Categoria(): MyParcelable {
    @SerialName("infantil") INFANTIL,
    @SerialName("juvenil") JUVENIL,
    @SerialName("adulto") ADULTO,
    @SerialName("senior") SENIOR,
}

