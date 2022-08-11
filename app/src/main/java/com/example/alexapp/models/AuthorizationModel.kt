package com.example.alexapp.models

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface AuthorizationModel {
  data class Credentials(val host: String, val login: String, val token: String)

  val initials: Flow<Credentials?>
  suspend fun remember(credentials: Credentials)

  object Example : AuthorizationModel {
    const val login = "Android"
    private val constant = Credentials("https://example.com", login, "token")
    override val initials get() = flowOf(constant)
    override suspend fun remember(credentials: Credentials) = assert(credentials.login == login)
  }
}