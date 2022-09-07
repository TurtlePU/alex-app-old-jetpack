package com.example.alexapp.performance

import Participant
import Performance
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.*
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

data class Rating(val grade: Double, val comment: String?)

interface RatingModel {
  fun restore(performance: Performance): Flow<Rating?>
  fun isRated(performance: Performance): Flow<Boolean>
  suspend fun rate(performance: Performance, rating: Rating)
}

interface RatingDriver {
  suspend fun rate(performance: Performance, rating: Rating)
  val performances: Flow<PagingData<Performance>>
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
          PerformanceCard(performance = it, isNew = !isRated) { ratingTarget = it }
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
  val map = remember { mutableStateMapOf<Performance, Rating>() }
  PerformancesScreen(
    object : RatingModel {
      override fun restore(performance: Performance) = flowOf(map[performance])
      override fun isRated(performance: Performance) = flowOf(map.contains(performance))
      override suspend fun rate(performance: Performance, rating: Rating) {
        map[performance] = rating
      }
    },
    object : RatingDriver {
      override suspend fun rate(performance: Performance, rating: Rating) {}
      override val performances = Pager(PagingConfig(100)) {
        object : PagingSource<Int, Performance>() {
          override fun getRefreshKey(state: PagingState<Int, Performance>): Int? {
            TODO("Not yet implemented")
          }

          override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Performance> {
            TODO("Not yet implemented")
          }
        }
      }.flow
    },
  )
}