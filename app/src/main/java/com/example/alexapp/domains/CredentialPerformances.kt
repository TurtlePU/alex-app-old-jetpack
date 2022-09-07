package com.example.alexapp.domains

import Performance
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.models.RestoreModel.Rating

class CredentialPerformances(private val app: App, private val cred: Credentials) : Performances {
  override val flow get() = app.flow(cred.host)
  override fun restore(performance: Performance) = app.restore(performance)
  override fun isRated(performance: Performance) = app.isRated(performance)
  override suspend fun rate(`for`: Performance, rating: Rating) = app.rate(cred, `for`, rating)
}