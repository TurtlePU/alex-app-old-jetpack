package com.example.alexapp.domains

import Performance
import com.example.alexapp.drivers.AppDriver
import com.example.alexapp.models.AuthorizationModel
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.models.RestoreModel
import com.example.alexapp.models.RestoreModel.Rating

interface App : AppDriver, AuthorizationModel, RestoreModel {
  class Example(ratings: MutableMap<Performance, Rating>) : App {
    private val auth = Authorization.Example
    private val perf = Performances.Example(ratings)
    override val initials get() = auth.initials
    override suspend fun remember(credentials: Credentials) = auth.remember(credentials)
    override suspend fun authorize(credentials: Credentials) = auth.authorize(credentials)
    override fun restore(performance: Performance) = perf.restore(performance)
    override fun isRated(performance: Performance) = perf.isRated(performance)
    override fun flow(host: String) = perf.flow
    override suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating) {
      perf.rate(performance, rating)
    }
  }
}