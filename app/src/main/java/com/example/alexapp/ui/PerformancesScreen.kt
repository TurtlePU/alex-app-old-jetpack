package com.example.alexapp.ui

import Participant
import Performance
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.alexapp.domains.Performances
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun PerformancesScreen(performances: Performances) {
  val items = performances.flow.collectAsLazyPagingItems()
  val isRefreshing = items.loadState.refresh == LoadState.Loading
  var ratingTarget: Performance? by remember { mutableStateOf(null) }

  SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing), onRefresh = items::refresh) {
    LazyColumn {
      items(items) {
        if (it != null) {
          val isRated by performances.isRated(it).collectAsState(initial = false)
          PerformanceCard(performance = it, colorBg = !isRated) { ratingTarget = it }
        } else {
          PerformanceCard(Performance(0, Participant("", "", ""), ""))
        }
      }
    }
  }

  ratingTarget?.let {
    val rating by performances.restore(it).collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    PerformancePopup(it, rating = rating, modifier = Modifier.padding(8.dp)) { newRating ->
      scope.launch { performances.rate(it, newRating) }
      ratingTarget = null
    }
  }
}

@Preview
@Composable
fun PerformancesPreview() {
  PerformancesScreen(Performances.Example(remember { mutableStateMapOf() }))
}