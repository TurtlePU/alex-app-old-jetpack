package com.example.alexapp.ui.performance

import Participant
import Performance
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PerformanceCard(performance: Performance, colorBg: Boolean = false, onClick: () -> Unit = {}) {
}

@Preview
@Composable
fun PerformanceCardPreview() {
  val whiteStripes = Participant("the White Stripes", "alternative rock", "25")
  val sevenNationArmy = Performance(0, whiteStripes, "Seven Nation Army")
  var colorBg by remember { mutableStateOf(true) }
  PerformanceCard(sevenNationArmy, colorBg) { colorBg = !colorBg }
}