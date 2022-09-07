package com.example.alexapp.drivers

import Performance
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState

val examplePager = Pager(PagingConfig(100)) {
  object : PagingSource<Int, Performance>() {
    override fun getRefreshKey(state: PagingState<Int, Performance>): Int? {
      TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Performance> {
      TODO("Not yet implemented")
    }
  }
}