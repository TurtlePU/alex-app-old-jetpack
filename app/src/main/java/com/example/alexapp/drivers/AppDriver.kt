package com.example.alexapp.drivers

import com.example.alexapp.models.AppModel
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.ui.performance.RatingDriver

interface AppDriver : AuthorizationDriver {
  fun authorized(credentials: Credentials): RatingDriver

  object Example : AppDriver {
    private const val login = AppModel.Example.login
    override fun authorized(credentials: Credentials) = RatingDriver.Example
    override suspend fun authorize(credentials: Credentials) =
      if (credentials.login != login) "Expected login '$login'" else null
  }
}