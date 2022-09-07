package com.example.alexapp.authorization

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.alexapp.drivers.AppDriver

@Composable
fun AuthorizationScreen(
  model: AuthorizationModel,
  authorize: suspend (Credentials) -> String?,
  onSuccess: Credentials.() -> Unit = {},
) {
  Text("Hello, Android!")
}

@Composable
@Preview
fun AuthorizationPreview() {
  AuthorizationScreen(AuthorizationModel.Example, AppDriver.Example::authorize)
}