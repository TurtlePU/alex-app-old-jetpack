package com.example.alexapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alexapp.domains.AlexApp
import com.example.alexapp.domains.Authorization
import com.example.alexapp.ui.theme.AlexAppTheme

@Composable
fun AlexApp.Layout() {
  AlexAppTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background,
    ) {
      val navController = rememberNavController()
      NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
          authorization {
            navController.navigate("performances/$host:$login:$token")
          }.Layout()
        }
        composable("performances/{host}:{login}:{token}") {
          performances(it.arguments!!.run {
            Authorization.Credentials(
              getString("host")!!,
              getString("login")!!,
              getString("token")!!,
            )
          }).Layout()
        }
      }
    }
  }
}

@Preview
@Composable
fun AlexAppPreview() {
  AlexApp.example(remember { mutableStateMapOf() }).Layout()
}