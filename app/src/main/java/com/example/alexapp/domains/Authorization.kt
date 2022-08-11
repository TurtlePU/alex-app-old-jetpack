package com.example.alexapp.domains

interface Authorization {
  data class Credentials(val host: String, val login: String, val token: String)

  val initial: Credentials?
  suspend fun check(credentials: Credentials): String?
  suspend fun authorizeWith(credentials: Credentials)

  object Example : Authorization {
    override val initial get() = Credentials("https://example.com", "Android", "token")

    override suspend fun check(credentials: Credentials) =
      if (credentials.login != initial.login) "Expected login '${initial.login}'" else null

    override suspend fun authorizeWith(credentials: Credentials) {
      assert(credentials.login == initial.login)
    }
  }
}