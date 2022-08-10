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
import com.example.alexapp.Authorization.Credentials
import com.example.alexapp.ui.theme.AlexAppTheme
import kotlinx.coroutines.flow.Flow

interface AlexApp {
  suspend fun checkCredentials(credentials: Credentials): String?
  fun flow(credentials: Credentials): Flow<PagingData<Performance>>
  suspend fun rate(
    credentials: Credentials,
    performance: Performance,
    grade: Double,
    comment: String?
  )

  private fun authorization(onSuccess: Credentials.() -> Unit) = object : Authorization {
    override suspend fun check(credentials: Credentials) = checkCredentials(credentials)
    override fun authorizeWith(credentials: Credentials) = onSuccess(credentials)
  }

  private fun performances(credentials: Credentials) = object : Performances {
    override val flow: Flow<PagingData<Performance>> get() = flow(credentials)
    override suspend fun rate(performance: Performance, grade: Double, comment: String?) {
      rate(credentials, performance, grade, comment)
    }
  }

  @Composable
  fun Layout() {
    AlexAppTheme {
      Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "auth") {
          composable("auth") {
            authorization {
              navController.navigate("performances/$host:$login:$token")
            }.Layout()
          }
          composable("performances/{host}:{login}:{token}") {
            performances(it.arguments!!.run {
              Credentials(
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
}