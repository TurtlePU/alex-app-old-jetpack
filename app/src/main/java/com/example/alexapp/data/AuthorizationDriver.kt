package com.example.alexapp.data

interface AuthorizationDriver {
  data class Credentials(val host: String, val login: String, val token: String)

  val initialCredentials: Credentials?
  suspend fun authorizeWith(credentials: Credentials)

  object Example : AuthorizationDriver {
    override val initialCredentials get() = Credentials("https://example.com", "Android", "token")
    override suspend fun authorizeWith(credentials: Credentials) =
      assert(credentials.login == initialCredentials.login)
  }
}