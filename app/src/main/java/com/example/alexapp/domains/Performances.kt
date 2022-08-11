package com.example.alexapp.domains

import Performance
import androidx.paging.*
import com.example.alexapp.models.RatingModel
import com.example.alexapp.models.RatingModel.Rating
import kotlinx.coroutines.flow.Flow

interface Performances : RatingModel {
  val flow: Flow<PagingData<Performance>>

  open class Example(m: MutableMap<Performance, Rating>) : RatingModel.Example(m), Performances {
    override val flow get() = Pager(PagingConfig(100)) { DefaultSource }.flow

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