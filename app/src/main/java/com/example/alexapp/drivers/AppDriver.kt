package com.example.alexapp.drivers

import com.example.alexapp.models.AuthorizationModel
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.performance.RatingDriver

interface AppDriver {
  fun authorized(credentials: Credentials): RatingDriver
  suspend fun authorize(credentials: Credentials): String?

  object Example : AppDriver {
    private const val login = AuthorizationModel.Example.login
    override fun authorized(credentials: Credentials) = RatingDriver.Example
    override suspend fun authorize(credentials: Credentials) =
      if (credentials.login != login) "Expected login '$login'" else null
  }
}