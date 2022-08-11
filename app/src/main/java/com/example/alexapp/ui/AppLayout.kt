package com.example.alexapp.ui

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
import com.example.alexapp.models.AuthorizationModel
import com.example.alexapp.domains.App
import com.example.alexapp.domains.App.OverloadedAuth
import com.example.alexapp.ui.theme.AlexAppTheme

@Composable
fun AppLayout(app: App) {
  AlexAppTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background,
    ) {
      val navController = rememberNavController()
      NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
          AuthorizationScreen(
            OverloadedAuth(app) {
              navController.navigate("performances/$host:$login:$token")
            }
          )
        }
        composable("performances/{host}:{login}:{token}") {
          PerformancesScreen(
            App.CredentialPerformances(app, it.arguments!!.run {
              AuthorizationModel.Credentials(
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
fun AppPreview() {
  AppLayout(App.Example(remember { mutableStateMapOf() }))
}