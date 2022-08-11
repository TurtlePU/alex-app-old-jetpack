package com.example.alexapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.preferences.preferencesDataStore
import com.example.alexapp.ui.components.AlexAppLayout

class MainActivity : ComponentActivity() {
  companion object {
    private val Context.dataStore by preferencesDataStore("settings")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent { AlexAppLayout(NetworkApp(PreferencesDriver(dataStore))) }
  }
}