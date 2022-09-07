package com.example.alexapp.authorization

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AuthorizationScreen(
  model: AuthorizationModel,
  authorize: suspend (Credentials) -> String?,
  onSuccess: Credentials.() -> Unit = {},
) {
  Text("Hello, Android!")
}

fun exampleAuth(credentials: Credentials): String? {
  val login = AuthorizationModel.Example.login
  return if (credentials.login != login) "Expected login '$login'" else null
}

@Composable
@Preview
fun AuthorizationPreview() {
  AuthorizationScreen(AuthorizationModel.Example, ::exampleAuth)
}