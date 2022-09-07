package com.example.alexapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.preferences.preferencesDataStore
import com.example.alexapp.authorization.authorize
import com.example.alexapp.performance.NetworkRatings

class MainActivity : ComponentActivity() {
  companion object {
    private val Context.dataStore by preferencesDataStore("settings")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val model = PreferencesModel(dataStore)
    setContent {
      AppLayout(
        model,
        model,
        { authorize(ktorClient, it) },
        { NetworkRatings(ktorClient, it) })
    }
  }
}