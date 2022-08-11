package com.example.alexapp.domains

import Performance
import androidx.paging.*
import com.example.alexapp.data.RatingDriver
import com.example.alexapp.data.RatingDriver.Rating
import kotlinx.coroutines.flow.Flow

interface Performances : RatingDriver {
  val flow: Flow<PagingData<Performance>>

  open class Example(map: MutableMap<Performance, Rating>) : RatingDriver.Example(map), Performances {
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