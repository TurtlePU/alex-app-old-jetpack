package com.example.alexapp.performance

import Performance
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.map

class RatingPreferences(private val dataStore: DataStore<Preferences>) : RatingModel {
  override fun restore(performance: Performance) = dataStore.data.map { performance.rating(it) }
  override fun isRated(performance: Performance) =
    dataStore.data.map { it.contains(performance.GRADE) }

  override suspend fun rate(performance: Performance, rating: Rating) {
    dataStore.edit { performance.remember(rating, it) }
  }

  companion object {
    private val Performance.GRADE get() = doublePreferencesKey("grade[$id]")
    private val Performance.COMMENT get() = stringPreferencesKey("comment[$id]")

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