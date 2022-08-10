package com.example.alexapp

import Performance
import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow

@Composable
fun Rate(flow: Flow<PagingData<Performance>>) {
  val performances = flow.collectAsLazyPagingItems()
}