package com.example.alexapp.domains

import Performance
import androidx.paging.PagingData
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.models.RatingModel.Rating
import kotlinx.coroutines.flow.Flow

interface App : Authorization {
  fun flow(host: String): Flow<PagingData<Performance>>
  fun restoreRating(performance: Performance): Flow<Rating?>
  suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating)

  class OverloadedAuth(private val app: App, private val onSuccess: Credentials.() -> Unit) :
    Authorization {
    override val initialCredentials get() = app.initialCredentials
    override suspend fun authorizeWith(credentials: Credentials) {
      app.authorizeWith(credentials)
      credentials.onSuccess()
    }

    override suspend fun checkCredentials(credentials: Credentials) =
      app.checkCredentials(credentials)
  }

  class CredentialPerformances(private val app: App, private val credentials: Credentials) :
    Performances {
    override val flow get() = app.flow(credentials.host)
    override fun restore(performance: Performance) = app.restoreRating(performance)
    override suspend fun saveRating(performance: Performance, rating: Rating) =
      app.rate(credentials, performance, rating)
  }

  class Example(ratings: MutableMap<Performance, Rating>) : App, Performances.Example(ratings) {
    private val auth = Authorization.Example
    override val initialCredentials get() = auth.initialCredentials
    override suspend fun authorizeWith(credentials: Credentials) = auth.authorizeWith(credentials)
    override suspend fun checkCredentials(credentials: Credentials) =
      auth.checkCredentials(credentials)

    override fun flow(host: String) = flow
    override fun restoreRating(performance: Performance) = restore(performance)
    override suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating) =
      saveRating(performance, rating)
  }
}