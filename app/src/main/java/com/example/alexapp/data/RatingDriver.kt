package com.example.alexapp.data

import Performance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface RatingDriver {
  data class Rating(val grade: Double, val comment: String?)

  fun restore(performance: Performance): Flow<Rating?>
  suspend fun saveRating(performance: Performance, rating: Rating)

  open class Example(private val state: MutableMap<Performance, Rating>) : RatingDriver {
    override fun restore(performance: Performance) = flowOf(state[performance])
    override suspend fun saveRating(performance: Performance, rating: Rating) {
      state[performance] = rating
    }
  }
}