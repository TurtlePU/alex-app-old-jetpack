package com.example.alexapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

interface Authorization {
  data class Credentials(val host: String, val login: String, val token: String)

  suspend fun check(credentials: Credentials): String?
  fun authorizeWith(credentials: Credentials)

  @Composable
  fun Layout() {
    Text(text = "Hello Android!")
  }
}