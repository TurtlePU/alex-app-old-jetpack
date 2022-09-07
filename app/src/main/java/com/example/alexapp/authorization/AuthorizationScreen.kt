package com.example.alexapp.authorization

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class Credentials(val host: String, val login: String, val token: String)

interface AuthorizationModel {
  val initials: Flow<Credentials?>
  suspend fun remember(credentials: Credentials)
}

typealias OnSuccess = Credentials.() -> Unit

@Composable
fun AuthorizationScreen(
  model: AuthorizationModel,
  authorize: suspend (Credentials) -> String?,
  onSuccess: OnSuccess
) {
  Text("Hello, Android!")
}

@Composable
@Preview
fun AuthorizationPreview(onSuccess: OnSuccess = {}) {
  val login = "Android"
  AuthorizationScreen(
    object : AuthorizationModel {
      override val initials = flowOf(Credentials("https://example.com", login, "token"))
      override suspend fun remember(credentials: Credentials) = assert(credentials.login == login)
    },
    { if (it.login != login) "Expected login '$login'" else null },
    onSuccess,
  )
}