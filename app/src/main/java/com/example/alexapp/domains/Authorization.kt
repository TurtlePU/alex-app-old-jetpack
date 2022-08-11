package com.example.alexapp.domains

import com.example.alexapp.drivers.AuthorizationDriver
import com.example.alexapp.models.AuthorizationModel
import com.example.alexapp.models.AuthorizationModel.Credentials
import kotlinx.coroutines.flow.flowOf

interface Authorization : AuthorizationModel, AuthorizationDriver {
  object Example : Authorization {
    private const val login = "Android"
    private val constant = Credentials("https://example.com", login, "token")
    override val initials get() = flowOf(constant)
    override suspend fun remember(credentials: Credentials) = assert(credentials.login == login)
    override suspend fun authorize(credentials: Credentials) =
      if (credentials.login != login) "Expected login '$login'" else null
  }
}