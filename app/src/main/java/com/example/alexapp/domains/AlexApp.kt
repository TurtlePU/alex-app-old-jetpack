package com.example.alexapp.domains

import Performance
import androidx.paging.PagingData
import com.example.alexapp.data.AuthorizationDriver.Credentials
import com.example.alexapp.data.RatingDriver.Rating
import kotlinx.coroutines.flow.Flow

interface AlexApp : Authorization {
  fun flow(host: String): Flow<PagingData<Performance>>
  fun restoreRating(performance: Performance): Flow<Rating?>
  suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating)

  fun authorization(onSuccess: Credentials.() -> Unit) = object : Authorization {
    override val initialCredentials get() = this@AlexApp.initialCredentials
    override suspend fun authorizeWith(credentials: Credentials) {
      this@AlexApp.authorizeWith(credentials)
      credentials.onSuccess()
    }

    override suspend fun checkCredentials(credentials: Credentials) =
      this@AlexApp.checkCredentials(credentials)
  }

  fun performances(credentials: Credentials) = object : Performances {
    override val flow get() = flow(credentials.host)
    override fun restore(performance: Performance) = restoreRating(performance)
    override suspend fun saveRating(performance: Performance, rating: Rating) =
      rate(credentials, performance, rating)
  }

  companion object {
    fun example(ratings: MutableMap<Performance, Rating>): AlexApp {
      val auth = Authorization.Example
      val performances = Performances.example(ratings)
      return object : AlexApp {
        override val initialCredentials get() = auth.initialCredentials
        override suspend fun authorizeWith(credentials: Credentials) =
          auth.authorizeWith(credentials)

        override suspend fun checkCredentials(credentials: Credentials) =
          auth.checkCredentials(credentials)

        override fun flow(host: String) = performances.flow
        override fun restoreRating(performance: Performance) = performances.restore(performance)
        override suspend fun rate(
          credentials: Credentials,
          performance: Performance,
          rating: Rating
        ) = performances.saveRating(performance, rating)
      }
    }
  }
}