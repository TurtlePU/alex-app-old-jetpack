package com.example.alexapp.authorization

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class AuthorizationPreferences(private val dataStore: DataStore<Preferences>) : AuthorizationModel {
  override val initials get() = dataStore.data.map(::credentials)
  override suspend fun remember(credentials: Credentials) {
    dataStore.edit { credentials.remember(it) }
  }

  companion object {
    private val HOST = stringPreferencesKey("host")
    private val LOGIN = stringPreferencesKey("login")
    private val TOKEN = stringPreferencesKey("token")

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