package com.example.alexapp

import Performance
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import com.example.alexapp.Credentials.Companion.credentials
import com.example.alexapp.ui.theme.AlexAppTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun AlexApp(
  checkCredentials: suspend (Credentials) -> String?,
  performances: (Credentials) -> Flow<PagingData<Performance>>
) {
  AlexAppTheme {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
      val navController = rememberNavController()
      NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
          Auth(checkCredentials) { navController.navigate("rate/$it") }
        }
        composable("rate/{host}:{login}:{token}") {
          val credentials = it.arguments?.credentials ?: throw MissingArguments()
          Rate(performances(credentials))
        }
      }
    }
  }
}

class MissingArguments : Throwable()