package com.example.alexapp.models

import Performance
import com.example.alexapp.models.AuthorizationModel.Credentials
import kotlinx.coroutines.flow.flowOf

interface AppModel : AuthorizationModel, RatingModel {
  class Example(map: MutableMap<Performance, RestoreModel.Rating>) : AppModel,
    RatingModel.Example(map) {
    override val initials = flowOf(Credentials("https://example.com", login, "token"))
    override suspend fun remember(credentials: Credentials) = assert(credentials.login == login)

    companion object {
      const val login = "Android"
    }
  }
}