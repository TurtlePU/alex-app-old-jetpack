package com.example.alexapp.drivers

import Performance
import androidx.paging.PagingData
import com.example.alexapp.models.AuthorizationModel.Credentials
import com.example.alexapp.models.RatingModel.Rating
import kotlinx.coroutines.flow.Flow

interface AppDriver : AuthorizationDriver {
  fun flow(host: String): Flow<PagingData<Performance>>
  suspend fun rate(credentials: Credentials, performance: Performance, rating: Rating)
}