package com.example.alexapp.drivers

import Performance
import PostAuth
import PostGrade
import Protocol
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.performance.Rating
import com.example.alexapp.performance.RatingDriver
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

object NetworkDriver : AppDriver {
  private val client = HttpClient(CIO) {
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

  private class Authorized(private val credentials: Credentials) : RatingDriver {
    override val performances = Pager(PagingConfig(100)) {
      object : PagingSource<Int, Performance>() {
        override fun getRefreshKey(state: PagingState<Int, Performance>): Int? {
          TODO("Not yet implemented")
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Performance> {
          TODO("Not yet implemented")
        }
      }
    }.flow

    override suspend fun rate(performance: Performance, rating: Rating) {
      val (host, login, token) = credentials
      val (grade, comment) = rating
      val response: HttpResponse = client.post("$host/grade") {
        body = PostGrade(login, token, performance, grade, comment)
      }
      assert(response.status == HttpStatusCode.OK)
    }
  }

  override fun authorized(credentials: Credentials): RatingDriver = Authorized(credentials)

  override suspend fun authorize(credentials: Credentials) = try {
    val (host, login, token) = credentials
    val response: HttpResponse = client.post("$host/auth") {
      body = PostAuth(login, token)
    }
    assert(response.status == HttpStatusCode.OK || response.status == HttpStatusCode.Created)
    null
  } catch (e: Throwable) {
    e.localizedMessage
  }
}