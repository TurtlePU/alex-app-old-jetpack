package com.example.alexapp

import Performance
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Evaluation(
  performance: Performance,
  modifier: Modifier = Modifier,
  oldRating: Rating? = null,
  commit: (Rating) -> Unit = {}
) {
  //
}