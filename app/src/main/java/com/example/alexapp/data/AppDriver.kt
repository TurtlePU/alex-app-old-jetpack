package com.example.alexapp.data

import Performance
import com.example.alexapp.data.AuthorizationDriver.Credentials
import com.example.alexapp.data.RatingDriver.Rating

interface AppDriver : AuthorizationDriver, RatingDriver {
  companion object {
    fun example(state: MutableMap<Performance, Rating>): AppDriver {
      val auth = AuthorizationDriver.Example
      val rate = RatingDriver.example(state)
      return object : AppDriver {
        override val initialCredentials get() = auth.initialCredentials
        override suspend fun authorizeWith(credentials: Credentials) =
          auth.authorizeWith(credentials)

        override fun restore(performance: Performance) = rate.restore(performance)
        override suspend fun saveRating(performance: Performance, rating: Rating) =
          rate.saveRating(performance, rating)
      }
    }
  }
}