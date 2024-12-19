package com.fayinterview.app.data

import com.fayinterview.app.ui.appointments.AppointmentInfo
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

    var authToken: String? = null //fixme

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
                authToken = result.body()?.token
                // TODO save token for calls requiring authentication (e.g. Appointments Overview)
                true
            }

            else -> {
                false
            }
        }
    }

    suspend fun fetchAppointments(): List<AppointmentInfo>? {
        return authToken?.let { authToken ->
            val result = fayService.fetchAppointments("Bearer $authToken")
            when {
                result.isSuccessful -> {
                    result.body()?.appointments?.map {
                        // Note: if fetchProviderName is implemented and could have errors, there should
                        // be error checking added for that too
                        AppointmentInfo(
                            it,
                            fetchProviderName(it.providerId)
                        )
                    }
                }

                else -> {
                    null
                }
            }
        }
    }

    private fun fetchProviderName(providerId: String): String {
        // A new endpoint can be used to fetch the provider name
        return "Taylor Palmer, RD"
    }
}
