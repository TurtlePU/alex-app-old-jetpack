package com.example.alexapp

data class Credentials(val host: String, val login: String, val token: String) {
  override fun toString(): String = "$host:$login:$token"
}