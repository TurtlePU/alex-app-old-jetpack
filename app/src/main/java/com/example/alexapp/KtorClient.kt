package com.example.alexapp

import Protocol
import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

val ktorClient = HttpClient(Android) {
  install(JsonFeature) {
    serializer = KotlinxSerializer(Protocol.json)
  }
  install(Logging) {
    logger = object : Logger {
      override fun log(message: String) {
        Log.i("Network", message)
      }
    }
    level = LogLevel.ALL
  }
  install(HttpTimeout) {
    socketTimeoutMillis = 3_000
    requestTimeoutMillis = 3_000
    connectTimeoutMillis = 3_000
  }
  defaultRequest {
    contentType(ContentType.Application.Json)
    accept(ContentType.Application.Json)
  }
}