package com.example.alexapp.ui

import Performance
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
import com.example.alexapp.domains.Authorization
import com.example.alexapp.drivers.AppDriver
import com.example.alexapp.models.AppModel
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.models.RatingModel
import com.example.alexapp.models.RestoreModel.Rating
import com.example.alexapp.ui.theme.AlexAppTheme

@Composable
fun AppLayout(model: AppModel, driver: AppDriver) {
  AlexAppTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background,
    ) {
      val navController = rememberNavController()
      NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
          AuthorizationScreen(object : Authorization {
            override val initials get() = model.initials
            override suspend fun authorize(credentials: Credentials) = driver.authorize(credentials)
            override suspend fun remember(credentials: Credentials) {
              model.remember(credentials)
              val (host, login, token) = credentials
              navController.navigate("performances/$host:$login:$token")
            }
          })
        }
        composable("performances/{host}:{login}:{token}") {
          val credentials = it.arguments!!.run {
            Credentials(
              getString("host")!!,
              getString("login")!!,
              getString("token")!!,
            )
          }
          PerformancesScreen(driver.flow(credentials.host), object : RatingModel {
            override fun restore(performance: Performance) = model.restore(performance)
            override fun isRated(performance: Performance) = model.isRated(performance)
            override suspend fun rate(`for`: Performance, rating: Rating) {
              model.rate(`for`, rating)
              driver.rate(credentials, `for`, rating)
            }
          })
        }
      }
    }
  }
}

@Preview
@Composable
fun AppPreview() {
  AppLayout(AppModel.Example(remember { mutableStateMapOf() }), AppDriver.Example)
}