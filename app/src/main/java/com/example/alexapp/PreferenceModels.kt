package com.example.alexapp

import Performance
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.map

class AuthorizationPreferences(private val dataStore: DataStore<Preferences>) : AuthorizationModel {
  override val initials get() = dataStore.data.map(::credentials)
  override suspend fun remember(credentials: Credentials) {
    dataStore.edit { credentials.remember(it) }
  }

  private companion object {
    val HOST = stringPreferencesKey("host")
    val LOGIN = stringPreferencesKey("login")
    val TOKEN = stringPreferencesKey("token")

    fun credentials(pref: Preferences): Credentials? {
      val host = pref[HOST] ?: return null
      val login = pref[LOGIN] ?: return null
      val token = pref[TOKEN] ?: return null
      return Credentials(host, login, token)
    }

    fun Credentials.remember(pref: MutablePreferences) {
      pref[HOST] = host
      pref[LOGIN] = login
      pref[TOKEN] = token
    }
  }
}

class RatingPreferences(private val dataStore: DataStore<Preferences>) : RatingModel {
  override fun restore(performance: Performance) = dataStore.data.map { performance.rating(it) }
  override fun isRated(performance: Performance) =
    dataStore.data.map { it.contains(performance.GRADE) }

  override suspend fun rate(performance: Performance, rating: Rating) {
    dataStore.edit { performance.remember(rating, it) }
  }

  private companion object {
    val Performance.GRADE get() = doublePreferencesKey("grade[$id]")
    val Performance.COMMENT get() = stringPreferencesKey("comment[$id]")

    fun Performance.rating(pref: Preferences): Rating? {
      val grade = pref[GRADE] ?: return null
      val comment = pref[COMMENT]
      return Rating(grade, comment)
    }

    fun Performance.remember(rating: Rating, pref: MutablePreferences) {
      pref[GRADE] = rating.grade
      rating.comment?.let { pref[COMMENT] = it }
    }
  }
}