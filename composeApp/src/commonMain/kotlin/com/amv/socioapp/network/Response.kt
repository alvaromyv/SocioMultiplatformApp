package com.amv.socioapp.network

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

/*
* Respuesta de éxito. Contiene un campo data que alberga el contenido del resultado de la respuesta
 */
@Serializable @SerialName("success")
data class ResponseSuccess(val data: ContentResponse): BaseResponse()

/*
* Respuesta de ERROR
 */
@Serializable @SerialName("error") data class ResponseError(val error: ErrorDataResponse): BaseResponse()

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
@Serializable @SerialName("socios") data class SociosResponse(val result: List<Socio>, val info: InformationDataResponse): ContentResponse()
@Serializable data class UnSocioResponse(val result: Socio, val info: InformationDataResponse): ContentResponse()
@Serializable data class UsuariosResponse(val result: List<Usuario>, val info: InformationDataResponse): ContentResponse()
@Serializable data class UnUsuarioResponse(val result: Usuario, val info: InformationDataResponse): ContentResponse()

/*
* Información de la respuesta SUCCESS
*/
@Serializable
data class InformationDataResponse(val message: String, val numberOfEntriesAffected: Int? = null)

/*
* Información de la respuesta ERROR
*/
@Serializable
data class ErrorDataResponse(val message: String)