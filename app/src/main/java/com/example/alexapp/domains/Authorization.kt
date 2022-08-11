package com.example.alexapp.domains

import com.example.alexapp.models.AuthorizationModel
import com.example.alexapp.models.AuthorizationModel.Credentials

interface Authorization : AuthorizationModel {
  suspend fun checkCredentials(credentials: Credentials): String?

  object Example : Authorization {
    private val driver = AuthorizationModel.Example
    override val initialCredentials get() = driver.initialCredentials
    override suspend fun authorizeWith(credentials: Credentials) = driver.authorizeWith(credentials)
    override suspend fun checkCredentials(credentials: Credentials) =
      if (credentials.login != driver.constant.login) "Expected login '${driver.constant.login}'"
      else null
  }
}