package com.amv.socioapp.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkUtils {
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    classDiscriminator = "status"
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens("XXX", "XXX")
                }
            }
        }
    }
}

// Sevilla FC: id 559 https://api.football-data.org/v4/teams/559
// X-Auth-Token: 7424fb04a82940418956b93ce9468f7a