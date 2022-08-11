package com.example.alexapp.domains

import Performance
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.alexapp.models.AppModel
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.models.RatingModel.Rating

class NetworkApp(private val driver: AppModel) : App {
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

  override val initialCredentials get() = driver.initialCredentials
  override suspend fun authorizeWith(credentials: Credentials) = driver.authorizeWith(credentials)
  override suspend fun checkCredentials(credentials: Credentials): String? {
    TODO("Not yet implemented")
  }

  override fun restoreRating(performance: Performance) = driver.restore(performance)
  override suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating) {
    driver.saveRating(performance, rating)
    TODO("Not yet implemented")
  }
}