package com.example.alexapp

import Participant
import Performance
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.*
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.alexapp.ui.theme.AlexAppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlin.random.Random

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
fun Exposition(ratings: RatingModel, driver: RatingDriver) {
  val items = driver.performances.collectAsLazyPagingItems()
  val isRefreshing = items.loadState.refresh == LoadState.Loading
  var ratingTarget: Performance? by remember { mutableStateOf(null) }

  Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
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
      val oldRating by ratings.restore(it).collectAsState(initial = null)
      val scope = rememberCoroutineScope()
      Evaluation(it, oldRating = oldRating, modifier = Modifier.padding(8.dp)) { newRating ->
        scope.launch {
          ratings.rate(it, newRating)
          driver.rate(it, newRating)
        }
        ratingTarget = null
      }
    }
  }
}

@Preview
@Composable
fun ExpositionPreview() {
  AlexAppTheme {
    Exposition(
      object : RatingModel {
        val map = remember { mutableStateMapOf<Performance, Rating>() }
        override fun restore(performance: Performance) = flowOf(map[performance])
        override fun isRated(performance: Performance) = flowOf(map.contains(performance))
        override suspend fun rate(performance: Performance, rating: Rating) {
          map[performance] = rating
        }
      },
      object : RatingDriver {
        val accumulated = remember { mutableStateListOf<Performance>() }
        override suspend fun rate(performance: Performance, rating: Rating) {}
        override val performances = Pager(PagingConfig(100)) {
          object : PagingSource<Int, Performance>() {
            override fun getRefreshKey(state: PagingState<Int, Performance>) = state.run {
              ((anchorPosition ?: 0) - config.initialLoadSize / 2).coerceAtLeast(0)
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Performance> {
              val choir = Participant("Choir #${Random.nextInt()}", "Soviet songs", "100")
              val result = Performance(params.key ?: 0, choir, "Katusha #${Random.nextInt()}")
              accumulated.add(result)
              return LoadResult.Page(accumulated, null, null)
            }
          }
        }.flow
      },
    )
  }
}