package com.example.alexapp.domains

import Performance
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface Performances {
  data class Rating(val grade: Double, val comment: String?)

  val flow: Flow<PagingData<Performance>>
  fun restore(performance: Performance): Rating?
  suspend fun rate(performance: Performance, rating: Rating)

  companion object {
    fun example(ratings: MutableMap<Performance, Rating>) = object : Performances {
      override val flow: Flow<PagingData<Performance>>
        get() = TODO("Not yet implemented")

      override fun restore(performance: Performance) = ratings[performance]

      override suspend fun rate(performance: Performance, rating: Rating) {
        ratings[performance] = rating
      }
    }
  }
}