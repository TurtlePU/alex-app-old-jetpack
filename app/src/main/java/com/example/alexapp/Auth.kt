package com.example.alexapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Auth(check: suspend (Credentials) -> String?, onSuccess: (Credentials) -> Unit) {
  Text(text = "Hello Android!")
}