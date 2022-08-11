package com.example.alexapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface AuthorizationDriver {
  data class Credentials(val host: String, val login: String, val token: String)

  val initialCredentials: Flow<Credentials?>
  suspend fun authorizeWith(credentials: Credentials)

  object Example : AuthorizationDriver {
    val constant = Credentials("https://example.com", "Android", "token")
    override val initialCredentials get() = flowOf(constant)
    override suspend fun authorizeWith(credentials: Credentials) =
      assert(credentials.login == constant.login)
  }
}