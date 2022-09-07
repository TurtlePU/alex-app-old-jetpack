package com.example.alexapp.authorization

import PostAuth
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend fun authorize(client: HttpClient, credentials: Credentials) = try {
  val (host, login, token) = credentials
  val response: HttpResponse = client.post("$host/auth") {
    body = PostAuth(login, token)
  }
  assert(response.status == HttpStatusCode.OK || response.status == HttpStatusCode.Created)
  null
} catch (e: Throwable) {
  e.localizedMessage
}