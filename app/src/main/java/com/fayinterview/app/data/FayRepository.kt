package com.fayinterview.app.data

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository used to call FayService. Note: If this app gets bigger this could be broken up into
 * an AuthRepository (for sign in/up calls) and another (or multiple other) repositories
 */
@Singleton
class FayRepository @Inject constructor(
    private val fayService: FayService
) {
    suspend fun signIn(
        username: String,
        password: String
    ): Boolean {
        val result = fayService.signIn(
            request = SignInRequest(
                username = username,
                password = password
            )
        )
        return when {
            result.isSuccessful -> {
                // TODO save token for calls requiring authentication (e.g. Appointments Overview)
                true
            }

            else -> {
                false
            }
        }
    }
}
