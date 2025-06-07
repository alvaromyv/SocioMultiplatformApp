package com.amv.socioapp.network.model

import com.amv.socioapp.model.Socio
import com.amv.socioapp.model.Usuario
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/*
* Clase base para todas las respuestas de la API
* Es @Polymorphic debido a que usa "status" como discriminador para saber el tipo de respuesta si es Success o Error
*/
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("status") @Serializable @Polymorphic
abstract class BaseResponse

@Serializable @SerialName("success")
data class ResponseSuccess(val data: ContentResponse): BaseResponse()

@Serializable @SerialName("error")
data class ResponseError(val error: ErrorDataResponse): BaseResponse()

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

/*
* Superclase para albelgar el contenido de respuesta de la API
* Es @Polymorphic debido a que tiene varios subtipos de respuesta en función de la petición que hagamos
*/
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type") @Serializable @Polymorphic
abstract class ContentResponse

/*
* Clases que contienen la información que hayamos solicitado en funciónd de su tipo
*/
@Serializable @SerialName("simple") data class SimpleResponse(val info: InformationDataResponse): ContentResponse()
@Serializable @SerialName("auth") data class AuthResponse(val result: AuthDataResponse, val info: InformationDataResponse): ContentResponse()
@Serializable @SerialName("socios") data class SociosResponse(val result: List<Socio>, val info: InformationDataResponse): ContentResponse()
@Serializable @SerialName("socio") data class UnSocioResponse(val result: Socio, val info: InformationDataResponse): ContentResponse()
@Serializable @SerialName("usuarios") data class UsuariosResponse(val result: List<Usuario>, val info: InformationDataResponse): ContentResponse()
@Serializable @SerialName("usuario") data class UnUsuarioResponse(val result: Usuario, val info: InformationDataResponse): ContentResponse()

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

@Serializable
data class AuthDataResponse(
    val usuario: Usuario,
    val token: String,
    val expiraEn: Long
)

// Información de la respuesta SUCCESS
@Serializable
data class InformationDataResponse(
    val message: String,
    val numberOfEntriesDeleted: Int? = null
)

// Información de la respuest ERROR
@Serializable
data class ErrorDataResponse(val message: String)