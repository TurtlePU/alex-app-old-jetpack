package com.example.alexapp.network

import Performance
import androidx.paging.PagingData
import com.example.alexapp.domains.AlexApp
import com.example.alexapp.domains.Authorization.Credentials
import com.example.alexapp.domains.Performances.Rating
import kotlinx.coroutines.flow.Flow

class NetworkApp(private val ratings: MutableMap<Performance, Rating>) : AlexApp {
  override val initialCredentials: Credentials get() = TODO("Not yet implemented")

  override suspend fun checkCredentials(credentials: Credentials): String? {
    TODO("Not yet implemented")
  }

  override suspend fun saveCredentials(credentials: Credentials) {
    TODO("Not yet implemented")
  }

  override fun flow(host: String): Flow<PagingData<Performance>> {
    TODO("Not yet implemented")
  }

  override fun restoreRating(performance: Performance) = ratings[performance]

  override suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating) {
    ratings[performance] = rating
    TODO("Not yet implemented")
  }
}