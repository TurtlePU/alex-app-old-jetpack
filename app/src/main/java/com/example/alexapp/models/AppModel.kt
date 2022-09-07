package com.example.alexapp.models

import Performance
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.ui.performance.Rating
import com.example.alexapp.ui.performance.RatingModel

interface AppModel : AuthorizationModel, RatingModel {
  class Example(map: MutableMap<Performance, Rating>) : AppModel, RatingModel.Example(map) {
    private val auth = AuthorizationModel.Example
    override val initials get() = auth.initials
    override suspend fun remember(credentials: Credentials) = auth.remember(credentials)
  }
}