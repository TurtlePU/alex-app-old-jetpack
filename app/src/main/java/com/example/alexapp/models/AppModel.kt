package com.example.alexapp.models

import Performance
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.models.RatingModel.Rating
import kotlinx.coroutines.flow.flowOf

interface AppModel : AuthorizationModel, RatingModel {
  class Example(map: MutableMap<Performance, Rating>) : AppModel, RatingModel.Example(map) {
    override val initials = flowOf(Credentials("https://example.com", login, "token"))
    override suspend fun remember(credentials: Credentials) = assert(credentials.login == login)

    companion object {
      const val login = "Android"
    }
  }
}