package com.amv.socioapp.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResponse<out T> {
    @SerialName("ok")
    abstract val ok: String

    @Serializable
    @SerialName("success")
    data class Success<T>(override val ok: String, val data: T, val info: InformationDataResponse) : ApiResponse<T>()

    @Serializable
    @SerialName("error")
    data class Error(override val ok: String, val error: ErrorDataResponse) : ApiResponse<Nothing>()
}

@Serializable
data class InformationDataResponse(val message: String, val numberOfEntriesDeleted: Int?)

@Serializable
data class ErrorDataResponse(val message: String)

