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
import com.example.alexapp.data.AuthorizationDriver
import com.example.alexapp.domains.AlexApp
import com.example.alexapp.ui.theme.AlexAppTheme

@Composable
fun AlexAppLayout(app: AlexApp) {
  AlexAppTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background,
    ) {
      val navController = rememberNavController()
      NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
          AuthorizationScreen(
            app.authorization {
              navController.navigate("performances/$host:$login:$token")
            }
          )
        }
        composable("performances/{host}:{login}:{token}") {
          PerformancesScreen(
            app.performances(it.arguments!!.run {
              AuthorizationDriver.Credentials(
                getString("host")!!,
                getString("login")!!,
                getString("token")!!,
              )
            })
          )
        }
      }
    }
  }
}

@Preview
@Composable
fun AlexAppPreview() {
  AlexAppLayout(AlexApp.example(remember { mutableStateMapOf() }))
}