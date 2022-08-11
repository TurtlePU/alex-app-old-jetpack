package com.example.alexapp.domains

import Performance
import androidx.paging.PagingData
import com.example.alexapp.domains.Authorization.Credentials
import com.example.alexapp.domains.Performances.Rating
import kotlinx.coroutines.flow.Flow

interface AlexApp {
  suspend fun checkCredentials(credentials: Credentials): String?
  fun flow(host: String): Flow<PagingData<Performance>>
  fun restoreRating(performance: Performance): Rating?
  suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating)

  fun authorization(onSuccess: Credentials.() -> Unit) = object : Authorization {
    override suspend fun check(credentials: Credentials) = checkCredentials(credentials)
    override fun authorizeWith(credentials: Credentials) = onSuccess(credentials)
  }

  fun performances(credentials: Credentials) = object : Performances {
    override val flow: Flow<PagingData<Performance>> get() = flow(credentials.host)
    override fun restore(performance: Performance) = restoreRating(performance)
    override suspend fun rate(performance: Performance, rating: Rating) {
      rate(credentials, performance, rating)
    }
  }

  companion object {
    fun example(ratings: MutableMap<Performance, Rating>): AlexApp {
      val auth = Authorization.Example
      val performances = Performances.example(ratings)
      return object : AlexApp {
        override suspend fun checkCredentials(credentials: Credentials) = auth.check(credentials)
        override fun flow(host: String) = performances.flow
        override fun restoreRating(performance: Performance) = performances.restore(performance)
        override suspend fun rate(
          credentials: Credentials,
          performance: Performance,
          rating: Rating
        ) {
          performances.rate(performance, rating)
        }
      }
    }
  }
}