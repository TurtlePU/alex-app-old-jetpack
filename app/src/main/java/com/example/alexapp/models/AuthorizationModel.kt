package com.example.alexapp.models

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface AuthorizationModel {
  val initials: Flow<Credentials?>
  suspend fun remember(credentials: Credentials)

  data class Credentials(val host: String, val login: String, val token: String)

  object Example : AuthorizationModel {
    const val login = "Android"
    override val initials = flowOf(Credentials("https://example.com", login, "token"))
    override suspend fun remember(credentials: Credentials) = assert(credentials.login == login)
  }
}