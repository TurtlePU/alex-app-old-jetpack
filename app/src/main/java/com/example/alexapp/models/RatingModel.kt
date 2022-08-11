package com.example.alexapp.models

import Performance
import com.example.alexapp.models.RestoreModel.Rating
import kotlinx.coroutines.flow.flowOf

interface RatingModel : RestoreModel {
  suspend fun rate(`for`: Performance, rating: Rating)

  open class Example(private val state: MutableMap<Performance, Rating>) : RatingModel {
    override fun restore(performance: Performance) = flowOf(state[performance])
    override suspend fun rate(`for`: Performance, rating: Rating) { state[`for`] = rating }
  }
}