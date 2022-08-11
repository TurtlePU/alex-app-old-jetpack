package com.example.alexapp.domains

interface Authorization {
  data class Credentials(val host: String, val login: String, val token: String)

  suspend fun check(credentials: Credentials): String?
  fun authorizeWith(credentials: Credentials)

  object Example : Authorization {
    override suspend fun check(credentials: Credentials) =
      if (credentials.login != "Android") "Expected login 'Android'" else null

    override fun authorizeWith(credentials: Credentials) {
      assert(credentials.login == "Android")
    }
  }
}