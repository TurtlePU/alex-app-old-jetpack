package com.example.alexapp.domains

import Performance
import androidx.paging.*
import com.example.alexapp.models.RatingModel
import com.example.alexapp.models.RestoreModel.Rating
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface Performances : RatingModel {
  val flow: Flow<PagingData<Performance>>

  class Example(private val ratings: MutableMap<Performance, Rating>) : Performances {
    override val flow get() = Pager(PagingConfig(100)) { DefaultSource }.flow
    override fun restore(performance: Performance) = flowOf(ratings[performance])
    override fun isRated(performance: Performance) = flowOf(ratings.contains(performance))
    override suspend fun rate(`for`: Performance, rating: Rating) {
      ratings[`for`] = rating
    }

    object DefaultSource : PagingSource<Int, Performance>() {
      override fun getRefreshKey(state: PagingState<Int, Performance>): Int? {
        TODO("Not yet implemented")
      }

      override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Performance> {
        TODO("Not yet implemented")
      }
    }
  }
}