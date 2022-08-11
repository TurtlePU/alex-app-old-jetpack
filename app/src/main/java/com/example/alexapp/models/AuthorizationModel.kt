package com.example.alexapp.models

import kotlinx.coroutines.flow.Flow

interface AuthorizationModel {
  data class Credentials(val host: String, val login: String, val token: String)

  val initials: Flow<Credentials?>
  suspend fun remember(credentials: Credentials)
}