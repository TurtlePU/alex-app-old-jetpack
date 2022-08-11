package com.example.alexapp.domains

import Performance
import androidx.paging.*
import com.example.alexapp.data.RatingDriver
import com.example.alexapp.data.RatingDriver.Rating
import kotlinx.coroutines.flow.Flow

interface Performances : RatingDriver {
  val flow: Flow<PagingData<Performance>>

  companion object {
    fun example(ratings: MutableMap<Performance, Rating>): Performances {
      val driver = RatingDriver.example(ratings)
      return object : Performances {
        override val flow get() = defaultPager.flow
        override fun restore(performance: Performance) = driver.restore(performance)
        override suspend fun saveRating(performance: Performance, rating: Rating) =
          driver.saveRating(performance, rating)
      }
    }

    val defaultPager = Pager(PagingConfig(100)) {
      object : PagingSource<Int, Performance>() {
        override fun getRefreshKey(state: PagingState<Int, Performance>): Int? {
          TODO("Not yet implemented")
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Performance> {
          TODO("Not yet implemented")
        }
      }
    }
  }
}