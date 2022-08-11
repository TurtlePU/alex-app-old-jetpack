package com.example.alexapp.models

import Performance
import kotlinx.coroutines.flow.Flow

interface RestoreModel {
  data class Rating(val grade: Double, val comment: String?)

  fun restore(performance: Performance): Flow<Rating?>
}