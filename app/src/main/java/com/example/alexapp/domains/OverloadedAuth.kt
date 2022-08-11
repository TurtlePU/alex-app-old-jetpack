package com.example.alexapp.domains

import com.example.alexapp.models.AuthorizationModel.Credentials

class OverloadedAuth(private val app: App, private val ok: Credentials.() -> Unit) : Authorization {
  override val initials get() = app.initials
  override suspend fun authorize(credentials: Credentials) = app.authorize(credentials)
  override suspend fun remember(credentials: Credentials) {
    app.remember(credentials)
    credentials.ok()
  }
}