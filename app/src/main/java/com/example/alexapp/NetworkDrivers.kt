package com.example.alexapp

import GetQueue
import Performance
import PostAuth
import PostGrade
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
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

class NetworkRatings(
  private val client: HttpClient,
  private val credentials: Credentials,
  private val accumulated: MutableList<Performance>,
) : RatingDriver {
  override val performances = Pager(PagingConfig(100)) {
    object : PagingSource<Int, Performance>() {
      override fun getRefreshKey(state: PagingState<Int, Performance>) = state.run {
        ((anchorPosition ?: 0) - config.initialLoadSize / 2).coerceAtLeast(0)
      }

      override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Performance> {
        val response: List<Performance> = client.get("${credentials.host}/queue") {
          body = GetQueue(since = accumulated.size)
        }
        accumulated.addAll(response)
        return LoadResult.Page(accumulated, null, null)
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