package com.example.alexapp

import Participant
import Performance
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alexapp.ui.theme.AlexAppTheme

@Composable
fun PerformanceCard(performance: Performance, isNew: Boolean = false, onClick: () -> Unit = {}) {
  Surface(
    color =
    if (isNew) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.background,
    modifier = Modifier
      .fillMaxWidth()
      .selectable(selected = false, onClick = onClick)
  ) {
    Column(modifier = Modifier.padding(15.dp)) {
      Text(text = performance.participantName, fontSize = 20.sp)
      Text(text = performance.repertoire, fontSize = 20.sp)
    }
  }
}

@Preview
@Composable
fun PerformanceCardPreview() {
  val whiteStripes = Participant("the White Stripes", "alternative rock", "25")
  val sevenNationArmy = Performance(0, whiteStripes, "Seven Nation Army")
  var isNew by remember { mutableStateOf(true) }
  AlexAppTheme {
    PerformanceCard(sevenNationArmy, isNew) { isNew = !isNew }
  }
}