package com.example.alexapp.domains

import com.example.alexapp.drivers.AuthorizationDriver
import com.example.alexapp.models.AuthorizationModel
import com.example.alexapp.models.AuthorizationModel.Credentials

interface Authorization : AuthorizationModel, AuthorizationDriver {
  object Example : Authorization {
    private val model = AuthorizationModel.Example
    override val initialCredentials get() = model.initialCredentials
    override suspend fun authorizeWith(credentials: Credentials) = model.authorizeWith(credentials)
    override suspend fun checkCredentials(credentials: Credentials) =
      if (credentials.login != model.constant.login) "Expected login '${model.constant.login}'"
      else null
  }
}