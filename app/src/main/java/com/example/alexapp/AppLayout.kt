package com.example.alexapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alexapp.authorization.AuthorizationPreview
import com.example.alexapp.authorization.AuthorizationScreen
import com.example.alexapp.authorization.Credentials
import com.example.alexapp.authorization.OnSuccess
import com.example.alexapp.performance.PerformancesPreview
import com.example.alexapp.performance.PerformancesScreen
import com.example.alexapp.ui.theme.AlexAppTheme
import io.ktor.client.*

@Composable
fun AppLayout(auth: @Composable (OnSuccess) -> Unit, rating: @Composable (Credentials) -> Unit) {
  AlexAppTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background,
    ) {
      val navController = rememberNavController()
      NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
          auth { navController.navigate("performances/$host:$login:$token") }
        }
        composable("performances/{host}:{login}:{token}") {
          val credentials = it.arguments!!.run {
            Credentials(
              getString("host")!!,
              getString("login")!!,
              getString("token")!!
            )
          }
          rating(credentials)
        }
      }
    }
  }
}

@Composable
fun AppInject(data: DataStore<Preferences>, client: HttpClient) {
  AppLayout(
    { onSuccess ->
      AuthorizationScreen(
        AuthorizationPreferences(data),
        { creds -> authorize(client, creds) },
        onSuccess
      )
    },
    { credentials ->
      PerformancesScreen(
        RatingPreferences(data),
        NetworkRatings(client, credentials)
      )
    },
  )
}

@Preview
@Composable
fun AppPreview() {
  AppLayout({ AuthorizationPreview(it) }, { PerformancesPreview() })
}