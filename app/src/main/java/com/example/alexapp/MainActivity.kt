package com.example.alexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alexapp.ui.components.AlexAppLayout

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent { AlexAppLayout(NetworkApp(BasicDriver())) }
  }
}