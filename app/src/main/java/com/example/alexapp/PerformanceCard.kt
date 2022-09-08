package com.example.alexapp

import Participant
import Performance
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PerformanceCard(performance: Performance, isNew: Boolean = false, onClick: () -> Unit = {}) {
  Column(
    modifier = Modifier
      .padding(15.dp)
      .fillMaxWidth()
      .selectable(selected = false, onClick = onClick)
      .apply { if (isNew) background(color = MaterialTheme.colorScheme.secondary) }
  ) {
    Text(text = performance.participantName, fontSize = 20.sp)
    Text(text = performance.repertoire, fontSize = 20.sp)
  }
}

@Preview
@Composable
fun PerformanceCardPreview() {
  val whiteStripes = Participant("the White Stripes", "alternative rock", "25")
  val sevenNationArmy = Performance(0, whiteStripes, "Seven Nation Army")
  var isNew by remember { mutableStateOf(true) }
  PerformanceCard(sevenNationArmy, isNew) { isNew = !isNew }
}