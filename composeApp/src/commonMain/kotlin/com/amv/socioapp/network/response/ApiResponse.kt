package com.amv.socioapp.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResponse<out T> {
    @SerialName("status")
    abstract val ok: String

    @Serializable
    @SerialName("success")
    data class Success<T>(override val ok: String, val data: SuccessDataResponse<T>) : ApiResponse<T>()

    @Serializable
    @SerialName("error")
    data class Error(override val ok: String, val error: ErrorDataResponse) : ApiResponse<Nothing>()
}

@Serializable
data class SuccessDataResponse<T>(val result: T, val info: InformationDataResponse)

@Serializable
data class InformationDataResponse(val message: String, val numberOfEntriesDeleted: Int?)

@Serializable
data class ErrorDataResponse(val message: String)

