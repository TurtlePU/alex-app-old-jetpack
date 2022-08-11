package com.example.alexapp.drivers

import Performance
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.models.RestoreModel.Rating

object NetworkDriver : AppDriver {
  override fun flow(host: String) = Pager(PagingConfig(100)) {
    object : PagingSource<Int, Performance>() {
      override fun getRefreshKey(state: PagingState<Int, Performance>): Int? {
        TODO("Not yet implemented")
      }

      override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Performance> {
        TODO("Not yet implemented")
      }
    }
  }.flow

  override suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating) {
    TODO("Not yet implemented")
  }

  override suspend fun authorize(credentials: Credentials): String? {
    TODO("Not yet implemented")
  }
}