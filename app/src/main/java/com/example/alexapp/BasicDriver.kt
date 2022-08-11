package com.example.alexapp

import Performance
import com.example.alexapp.data.AppDriver
import com.example.alexapp.data.AuthorizationDriver.Credentials
import com.example.alexapp.data.RatingDriver.Rating
import kotlinx.coroutines.flow.Flow

class BasicDriver : AppDriver {
  override val initialCredentials: Flow<Credentials>
    get() = TODO("Not yet implemented")

  override suspend fun authorizeWith(credentials: Credentials) {
    TODO("Not yet implemented")
  }

  override fun restore(performance: Performance): Flow<Rating?> {
    TODO("Not yet implemented")
  }

  override suspend fun saveRating(performance: Performance, rating: Rating) {
    TODO("Not yet implemented")
  }
}