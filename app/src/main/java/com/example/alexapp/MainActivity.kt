package com.example.alexapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.preferences.preferencesDataStore
import com.example.alexapp.authorization.AuthorizationPreferences
import com.example.alexapp.authorization.authorize
import com.example.alexapp.performance.NetworkRatings
import com.example.alexapp.performance.RatingPreferences

class MainActivity : ComponentActivity() {
  companion object {
    private val Context.dataStore by preferencesDataStore("settings")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AppLayout(
        AuthorizationPreferences(dataStore),
        RatingPreferences(dataStore),
        { authorize(ktorClient, it) },
        { NetworkRatings(ktorClient, it) })
    }
  }
}