package com.example.alexapp

import Performance
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.alexapp.data.AppDriver
import com.example.alexapp.data.AuthorizationDriver.Credentials
import com.example.alexapp.data.RatingDriver.Rating
import kotlinx.coroutines.flow.map

class PreferencesDriver(private val dataStore: DataStore<Preferences>) : AppDriver {
  override val initialCredentials get() = dataStore.data.map(::credentials)
  override suspend fun authorizeWith(credentials: Credentials) {
    dataStore.edit { credentials.writePreferences(it) }
  }

  override fun restore(performance: Performance) = dataStore.data.map { performance.rating(it) }
  override suspend fun saveRating(performance: Performance, rating: Rating) {
    dataStore.edit { performance.writeRating(rating, it) }
  }

  companion object {
    private val HOST = stringPreferencesKey("host")
    private val LOGIN = stringPreferencesKey("login")
    private val TOKEN = stringPreferencesKey("token")
    private val Performance.GRADE get() = doublePreferencesKey("grade[$id]")
    private val Performance.COMMENT get() = stringPreferencesKey("comment[$id]")

    fun credentials(pref: Preferences): Credentials? {
      val host = pref[HOST] ?: return null
      val login = pref[LOGIN] ?: return null
      val token = pref[TOKEN] ?: return null
      return Credentials(host, login, token)
    }

    fun Performance.rating(pref: Preferences): Rating? {
      val grade = pref[GRADE] ?: return null
      val comment = pref[COMMENT]
      return Rating(grade, comment)
    }

    fun Credentials.writePreferences(pref: MutablePreferences) {
      pref[HOST] = host
      pref[LOGIN] = login
      pref[TOKEN] = token
    }

    fun Performance.writeRating(rating: Rating, pref: MutablePreferences) {
      pref[GRADE] = rating.grade
      rating.comment?.let { pref[COMMENT] = it }
    }
  }
}