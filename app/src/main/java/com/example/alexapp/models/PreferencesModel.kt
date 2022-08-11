package com.example.alexapp.models

import Performance
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.models.RestoreModel.Rating
import kotlinx.coroutines.flow.map

class PreferencesModel(private val dataStore: DataStore<Preferences>) : AppModel {
  override val initials get() = dataStore.data.map(Companion::credentials)
  override suspend fun remember(credentials: Credentials) {
    dataStore.edit { credentials.remember(it) }
  }

  override fun restore(performance: Performance) = dataStore.data.map { performance.rating(it) }
  override suspend fun rate(`for`: Performance, rating: Rating) {
    dataStore.edit { `for`.remember(rating, it) }
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

    fun Credentials.remember(pref: MutablePreferences) {
      pref[HOST] = host
      pref[LOGIN] = login
      pref[TOKEN] = token
    }

    fun Performance.remember(rating: Rating, pref: MutablePreferences) {
      pref[GRADE] = rating.grade
      rating.comment?.let { pref[COMMENT] = it }
    }
  }
}