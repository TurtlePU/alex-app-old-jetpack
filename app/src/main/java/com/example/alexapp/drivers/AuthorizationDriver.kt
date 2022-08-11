package com.example.alexapp.drivers

import com.example.alexapp.models.AuthorizationModel.Credentials

interface AuthorizationDriver {
  suspend fun checkCredentials(credentials: Credentials): String?
}