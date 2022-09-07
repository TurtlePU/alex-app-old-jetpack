package com.example.alexapp.performance

import Performance
import PostGrade
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.alexapp.authorization.Credentials
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class NetworkRatings(private val client: HttpClient, private val credentials: Credentials) :
  RatingDriver {
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