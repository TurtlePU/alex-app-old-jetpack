package com.example.alexapp.models

import Performance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface RatingModel {
  fun restore(performance: Performance): Flow<Rating?>
  fun isRated(performance: Performance): Flow<Boolean>
  suspend fun rate(performance: Performance, rating: Rating)

  data class Rating(val grade: Double, val comment: String?)

  open class Example(private val map: MutableMap<Performance, Rating>) : RatingModel {
    override fun restore(performance: Performance) = flowOf(map[performance])
    override fun isRated(performance: Performance) = flowOf(map.contains(performance))
    override suspend fun rate(performance: Performance, rating: Rating) {
      map[performance] = rating
    }
  }
}