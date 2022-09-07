package com.example.alexapp.app

import com.example.alexapp.authorization.Credentials
import com.example.alexapp.authorization.exampleAuth
import com.example.alexapp.performance.RatingDriver

interface AppDriver {
  fun authorized(credentials: Credentials): RatingDriver
  suspend fun authorize(credentials: Credentials): String?

  object Example : AppDriver {
    override fun authorized(credentials: Credentials) = RatingDriver.Example
    override suspend fun authorize(credentials: Credentials) = exampleAuth(credentials)
  }
}