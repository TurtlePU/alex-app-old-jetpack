package com.example.alexapp

import Performance
import androidx.compose.runtime.*
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow

interface Performances {
  val flow: Flow<PagingData<Performance>>
  suspend fun rate(performance: Performance, grade: Double, comment: String?)

  @Composable
  fun Layout() {
    val performances = flow.collectAsLazyPagingItems()
    var ratingTarget: Performance? by remember { mutableStateOf(null) }
    TODO("show list + let select target")
  }
}