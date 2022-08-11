package com.example.alexapp

import Performance
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.alexapp.data.AppDriver
import com.example.alexapp.data.AuthorizationDriver.Credentials
import com.example.alexapp.data.RatingDriver.Rating
import com.example.alexapp.domains.AlexApp

class NetworkApp(private val driver: AppDriver) : AlexApp {
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