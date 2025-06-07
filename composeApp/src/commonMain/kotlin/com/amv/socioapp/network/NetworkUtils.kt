package com.amv.socioapp.network
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
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

object NetworkUtils {
    private const val BASE_URL =
        //"http://10.0.2.2:3000/"
        "http://localhost:3000/"

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
                    BearerTokens("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJyb290QGdtYWlsLmNvbSIsImlhdCI6MTc0OTMxODk4NCwiZXhwIjoxNzQ5NTM0OTg0fQ.HTZsuz2l1G0jKAgRQuylXitFxCFrOX5jh-ZJJpj-03E", null)
                }
            }
        }
    }
    val ktorfit: Ktorfit = Ktorfit.Builder()
        .baseUrl(BASE_URL)
        .httpClient(httpClient)
        .build()
}

// Sevilla FC: id 559 https://api.football-data.org/v4/teams/559
// X-Auth-Token: 7424fb04a82940418956b93ce9468f7a