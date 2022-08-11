package com.example.alexapp.domains

import com.example.alexapp.drivers.AuthorizationDriver
import com.example.alexapp.models.AuthorizationModel
import com.example.alexapp.models.AuthorizationModel.Credentials

interface Authorization : AuthorizationModel, AuthorizationDriver {
  object Example : Authorization {
    private val model = AuthorizationModel.Example
    override val initials get() = model.initials
    override suspend fun remember(credentials: Credentials) = model.remember(credentials)
    override suspend fun authorize(credentials: Credentials) =
      if (credentials.login != model.login) "Expected login '${model.login}'" else null
  }
}