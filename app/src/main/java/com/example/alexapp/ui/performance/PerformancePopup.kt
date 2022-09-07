package com.example.alexapp.ui.performance

import Performance
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PerformancePopup(
  performance: Performance,
  modifier: Modifier = Modifier,
  rating: Rating? = null,
  commit: (Rating) -> Unit = {}
) {
  //
}