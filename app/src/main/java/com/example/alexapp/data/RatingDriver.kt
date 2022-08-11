package com.example.alexapp.data

import Performance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface RatingDriver {
  data class Rating(val grade: Double, val comment: String?)

  fun restore(performance: Performance): Flow<Rating?>
  suspend fun saveRating(performance: Performance, rating: Rating)

  companion object {
    fun example(state: MutableMap<Performance, Rating>) = object : RatingDriver {
      override fun restore(performance: Performance) = flowOf(state[performance])
      override suspend fun saveRating(performance: Performance, rating: Rating) {
        state[performance] = rating
      }
    }
  }
}