package com.example.alexapp.models

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface AuthorizationModel {
  data class Credentials(val host: String, val login: String, val token: String)

  val initialCredentials: Flow<Credentials?>
  suspend fun authorizeWith(credentials: Credentials)

  object Example : AuthorizationModel {
    val constant = Credentials("https://example.com", "Android", "token")
    override val initialCredentials get() = flowOf(constant)
    override suspend fun authorizeWith(credentials: Credentials) =
      assert(credentials.login == constant.login)
  }
}