package com.amv.socioapp.network
import com.amv.socioapp.data.SessionManager
import com.amv.socioapp.network.model.AuthResponse
import com.amv.socioapp.network.model.BaseResponse
import com.amv.socioapp.network.model.ContentResponse
import com.amv.socioapp.network.model.ResponseError
import com.amv.socioapp.network.model.ResponseSuccess
import com.amv.socioapp.network.model.SimpleResponse
import com.amv.socioapp.network.model.SociosResponse
import com.amv.socioapp.network.model.UnSocioResponse
import com.amv.socioapp.network.model.UnUsuarioResponse
import com.amv.socioapp.network.model.UsuariosResponse
import com.amv.socioapp.util.getBaseUrl
import com.amv.socioapp.util.getLanguageTag
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

object NetworkUtils {
    private val BASE_URL = getBaseUrl()

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    serializersModule = SerializersModule {
                        polymorphic(BaseResponse::class) {
                            subclass(ResponseSuccess::class, ResponseSuccess.serializer())
                            subclass(ResponseError::class, ResponseError.serializer())
                        }
                        polymorphic(ContentResponse::class) {
                            subclass(SimpleResponse::class, SimpleResponse.serializer())
                            subclass(AuthResponse::class, AuthResponse.serializer())
                            subclass(SociosResponse::class, SociosResponse.serializer())
                            subclass(UnSocioResponse::class, UnSocioResponse.serializer())
                            subclass(UsuariosResponse::class, UsuariosResponse.serializer())
                            subclass(UnUsuarioResponse::class, UnUsuarioResponse.serializer())
                        }
                    }
                }
            )
        }
        install(Auth) {
            bearer {
                loadTokens {
                    SessionManager.obtenerToken()?.let { BearerTokens(it, null) }
                }
                refreshTokens {
                    SessionManager.obtenerToken()?.let { BearerTokens(it, null) }
                }
            }
        }
        HttpResponseValidator {
           validateResponse { response ->
               if(response.status.value == 401 || response.status.value == 403) {
                   SessionManager.cerrarSesion()
               }
           }
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            headers.append("Accept-Language", getLanguageTag())
        }
    }
    val ktorfit: Ktorfit = Ktorfit.Builder()
        .baseUrl(BASE_URL)
        .httpClient(httpClient)
        .build()
}