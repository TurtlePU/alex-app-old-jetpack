package com.example.alexapp

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
import com.example.alexapp.authorization.AuthorizationModel
import com.example.alexapp.authorization.AuthorizationScreen
import com.example.alexapp.authorization.Credentials
import com.example.alexapp.authorization.exampleAuth
import com.example.alexapp.performance.PerformancesScreen
import com.example.alexapp.performance.RatingDriver
import com.example.alexapp.performance.RatingModel
import com.example.alexapp.ui.theme.AlexAppTheme

@Composable
fun AppLayout(
  authModel: AuthorizationModel,
  ratingModel: RatingModel,
  authorize: suspend (Credentials) -> String?,
  ratingDriver: (Credentials) -> RatingDriver,
) {
  AlexAppTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background,
    ) {
      val navController = rememberNavController()
      NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
          AuthorizationScreen(authModel, authorize) {
            navController.navigate("performances/$host:$login:$token")
          }
        }
        composable("performances/{host}:{login}:{token}") {
          PerformancesScreen(ratingModel, ratingDriver(it.arguments!!.run {
            Credentials(
              getString("host")!!,
              getString("login")!!,
              getString("token")!!,
            )
          }))
        }
      }
    }
  }
}

@Preview
@Composable
fun AppPreview() {
  AppLayout(
    AuthorizationModel.Example,
    RatingModel.Example(remember { mutableStateMapOf() }),
    ::exampleAuth
  ) { RatingDriver.Example }
}