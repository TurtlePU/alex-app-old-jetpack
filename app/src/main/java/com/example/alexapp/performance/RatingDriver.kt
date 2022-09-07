package com.example.alexapp.performance

import Performance
import androidx.paging.*
import kotlinx.coroutines.flow.Flow

interface RatingDriver {
  suspend fun rate(performance: Performance, rating: Rating)
  val performances: Flow<PagingData<Performance>>

  object Example : RatingDriver {
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
  }
}