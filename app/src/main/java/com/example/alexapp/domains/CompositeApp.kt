package com.example.alexapp.domains

import Performance
import com.example.alexapp.drivers.AppDriver
import com.example.alexapp.models.AppModel
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.models.RestoreModel.Rating

class CompositeApp(private val model: AppModel, private val driver: AppDriver) : App {
  override fun flow(host: String) = driver.flow(host)
  override val initials get() = model.initials
  override suspend fun remember(credentials: Credentials) = model.remember(credentials)
  override suspend fun authorize(credentials: Credentials) = driver.authorize(credentials)
  override fun restore(performance: Performance) = model.restore(performance)
  override fun isRated(performance: Performance) = model.isRated(performance)
  override suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating) {
    model.rate(performance, rating)
    driver.rate(credentials, performance, rating)
  }
}