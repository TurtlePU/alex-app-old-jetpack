package com.example.alexapp.ui.performance

import Participant
import Performance
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.alexapp.drivers.examplePager
import com.example.alexapp.models.RatingModel
import com.example.alexapp.models.RestoreModel.Rating
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface RatingDriver {
  val performances: Flow<PagingData<Performance>>
  suspend fun rate(performance: Performance, rating: Rating)

  object Example : RatingDriver {
    override val performances = examplePager.flow
    override suspend fun rate(performance: Performance, rating: Rating) {}
  }
}

@Composable
fun PerformancesScreen(ratings: RatingModel, driver: RatingDriver) {
  val items = driver.performances.collectAsLazyPagingItems()
  val isRefreshing = items.loadState.refresh == LoadState.Loading
  var ratingTarget: Performance? by remember { mutableStateOf(null) }

  SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing), onRefresh = items::refresh) {
    LazyColumn {
      items(items) {
        if (it != null) {
          val isRated by ratings.isRated(it).collectAsState(initial = false)
          PerformanceCard(performance = it, colorBg = !isRated) { ratingTarget = it }
        } else {
          PerformanceCard(Performance(0, Participant("", "", ""), ""))
        }
      }
    }
  }

  ratingTarget?.let {
    val rating by ratings.restore(it).collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    PerformancePopup(it, rating = rating, modifier = Modifier.padding(8.dp)) { newRating ->
      scope.launch {
        ratings.rate(it, newRating)
        driver.rate(it, newRating)
      }
      ratingTarget = null
    }
  }
}

@Preview
@Composable
fun PerformancesPreview() {
  PerformancesScreen(RatingModel.Example(remember { mutableStateMapOf() }), RatingDriver.Example)
}