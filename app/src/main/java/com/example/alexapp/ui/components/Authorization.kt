package com.example.alexapp.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.alexapp.domains.Authorization

@Composable
fun AuthorizationScreen(authorization: Authorization) {
  Text("Hello, Android!")
}

@Composable
@Preview
fun AuthorizationPreview() {
  AuthorizationScreen(Authorization.Example)
}