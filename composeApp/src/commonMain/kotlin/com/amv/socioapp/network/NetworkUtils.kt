package com.amv.socioapp.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkUtils {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    classDiscriminator = "response"
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}