package com.example.alexapp.ui.components

import Performance
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.alexapp.domains.Performances

@Composable
fun PerformancesScreen(performances: Performances) {
  val items = performances.flow.collectAsLazyPagingItems()
  var ratingTarget: Performance? by remember { mutableStateOf(null) }
}

@Preview
@Composable
fun PerformancesPreview() {
  PerformancesScreen(Performances.example(remember { mutableStateMapOf() }))
}