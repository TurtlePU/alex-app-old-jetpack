package com.example.alexapp.domains

import Performance
import com.example.alexapp.drivers.AppDriver
import com.example.alexapp.models.AppModel
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.models.RatingModel.Rating

class CompositeApp(private val model: AppModel, private val driver: AppDriver) : App {
  override fun flow(host: String) = driver.flow(host)
  override val initialCredentials get() = model.initialCredentials
  override suspend fun authorizeWith(credentials: Credentials) = model.authorizeWith(credentials)
  override suspend fun checkCredentials(credentials: Credentials) =
    driver.checkCredentials(credentials)

  override fun restoreRating(performance: Performance) = model.restore(performance)
  override suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating) {
    model.saveRating(performance, rating)
    driver.rate(credentials, performance, rating)
  }
}