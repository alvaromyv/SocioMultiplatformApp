package com.amv.socioapp.network.response

import com.amv.socioapp.model.Socio
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//@Serializable
//@Polymorphic
//sealed class ApiResponse<out T> {
//    @SerialName("status")
//    abstract val ok: String
//
//    @Serializable
//    @SerialName("success")
//    data class Success<T>(override val ok: String, val data: ResponseData<T>) : ApiResponse<T>()
//
//    @Serializable
//    @SerialName("error")
//    data class Error(override val ok: String, val error: ErrorDataResponse) : ApiResponse<Nothing>()
//}

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
@Serializable @Polymorphic
abstract class BaseResponse

@Serializable @SerialName("success")
data class ResponseSuccess(val data: ResponseData): BaseResponse()

@Serializable @SerialName("error") data class ResponseError(val error: ErrorDataResponse): BaseResponse()

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

@Serializable data class ResponseData(val result: List<Socio>, val info: InformationDataResponse)

@Serializable
data class InformationDataResponse(val message: String, val numberOfEntriesAffected: Int? = null)

@Serializable
data class ErrorDataResponse(val message: String)