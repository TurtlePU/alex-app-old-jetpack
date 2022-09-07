package com.example.alexapp.models

import Performance
import com.example.alexapp.models.RestoreModel.Rating
import kotlinx.coroutines.flow.flowOf

interface RatingModel : RestoreModel {
  suspend fun rate(`for`: Performance, rating: Rating)

  class Example(private val map: MutableMap<Performance, Rating>) : RatingModel {
    override fun restore(performance: Performance) = flowOf(map[performance])
    override fun isRated(performance: Performance) = flowOf(map.contains(performance))
    override suspend fun rate(`for`: Performance, rating: Rating) {
      map[`for`] = rating
    }
  }
}