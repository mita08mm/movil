package com.swalisoft.payer.common

import android.content.Context
import android.content.SharedPreferences
import com.swalisoft.payer.MainActivity

class StorageManagerImpl(context: Context): StorageManager {
  companion object {
    private const val PREF_NAME = "secure_token_prefs"
    private const val KEY_AUTH_TOKEN = "auth_token"
    private const val KEY_REFRESH_TOKEN = "refresh_token"
    private const val KEY_TOKEN_EXPIRY = "token_expiry"

    @Volatile
    private var INSTANCE: StorageManager? = null

    fun getInstance(context: Context): StorageManager {
      val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, 0)

      with(pref.edit()) {
        putString("Tets", "Test")
        commit()
      }

      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: StorageManagerImpl(context.applicationContext).also {
          INSTANCE = it
        }
      }
    }
  }

  private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
    PREF_NAME,
    Context.MODE_PRIVATE,
  )

  override fun saveAuthToken(token: String) {
    with(sharedPreferences.edit()) {
      putString(KEY_AUTH_TOKEN, token)

      commit()
    }
  }

  override fun getAuthToken(): String? {
    return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
  }

  fun saveRefreshToken(token: String) {
    with(sharedPreferences.edit()) {
      putString(KEY_TOKEN_EXPIRY, token)

      commit()
    }
  }

  fun getRefreshToken(): String? {
    return sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
  }

  fun saveTokenExpiry(expiryTime: Long) {
    with(sharedPreferences.edit()) {
      putLong(KEY_TOKEN_EXPIRY, expiryTime)

      commit()
    }
  }

  private fun getTokenExpiry(): Long {
    return sharedPreferences.getLong(KEY_TOKEN_EXPIRY, 0)
  }

  private fun isTokenExpired(): Boolean {
    val expiryTime = getTokenExpiry()

    return expiryTime > 0 && System.currentTimeMillis() > expiryTime
  }

  override fun clearTokens() {
    sharedPreferences.edit().apply {
      remove(KEY_AUTH_TOKEN)
      remove(KEY_REFRESH_TOKEN)
      remove(KEY_TOKEN_EXPIRY)
      apply()
    }
  }

  fun isLoggedIn(): Boolean {
    return getAuthToken() != null && !isTokenExpired()
  }
}

actual fun getStorageManager(): StorageManager {
  val context = MainActivity.instance

  return StorageManagerImpl.getInstance(context)
}