package com.example.alexapp.ui.performance

import Performance
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.alexapp.models.RestoreModel

@Composable
fun PerformancePopup(
  performance: Performance,
  modifier: Modifier = Modifier,
  rating: RestoreModel.Rating? = null,
  commit: (RestoreModel.Rating) -> Unit = {}
) {
  //
}