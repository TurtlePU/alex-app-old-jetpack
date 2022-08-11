package com.example.alexapp.domains

import com.example.alexapp.data.AuthorizationDriver
import com.example.alexapp.data.AuthorizationDriver.Credentials

interface Authorization : AuthorizationDriver {
  suspend fun checkCredentials(credentials: Credentials): String?

  object Example : Authorization {
    override val initialCredentials get() = driver.initialCredentials
    override suspend fun authorizeWith(credentials: Credentials) = driver.authorizeWith(credentials)
    override suspend fun checkCredentials(credentials: Credentials) =
      if (credentials.login != initialCredentials.login) "Expected login '${initialCredentials.login}'" else null
  }

  companion object {
    val driver = AuthorizationDriver.Example
  }
}