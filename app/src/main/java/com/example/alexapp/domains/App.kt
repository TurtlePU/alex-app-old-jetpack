package com.example.alexapp.domains

import Performance
import com.example.alexapp.drivers.AppDriver
import com.example.alexapp.models.AuthorizationModel
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.models.RestoreModel
import com.example.alexapp.models.RestoreModel.Rating

interface App : AppDriver, AuthorizationModel, RestoreModel {
  class OverloadedAuth(private val app: App, private val onSuccess: Credentials.() -> Unit) :
    Authorization {
    override val initials get() = app.initials
    override suspend fun authorize(credentials: Credentials) = app.authorize(credentials)
    override suspend fun remember(credentials: Credentials) {
      app.remember(credentials)
      credentials.onSuccess()
    }
  }

  class CredentialPerformances(private val app: App, private val c: Credentials) : Performances {
    override val flow get() = app.flow(c.host)
    override fun restore(performance: Performance) = app.restore(performance)
    override suspend fun rate(`for`: Performance, rating: Rating) = app.rate(c, `for`, rating)
  }

  class Example(ratings: MutableMap<Performance, Rating>) : App {
    private val auth = Authorization.Example
    private val perf = Performances.Example(ratings)
    override val initials get() = auth.initials
    override suspend fun remember(credentials: Credentials) = auth.remember(credentials)
    override suspend fun authorize(credentials: Credentials) = auth.authorize(credentials)
    override fun restore(performance: Performance) = perf.restore(performance)
    override fun flow(host: String) = perf.flow
    override suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating) {
      perf.rate(performance, rating)
    }
  }
}