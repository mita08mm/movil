package com.swalisoft.payer.common


interface StorageManager {
  fun saveAuthToken(token: String)
  fun getAuthToken(): String?
  fun clearTokens()
}

expect fun getStorageManager(): StorageManager
