package com.fayinterview.app.data

import android.content.SharedPreferences

class TokenManager(
    private val encryptedSharedPreferences: SharedPreferences
) {
    companion object {
        private const val TOKEN_KEY = "jwt_token"
    }

    fun getToken(): String? {
        return encryptedSharedPreferences.getString(TOKEN_KEY, null)
    }

    suspend fun saveToken(token: String) {
        encryptedSharedPreferences
            .edit()
            .putString(TOKEN_KEY, token)
            .apply()
    }

    // Can be called if a sign out button is implemented somewhere
    suspend fun deleteToken() {
        val editor = encryptedSharedPreferences
            .edit()
            .putString(TOKEN_KEY, null)
            .apply()
    }
}
