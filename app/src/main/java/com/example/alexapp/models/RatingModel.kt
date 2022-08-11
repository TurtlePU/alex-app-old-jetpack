package com.example.alexapp.models

import Performance
import com.example.alexapp.models.RestoreModel.Rating

interface RatingModel : RestoreModel {
  suspend fun rate(`for`: Performance, rating: Rating)
}