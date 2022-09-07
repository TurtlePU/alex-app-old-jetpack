package com.example.alexapp.drivers

import Performance
import androidx.paging.PagingData
import com.example.alexapp.models.AppModel
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.models.RestoreModel.Rating
import kotlinx.coroutines.flow.Flow

interface AppDriver : AuthorizationDriver {
  fun flow(host: String): Flow<PagingData<Performance>>
  suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating)

  object Example : AppDriver {
    private const val login = AppModel.Example.login
    override fun flow(host: String) = examplePager.flow
    override suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating) {}
    override suspend fun authorize(credentials: Credentials) =
      if (credentials.login != login) "Expected login '$login'" else null
  }
}