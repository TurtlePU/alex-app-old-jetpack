package com.example.alexapp.authorization

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface AuthorizationModel {
  val initials: Flow<Credentials?>
  suspend fun remember(credentials: Credentials)

  object Example : AuthorizationModel {
    const val login = "Android"
    override val initials = flowOf(Credentials("https://example.com", login, "token"))
    override suspend fun remember(credentials: Credentials) = assert(credentials.login == login)
  }
}