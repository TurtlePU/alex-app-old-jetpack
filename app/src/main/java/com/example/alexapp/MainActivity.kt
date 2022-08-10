package com.example.alexapp

import Performance
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.paging.PagingData
import com.example.alexapp.Authorization.Credentials
import kotlinx.coroutines.flow.emptyFlow

class MainActivity : ComponentActivity() {
  private val app = object : AlexApp {
    override suspend fun checkCredentials(credentials: Credentials): String? = null
    override fun flow(credentials: Credentials) = emptyFlow<PagingData<Performance>>()
    override suspend fun rate(
      credentials: Credentials,
      performance: Performance,
      grade: Double,
      comment: String?
    ) {
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent { app.Layout() }
  }
}