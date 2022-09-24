package com.example.alexapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.ktor.client.*

@Composable
fun AppLayout(auth: @Composable (OnSuccess) -> Unit, expo: @Composable (Credentials) -> Unit) {
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
      expo(credentials)
    }
  }
}

@Composable
fun AppInject(data: DataStore<Preferences>, client: HttpClient) {
  AppLayout(
    { onSuccess ->
      Authorization(
        AuthorizationPreferences(data),
        { creds -> authorize(client, creds) },
        onSuccess
      )
    },
    { credentials ->
      Exposition(
        RatingPreferences(data),
        NetworkRatings(client, credentials, remember { mutableStateListOf() })
      )
    },
  )
}

@Preview
@Composable
fun AppPreview() {
  AppLayout({ AuthorizationPreview(it) }, { ExpositionPreview() })
}