package com.amv.socioapp.network

import com.amv.socioapp.network.response.BaseResponse
import com.amv.socioapp.network.response.ResponseError
import com.amv.socioapp.network.response.ResponseSuccess
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
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    classDiscriminator = "status"
                    ignoreUnknownKeys = true
                    serializersModule = SerializersModule {
                        polymorphic(BaseResponse::class) {
                            subclass(ResponseSuccess::class, ResponseSuccess.serializer())
                            subclass(ResponseError::class, ResponseError.serializer())
                        }
                    }
                }
            )
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJyb290QGdtYWlsLmNvbSIsImlhdCI6MTc0ODg2MjgyMywiZXhwIjoxNzQ4ODY2NDIzfQ.M7MjZuIUJOd6cXgwshBLquUh51QYn8M1lKr20cmdZKQ", null)
                }
            }
        }
    }
}

// Sevilla FC: id 559 https://api.football-data.org/v4/teams/559
// X-Auth-Token: 7424fb04a82940418956b93ce9468f7a