package com.example.alexapp.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.alexapp.drivers.AppDriver
import com.example.alexapp.models.AuthorizationModel

@Composable
fun AuthorizationScreen(
  model: AuthorizationModel,
  authorize: suspend (AuthorizationModel.Credentials) -> String?,
  onSuccess: AuthorizationModel.Credentials.() -> Unit = {},
) {
  Text("Hello, Android!")
}

@Composable
@Preview
fun AuthorizationPreview() {
  AuthorizationScreen(AuthorizationModel.Example, AppDriver.Example::authorize)
}