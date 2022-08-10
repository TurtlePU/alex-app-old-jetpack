package com.example.alexapp

import android.os.Bundle

data class Credentials(val host: String, val login: String, val token: String) {
  companion object {
    val Bundle.credentials: Credentials?
      get() {
        val host = getString("host") ?: return null
        val login = getString("login") ?: return null
        val token = getString("token") ?: return null
        return Credentials(host, login, token)
      }
  }

  override fun toString(): String = "$host:$login:$token"
}